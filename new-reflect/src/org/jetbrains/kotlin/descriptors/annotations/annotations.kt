package org.jetbrains.kotlin.descriptors.annotations

import kotlinx.metadata.KmAnnotation
import kotlinx.metadata.KmAnnotationArgument
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.ModuleDescriptor
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.KotlinType
import java.lang.reflect.AnnotatedElement
import kotlin.reflect.jvm.internal.toAnnotationInstance

internal interface Annotated {
    val annotations: Annotations
}

interface Annotations {
    fun getAll(): List<Annotation>

    object Empty : Annotations {
        override fun getAll(): List<Annotation> = emptyList()
    }

    companion object {
        val EMPTY: Empty get() = Empty
    }
}

internal class JvmAnnotations(private val annotations: List<Annotation>): Annotations {
    constructor(element: AnnotatedElement) : this(element.declaredAnnotations.toList())

    override fun getAll(): List<Annotation> = annotations
}

internal class SerializedAnnotations(private val module: ModuleDescriptor, private val annotations: List<KmAnnotation>): Annotations {
    override fun getAll(): List<Annotation> =
        annotations.mapNotNull { it.toDescriptor().toAnnotationInstance() }

    // TODO: it shouldn't be an error if the class is not found
    private fun KmAnnotation.toDescriptor(): AnnotationDescriptor = AnnotationDescriptor(
        module.findClass(className) ?: error("Class in annotation $this is not found"),
        arguments.map { (name, argument) -> name to argument.toConstantValue() }.toMap(),
    )

    // TODO: test all of this
    private fun KmAnnotationArgument.toConstantValue(): ConstantValue<*> = when (this) {
        is KmAnnotationArgument.LiteralValue<*> -> LiteralValue(value)
        is KmAnnotationArgument.KClassValue -> KClassValue(ClassId(className), arrayDimensionCount)
        is KmAnnotationArgument.EnumValue -> EnumValue(ClassId(enumClassName), enumEntryName)
        is KmAnnotationArgument.AnnotationValue -> AnnotationValue(annotation.toDescriptor())
        is KmAnnotationArgument.ArrayValue -> ArrayValue(elements.map { it.toConstantValue() })
    }
}

internal class AnnotationDescriptor(
    val annotationClass: ClassDescriptor,
    val allValueArguments: Map<Name, ConstantValue<*>>,
)

internal fun Annotations.hasAnnotation(fqName: FqName): Boolean =
    getAll().any { it.annotationClass.java.name == fqName.fqName }

internal abstract class ConstantValue<out T>(open val value: T)
internal class AnnotationValue(value: AnnotationDescriptor) : ConstantValue<AnnotationDescriptor>(value)
internal class ArrayValue(value: List<ConstantValue<*>>) : ConstantValue<List<ConstantValue<*>>>(value)
internal class EnumValue(val enumClassId: ClassId, val enumEntryName: Name) : ConstantValue<Pair<ClassId, Name>>(enumClassId to enumEntryName)
internal class ErrorValue(message: String) : ConstantValue<String>(message)
internal class NullValue : ConstantValue<Nothing?>(null)
internal class LiteralValue(value: Any) : ConstantValue<Any>(value)

internal class KClassValue(value: Value) : ConstantValue<KClassValue.Value>(value) {
    sealed class Value {
        data class NormalClass(val value: ClassLiteralValue) : Value() {
            val classId: ClassId get() = value.classId
            val arrayDimensions: Int get() = value.arrayNestedness
        }

        data class LocalClass(val type: KotlinType) : Value()
    }

    constructor(value: ClassLiteralValue) : this(Value.NormalClass(value))

    constructor(classId: ClassId, arrayDimensions: Int) : this(ClassLiteralValue(classId, arrayDimensions))
}

data class ClassLiteralValue(val classId: ClassId, val arrayNestedness: Int) {
    override fun toString(): String = buildString {
        repeat(arrayNestedness) { append("kotlin/Array<") }
        append(classId)
        repeat(arrayNestedness) { append(">") }
    }
}
