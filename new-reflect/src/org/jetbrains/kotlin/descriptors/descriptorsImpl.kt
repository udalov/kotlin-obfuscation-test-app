package org.jetbrains.kotlin.descriptors

import kotlinx.metadata.ClassName
import org.jetbrains.kotlin.builtins.JavaToKotlinClassMap
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.misc.tryLoadClass
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.KotlinType
import kotlin.reflect.jvm.internal.KClassImpl

internal class ModuleDescriptorImpl(private val classLoader: ClassLoader) : ModuleDescriptor {
    override fun findClass(name: ClassName): ClassDescriptor? {
        val fqName =
            (JavaToKotlinClassMap.mapKotlinToJava(FqName(name.replace('/', '.'))) ?: ClassId(name)).asJavaLookupFqName()

        val jClass = when (fqName) {
            "kotlin.BooleanArray" -> BooleanArray::class.java
            "kotlin.ByteArray" -> ByteArray::class.java
            "kotlin.CharArray" -> CharArray::class.java
            "kotlin.DoubleArray" -> DoubleArray::class.java
            "kotlin.FloatArray" -> FloatArray::class.java
            "kotlin.IntArray" -> IntArray::class.java
            "kotlin.LongArray" -> LongArray::class.java
            "kotlin.ShortArray" -> ShortArray::class.java
            "kotlin.Array" -> {
                // We use `[Ljava.lang.Object;` as underlying Class for the class descriptor for kotlin/Array,
                // even if the type where it's referenced had another argument. This way we can have
                // exactly one class descriptor for kotlin/Array.
                Array<Any>::class.java
            }
            else -> classLoader.tryLoadClass(fqName)
        }
        return (jClass?.kotlin as KClassImpl<*>?)?.descriptor
    }
}

internal class ReceiverParameterDescriptorImpl(
    override val type: KotlinType
) : ReceiverParameterDescriptor {
    override val containingDeclaration: CallableMemberDescriptor
        get() = error("Shouldn't be called")

    override val annotations: Annotations
        get() = Annotations.EMPTY

    override val name: Name
        get() = "<this>"
}
