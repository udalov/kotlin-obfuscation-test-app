package org.jetbrains.kotlin.descriptors

import kotlinx.metadata.ClassName
import org.jetbrains.kotlin.builtins.JavaToKotlinClassMap
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.misc.tryLoadClass
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.KotlinType
import kotlin.reflect.jvm.internal.KClassImpl

internal class ModuleDescriptorImpl(val classLoader: ClassLoader) : ModuleDescriptor {
    override fun findClass(name: ClassName): ClassDescriptor? {
        // TODO: verify
        return findClass(FqName(name.replace('.', '$').replace('/', '.')))
    }

    fun findClass(kotlinFqName: FqName): ClassDescriptor? {
        val fqName = JavaToKotlinClassMap.mapKotlinToJava(kotlinFqName)?.asSingleFqName() ?: kotlinFqName
        val jClass = classLoader.tryLoadClass(fqName.asString())
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
