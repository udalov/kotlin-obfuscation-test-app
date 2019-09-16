package org.jetbrains.kotlin.descriptors

import kotlinx.metadata.*
import kotlinx.metadata.jvm.KotlinClassHeader
import kotlinx.metadata.jvm.KotlinClassMetadata
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.KotlinType

class ModuleDescriptor(val classLoader: ClassLoader) {
    fun findClass(name: ClassName): ClassDescriptor? {
        // TODO: verify
        return findClass(FqName(name.replace('/', '.')))
    }

    fun findClass(fqName: FqName): ClassDescriptor? {
        val jClass = classLoader.loadClass(fqName.asString())
        // TODO: cache
        return createClass(jClass)
    }

    fun createClass(jClass: Class<*>): ClassDescriptor? {
        val metadata = jClass.getDeclaredAnnotation(Metadata::class.java)
        val descriptor =
            if (metadata != null) {
                val header = with(metadata) {
                    KotlinClassHeader(kind, metadataVersion, data1, data2, extraString, packageName, extraInt)
                }
                val kmClass = (KotlinClassMetadata.read(header) as KotlinClassMetadata.Class).toKmClass()
                ClassDescriptorImpl(kmClass)
            } else TODO()

        return descriptor
    }
}

class ClassDescriptorImpl(private val klass: KmClass) : ClassDescriptor {
    override val name: Name
        get() = klass.name.substring(klass.name.lastIndexOf('.') + 1).substring(klass.name.lastIndexOf('/') + 1)

    override val visibility: DescriptorVisibility
        get() = klass.flags.toVisibility()

    override val annotations: Annotations
        get() = Annotations.EMPTY // TODO

    override val isInterface: Boolean
        get() = Flag.Class.IS_INTERFACE(klass.flags)
    override val isAnnotationClass: Boolean
        get() = Flag.Class.IS_ANNOTATION_CLASS(klass.flags)
    override val isObject: Boolean
        get() = Flag.Class.IS_OBJECT(klass.flags)
    override val isFinal: Boolean
        get() = Flag.Common.IS_FINAL(klass.flags)
    override val isOpen: Boolean
        get() = Flag.Common.IS_OPEN(klass.flags)
    override val isAbstract: Boolean
        get() = Flag.Common.IS_ABSTRACT(klass.flags)
    override val isSealed: Boolean
        get() = Flag.Common.IS_SEALED(klass.flags)
    override val isData: Boolean
        get() = Flag.Class.IS_DATA(klass.flags)
    override val isInner: Boolean
        get() = Flag.Class.IS_INNER(klass.flags)
    override val isInline: Boolean
        get() = Flag.Class.IS_VALUE(klass.flags)
    override val isCompanionObject: Boolean
        get() = Flag.Class.IS_COMPANION_OBJECT(klass.flags)
    override val isFun: Boolean
        get() = Flag.Class.IS_FUN(klass.flags)

    override val declaredTypeParameters: List<TypeParameterDescriptor>
        get() = emptyList() // TODO
    override val supertypes: List<KotlinType>
        get() = emptyList() // TODO
    override val containingClass: ClassDescriptor?
        get() = null // TODO
    override val thisAsReceiverParameter: ReceiverParameterDescriptor =
        ReceiverParameterDescriptorImpl(defaultType)
    override val constructors: List<ConstructorDescriptor>
        get() = emptyList() // TODO
    override val nestedClasses: List<ClassDescriptor>
        get() = emptyList() // TODO
    override val sealedSubclasses: List<ClassDescriptor>
        get() = emptyList() // TODO
    override val memberScope: MemberScope
        get() = MemberScope(emptyList(), klass.functions.map { FunctionDescriptorImpl(it, this) }) // TODO
    override val staticScope: MemberScope
        get() = MemberScope(emptyList(), emptyList()) // TODO
}

class FunctionDescriptorImpl(val function: KmFunction, override val containingDeclaration: ClassDescriptor) : FunctionDescriptor {
    override val name: Name
        get() = function.name
    override val visibility: DescriptorVisibility
        get() = function.flags.toVisibility()
    override val annotations: Annotations
        get() = Annotations.EMPTY // TODO

    override val dispatchReceiverParameter: ReceiverParameterDescriptor?
        get() = containingDeclaration.thisAsReceiverParameter // TODO
    override val extensionReceiverParameter: ReceiverParameterDescriptor?
        get() = null // TODO
    override val valueParameters: List<ValueParameterDescriptor>
        get() = emptyList() // TODO
    override val typeParameters: List<TypeParameterDescriptor>
        get() = emptyList() // TODO
    override val returnType: KotlinType
        get() = TODO()
    override val isFinal: Boolean
        get() = Flag.Common.IS_FINAL(function.flags)
    override val isOpen: Boolean
        get() = Flag.Common.IS_OPEN(function.flags)
    override val isAbstract: Boolean
        get() = Flag.Common.IS_ABSTRACT(function.flags)
    override val isExternal: Boolean
        get() = Flag.Function.IS_EXTERNAL(function.flags)
    override val isInline: Boolean
        get() = Flag.Function.IS_INLINE(function.flags)
    override val isOperator: Boolean
        get() = Flag.Function.IS_OPERATOR(function.flags)
    override val isInfix: Boolean
        get() = Flag.Function.IS_INFIX(function.flags)
    override val isSuspend: Boolean
        get() = Flag.Function.IS_SUSPEND(function.flags)
    override val isReal: Boolean
        get() = true // TODO

    override fun hasSynthesizedParameterNames(): Boolean = false

    override fun render(): String = TODO()
}

/*
// TODO
fun KmType.toKotlinType() : KotlinType {
    return KotlinType(

    )
}
*/

private fun Flags.toVisibility(): DescriptorVisibility =
    when {
        Flag.Common.IS_PRIVATE(this) -> DescriptorVisibility.PRIVATE
        Flag.Common.IS_PRIVATE_TO_THIS(this) -> DescriptorVisibility.PRIVATE_TO_THIS
        Flag.Common.IS_INTERNAL(this) -> DescriptorVisibility.INTERNAL
        Flag.Common.IS_PROTECTED(this) -> DescriptorVisibility.PROTECTED
        Flag.Common.IS_PUBLIC(this) -> DescriptorVisibility.PUBLIC
        else -> TODO()
    }
