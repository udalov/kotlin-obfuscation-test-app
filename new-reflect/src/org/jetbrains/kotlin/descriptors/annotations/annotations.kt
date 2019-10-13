package org.jetbrains.kotlin.descriptors.annotations

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.KotlinType

internal interface Annotated {
    val annotations: Annotations
}

internal interface Annotations : Iterable<AnnotationDescriptor> {
    fun findAnnotation(fqName: FqName): AnnotationDescriptor?

    object Empty : Annotations {
        override fun findAnnotation(fqName: FqName): AnnotationDescriptor? = null
        override fun iterator(): Iterator<AnnotationDescriptor> = emptyList<Nothing>().iterator()
    }

    companion object {
        val EMPTY: Empty get() = Empty
    }
}

internal interface AnnotationDescriptor {
    val annotationClass: ClassDescriptor
    val allValueArguments: Map<Name, ConstantValue<*>>
}

internal fun Annotations.hasAnnotation(fqName: FqName): Boolean =
    findAnnotation(fqName) != null

internal abstract class ConstantValue<out T>(open val value: T)
internal class AnnotationValue(value: AnnotationDescriptor) : ConstantValue<AnnotationDescriptor>(value)
internal class ArrayValue(value: List<ConstantValue<*>>) : ConstantValue<List<ConstantValue<*>>>(value)
internal class EnumValue(val enumClassId: ClassId, val enumEntryName: Name) : ConstantValue<Pair<ClassId, Name>>(enumClassId to enumEntryName)
internal class ErrorValue(message: String) : ConstantValue<String>(message)
internal class NullValue : ConstantValue<Nothing?>(null)

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
