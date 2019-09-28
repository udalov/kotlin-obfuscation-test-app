package org.jetbrains.kotlin.descriptors

import kotlinx.metadata.*
import org.jetbrains.kotlin.builtins.KotlinBuiltInsImpl
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.TypeProjection
import kotlin.reflect.KVariance

class ClassDescriptorImpl(
    private val klass: KmClass,
    override val module: ModuleDescriptor,
    override val classId: ClassId
) : ClassDescriptor {
    override val name: Name
        get() = klass.name.substringAfterLast('.').substringAfterLast('/')

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
        get() = klass.typeParameters.map { TypeParameterDescriptorImpl(it, module) }
    override val supertypes: List<KotlinType>
        get() = klass.supertypes.map { it.toKotlinType(module) }
    override val containingClass: ClassDescriptor?
        get() = null // TODO
    override val thisAsReceiverParameter: ReceiverParameterDescriptor =
        ReceiverParameterDescriptorImpl(defaultType)
    override val constructors: List<ConstructorDescriptor>
        get() = klass.constructors.map { ConstructorDescriptorImpl(it, module, this) }
    override val nestedClasses: List<ClassDescriptor>
        get() = emptyList() // TODO
    override val sealedSubclasses: List<ClassDescriptor>
        get() = emptyList() // TODO
    override val memberScope: MemberScope
        get() = MemberScope(emptyList(), klass.functions.map { FunctionDescriptorImpl(it, module, this) }) // TODO
    override val staticScope: MemberScope
        get() = MemberScope(emptyList(), emptyList()) // TODO
}

class FileDescriptorImpl(val file: KmPackage, private val module: ModuleDescriptor) : FileDescriptor {
    override val scope: MemberScope
        get() = MemberScope(
            emptyList(), // TODO
            file.functions.map { FunctionDescriptorImpl(it, module, null) }
        )
}

abstract class FunctionDescriptorBase : FunctionDescriptor {
    abstract val flags: Flags

    override val visibility: DescriptorVisibility
        get() = flags.toVisibility()

    override val isFinal: Boolean
        get() = Flag.Common.IS_FINAL(flags)
    override val isOpen: Boolean
        get() = Flag.Common.IS_OPEN(flags)
    override val isAbstract: Boolean
        get() = Flag.Common.IS_ABSTRACT(flags)
    override val isExternal: Boolean
        get() = Flag.Function.IS_EXTERNAL(flags)
    override val isInline: Boolean
        get() = Flag.Function.IS_INLINE(flags)
    override val isOperator: Boolean
        get() = Flag.Function.IS_OPERATOR(flags)
    override val isInfix: Boolean
        get() = Flag.Function.IS_INFIX(flags)
    override val isSuspend: Boolean
        get() = Flag.Function.IS_SUSPEND(flags)
}

class FunctionDescriptorImpl(
    val function: KmFunction,
    override val module: ModuleDescriptor,
    override val containingClass: ClassDescriptor?
) : FunctionDescriptorBase() {
    override val name: Name
        get() = function.name
    override val annotations: Annotations
        get() = Annotations.EMPTY // TODO

    override val dispatchReceiverParameter: ReceiverParameterDescriptor?
        get() = containingClass?.thisAsReceiverParameter // TODO
    override val extensionReceiverParameter: ReceiverParameterDescriptor?
        get() = function.receiverParameterType?.let { ReceiverParameterDescriptorImpl(it.toKotlinType(module)) }
    override val valueParameters: List<ValueParameterDescriptor>
        get() = function.valueParameters.map { ValueParameterDescriptorImpl(it, this) }
    override val typeParameters: List<TypeParameterDescriptor>
        get() = function.typeParameters.map { TypeParameterDescriptorImpl(it, module) }
    override val returnType: KotlinType
        get() = function.returnType.toKotlinType(module)
    override val isReal: Boolean
        get() = true // TODO

    override fun hasSynthesizedParameterNames(): Boolean = false

    override fun render(): String = TODO()

    override val flags: Flags
        get() = function.flags
}

class ConstructorDescriptorImpl(
    val constructor: KmConstructor,
    override val module: ModuleDescriptor,
    override val containingClass: ClassDescriptor
) : FunctionDescriptorBase(), ConstructorDescriptor {
    override val name: Name
        get() = "<init>"
    override val annotations: Annotations
        get() = Annotations.EMPTY // TODO

    override val dispatchReceiverParameter: ReceiverParameterDescriptor
        get() = containingClass.thisAsReceiverParameter // TODO
    override val extensionReceiverParameter: ReceiverParameterDescriptor?
        get() = null
    override val valueParameters: List<ValueParameterDescriptor>
        get() = constructor.valueParameters.map { ValueParameterDescriptorImpl(it, this) }
    override val typeParameters: List<TypeParameterDescriptor>
        get() = emptyList()
    override val returnType: KotlinType
        get() = containingClass.defaultType
    override val isReal: Boolean
        get() = true

    override fun hasSynthesizedParameterNames(): Boolean = false

    override fun render(): String = TODO()

    override val constructedClass: ClassDescriptor
        get() = containingClass
    override val isPrimary: Boolean
        get() = !Flag.Constructor.IS_SECONDARY(flags)

    override val flags: Flags
        get() = constructor.flags
}

class TypeParameterDescriptorImpl(
    private val typeParameter: KmTypeParameter,
    private val module: ModuleDescriptor
) : TypeParameterDescriptor {
    override val name: Name
        get() = typeParameter.name
    override val annotations: Annotations
        get() = TODO() // typeParameter.annotations

    override val upperBounds: List<KotlinType>
        get() = typeParameter.upperBounds.map { it.toKotlinType(module) }
    override val variance: KVariance
        get() = typeParameter.variance.toVariance()
    override val isReified: Boolean
        get() = Flag.TypeParameter.IS_REIFIED(typeParameter.flags)
}

class ValueParameterDescriptorImpl(
    private val valueParameter: KmValueParameter,
    override val containingDeclaration: CallableMemberDescriptor
) : ValueParameterDescriptor {
    override val name: Name
        get() = valueParameter.name
    override val annotations: Annotations
        get() = TODO()

    override val type: KotlinType
        get() = valueParameter.type!!.toKotlinType(containingDeclaration.module) // TODO: fix nullability in kotlinx-metadata
    override val declaresDefaultValue: Boolean
        get() = Flag.ValueParameter.DECLARES_DEFAULT_VALUE(valueParameter.flags)
    override val varargElementType: KotlinType?
        get() = valueParameter.varargElementType?.toKotlinType(containingDeclaration.module)
}

// TODO
fun KmType.toKotlinType(module: ModuleDescriptor): KotlinType {
    val classifier = classifier.let { classifier ->
        when (classifier) {
            is KmClassifier.Class -> module.findClass(classifier.name) ?: TODO(classifier.name)
            is KmClassifier.TypeParameter -> TODO()
            is KmClassifier.TypeAlias -> TODO()
        }
    }
    return KotlinType(
        classifier,
        arguments.map { (variance, type) ->
            TypeProjection(
                type?.toKotlinType(module) ?: KotlinBuiltInsImpl.anyType,
                variance == null,
                variance?.toVariance() ?: KVariance.OUT /* TODO: verify */
            )
        },
        Flag.Type.IS_NULLABLE(flags),
        Annotations.EMPTY // TODO
    )
}

private fun KmVariance.toVariance(): KVariance =
    when (this) {
        KmVariance.INVARIANT -> KVariance.INVARIANT
        KmVariance.IN -> KVariance.IN
        KmVariance.OUT -> KVariance.OUT
    }

private fun Flags.toVisibility(): DescriptorVisibility =
    when {
        Flag.Common.IS_PRIVATE(this) -> DescriptorVisibility.PRIVATE
        Flag.Common.IS_PRIVATE_TO_THIS(this) -> DescriptorVisibility.PRIVATE_TO_THIS
        Flag.Common.IS_INTERNAL(this) -> DescriptorVisibility.INTERNAL
        Flag.Common.IS_PROTECTED(this) -> DescriptorVisibility.PROTECTED
        Flag.Common.IS_PUBLIC(this) -> DescriptorVisibility.PUBLIC
        else -> TODO()
    }
