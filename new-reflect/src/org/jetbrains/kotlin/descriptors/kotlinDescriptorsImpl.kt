package org.jetbrains.kotlin.descriptors

import kotlinx.metadata.*
import kotlinx.metadata.jvm.*
import org.jetbrains.kotlin.builtins.KotlinBuiltInsImpl
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.descriptors.annotations.JvmAnnotations
import org.jetbrains.kotlin.descriptors.annotations.SerializedAnnotations
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.TypeProjection
import kotlin.reflect.KVariance
import kotlin.reflect.jvm.internal.*

internal class ClassDescriptorImpl internal constructor(
    val klass: KmClass,
    override val module: ModuleDescriptor,
    override val classId: ClassId,
    override val kClass: KClassImpl<*>
) : ClassDescriptor {
    override val name: Name
        get() = klass.name.substringAfterLast('.').substringAfterLast('/')

    override val visibility: DescriptorVisibility?
        get() = klass.flags.toVisibility()

    override val annotations: Annotations
        get() = JvmAnnotations(kClass.jClass.declaredAnnotations.filterNot { it.annotationClass.java == Metadata::class.java })

    override val isInterface: Boolean
        get() = Flag.Class.IS_INTERFACE(klass.flags)
    override val isAnnotationClass: Boolean
        get() = Flag.Class.IS_ANNOTATION_CLASS(klass.flags)
    override val isNonCompanionObject: Boolean
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

    // TODO: cache descriptor instances

    internal val typeParameterTable: TypeParameterTable =
        klass.typeParameters.toTypeParameters(this, module, containingClass?.typeParameterTable)

    override val declaredTypeParameters: List<TypeParameterDescriptorImpl>
        get() = typeParameterTable.typeParameters
    override val supertypes: List<KotlinType>
        get() = klass.supertypes.map { it.toKotlinType(module, typeParameterTable) }
    override val containingClass: ClassDescriptorImpl?
        get() = classId.getOuterClassId()?.let { module.findClass(it.asClassName()) as ClassDescriptorImpl? }
    override val thisAsReceiverParameter: ReceiverParameterDescriptor =
        ReceiverParameterDescriptorImpl(defaultType)
    override val constructors: List<ConstructorDescriptor>
        get() = klass.constructors.map { ConstructorDescriptorImpl(it, module, this) }
    override val nestedClasses: List<ClassDescriptor>
        get() = klass.nestedClasses.mapNotNull { module.findClass(classId.createNestedClassId(it).asClassName()) }
    override val sealedSubclasses: List<ClassDescriptor>
        get() = klass.sealedSubclasses.mapNotNull(module::findClass)
    override val memberScope: MemberScope
        get() = MemberScope(
            klass.properties.map { PropertyDescriptorImpl(it, module, this, kClass) }.let { realProperties ->
                realProperties + addPropertyFakeOverrides(this, realProperties)
            },
            klass.functions.map { FunctionDescriptorImpl(it, module, this, kClass) }.let { realFunctions ->
                realFunctions + addFunctionFakeOverrides(this, realFunctions)
            }
        ) // TODO: fake overrides should be cached
    override val staticScope: MemberScope
        get() = MemberScope(emptyList(), emptyList()) // TODO

    fun getLocalProperty(index: Int): PropertyDescriptorImpl? {
        val it = klass.localDelegatedProperties.getOrNull(index) ?: return null
        return PropertyDescriptorImpl(it, module, null, kClass)
    }

    override fun equals(other: Any?): Boolean =
        other is ClassDescriptor && classId == other.classId

    override fun hashCode(): Int =
        classId.hashCode()
}

private fun List<KmTypeParameter>.toTypeParameters(
    container: DeclarationDescriptor,
    module: ModuleDescriptor,
    parentTable: TypeParameterTable?
): TypeParameterTable {
    val list = ArrayList<TypeParameterDescriptorImpl>(size)
    return TypeParameterTable(list, parentTable).also { table ->
        mapTo(list) { TypeParameterDescriptorImpl(it, module, container, table) }
    }
}

internal class FileDescriptorImpl(
    val file: KmPackage,
    private val module: ModuleDescriptor,
    private val kPackage: KPackageImpl
) : FileDescriptor {
    override val scope: MemberScope
        get() = MemberScope(
            file.properties.map { PropertyDescriptorImpl(it, module, null, kPackage) },
            file.functions.map { FunctionDescriptorImpl(it, module, null, kPackage) }
        )

    fun getLocalProperty(index: Int): PropertyDescriptorImpl? {
        val it = file.localDelegatedProperties.getOrNull(index) ?: return null
        return PropertyDescriptorImpl(it, module, null, kPackage)
    }
}

abstract class AbstractCallableMemberDescriptor : CallableMemberDescriptor {
    protected abstract val flags: Flags

    internal abstract val typeParameterTable: TypeParameterTable

    abstract val methodForAnnotations: JvmMethodSignature?

    override val visibility: DescriptorVisibility?
        get() = flags.toVisibility()

    override val isFinal: Boolean
        get() = Flag.Common.IS_FINAL(flags)
    override val isOpen: Boolean
        get() = Flag.Common.IS_OPEN(flags)
    override val isAbstract: Boolean
        get() = Flag.Common.IS_ABSTRACT(flags)
    override val isExternal: Boolean
        get() = Flag.Function.IS_EXTERNAL(flags)

    override val annotations: Annotations
        get() {
            val signature = methodForAnnotations ?: return Annotations.EMPTY
            return JvmAnnotations(
                container.findMethodOrConstructorBySignature(signature.name, signature.desc)
                    ?: throw KotlinReflectionInternalError("Method $signature not found in $container")
            )
        }
}

abstract class AbstractFunctionDescriptor : AbstractCallableMemberDescriptor(), FunctionDescriptor {
    override val isInline: Boolean
        get() = Flag.Function.IS_INLINE(flags)
    override val isOperator: Boolean
        get() = Flag.Function.IS_OPERATOR(flags)
    override val isInfix: Boolean
        get() = Flag.Function.IS_INFIX(flags)
    override val isSuspend: Boolean
        get() = Flag.Function.IS_SUSPEND(flags)

    override fun render(): String = ReflectionObjectRenderer.renderFunction(this)

    override fun toString(): String = render()
}

internal class FunctionDescriptorImpl(
    val function: KmFunction,
    override val module: ModuleDescriptor,
    override val containingClass: ClassDescriptorImpl?,
    override val container: KDeclarationContainerImpl,
) : AbstractFunctionDescriptor() {
    override val name: Name
        get() = function.name
    override val methodForAnnotations: JvmMethodSignature
        get() = function.signature ?: error("Function $name in $container has no signature")

    override val typeParameterTable: TypeParameterTable =
        function.typeParameters.toTypeParameters(this, module, containingClass?.typeParameterTable)

    override val dispatchReceiverParameter: ReceiverParameterDescriptor?
        get() = containingClass?.thisAsReceiverParameter // TODO
    override val extensionReceiverParameter: ReceiverParameterDescriptor?
        get() = function.receiverParameterType?.let {
            ReceiverParameterDescriptorImpl(it.toKotlinType(module, typeParameterTable))
        }
    override val valueParameters: List<ValueParameterDescriptor>
        // TODO: make index +1 for extensions and ensure there's a test
        get() = function.valueParameters.mapIndexed { index, parameter -> ValueParameterDescriptorImpl(parameter, this, index) }
    override val typeParameters: List<TypeParameterDescriptorImpl>
        get() = typeParameterTable.typeParameters
    override val returnType: KotlinType
        get() = function.returnType.toKotlinType(module, typeParameterTable)
    override val isReal: Boolean
        get() = true // TODO

    override fun hasSynthesizedParameterNames(): Boolean = false

    override val flags: Flags
        get() = function.flags
}

internal class ConstructorDescriptorImpl(
    val constructor: KmConstructor,
    override val module: ModuleDescriptor,
    override val containingClass: ClassDescriptorImpl
) : AbstractFunctionDescriptor(), ConstructorDescriptor {
    override val name: Name
        get() = "<init>"
    override val methodForAnnotations: JvmMethodSignature
        get() = constructor.signature ?: error("Constructor in $container has no signature")

    override val container: KDeclarationContainerImpl
        get() = containingClass.kClass

    override val typeParameterTable: TypeParameterTable =
        emptyList<KmTypeParameter>().toTypeParameters(this, module, containingClass.typeParameterTable)

    override val dispatchReceiverParameter: ReceiverParameterDescriptor?
        get() = if (containingClass.isInner) containingClass.containingClass!!.thisAsReceiverParameter else null
    override val extensionReceiverParameter: ReceiverParameterDescriptor?
        get() = null
    override val valueParameters: List<ValueParameterDescriptor>
        // TODO: check annotations on parameters of inner class constructors
        get() = constructor.valueParameters.mapIndexed { index, parameter -> ValueParameterDescriptorImpl(parameter, this, index) }
    override val typeParameters: List<TypeParameterDescriptor>
        get() = emptyList()
    override val returnType: KotlinType
        get() = containingClass.defaultType
    override val isReal: Boolean
        get() = true

    override fun hasSynthesizedParameterNames(): Boolean = false

    override val constructedClass: ClassDescriptor
        get() = containingClass
    override val isPrimary: Boolean
        get() = !Flag.Constructor.IS_SECONDARY(flags)

    override val flags: Flags
        get() = constructor.flags
}

internal class PropertyDescriptorImpl(
    val property: KmProperty,
    override val module: ModuleDescriptor,
    override val containingClass: ClassDescriptorImpl?,
    override val container: KDeclarationContainerImpl,
) : AbstractCallableMemberDescriptor(), PropertyDescriptor {
    override val name: Name
        get() = property.name
    override val methodForAnnotations: JvmMethodSignature?
        get() = property.syntheticMethodForAnnotations

    override val typeParameterTable: TypeParameterTable =
        property.typeParameters.toTypeParameters(this, module, containingClass?.typeParameterTable)

    override val dispatchReceiverParameter: ReceiverParameterDescriptor?
        get() = containingClass?.thisAsReceiverParameter // TODO
    override val extensionReceiverParameter: ReceiverParameterDescriptor?
        get() = property.receiverParameterType?.let { ReceiverParameterDescriptorImpl(it.toKotlinType(module, typeParameterTable)) }
    override val valueParameters: List<ValueParameterDescriptor>
        get() = emptyList()
    override val typeParameters: List<TypeParameterDescriptorImpl>
        get() = typeParameterTable.typeParameters
    override val returnType: KotlinType
        get() = property.returnType.toKotlinType(module, typeParameterTable)
    override val isReal: Boolean
        get() = true // TODO

    override fun hasSynthesizedParameterNames(): Boolean = false

    override val isVar: Boolean
        get() = Flag.Property.IS_VAR(flags)
    override val isLateInit: Boolean
        get() = Flag.Property.IS_LATEINIT(flags)
    override val isConst: Boolean
        get() = Flag.Property.IS_CONST(flags)
    override val isDelegated: Boolean
        get() = Flag.Property.IS_DELEGATED(flags)
    override val getter: PropertyGetterDescriptor? =
        if (Flag.Property.HAS_GETTER(flags)) PropertyGetterDescriptorImpl(this) else null
    override val setter: PropertySetterDescriptor? =
        if (Flag.Property.HAS_SETTER(flags)) PropertySetterDescriptorImpl(this) else null
    override val flags: Flags
        get() = property.flags

    override val isMovedFromInterfaceCompanion: Boolean
        get() = JvmFlag.Property.IS_MOVED_FROM_INTERFACE_COMPANION(property.jvmFlags)

    override fun render(): String = ReflectionObjectRenderer.renderProperty(this)

    override fun toString(): String = render()
}

internal class PropertyGetterDescriptorImpl(
    override val property: PropertyDescriptorImpl
) : AbstractFunctionDescriptor(), PropertyGetterDescriptor {
    override val name: Name
        get() = TODO() // TODO: shouldn't be called
    override val methodForAnnotations: JvmMethodSignature?
        get() = property.property.getterSignature

    override val module: ModuleDescriptor
        get() = property.module
    override val containingClass: ClassDescriptorImpl?
        get() = property.containingClass
    override val container: KDeclarationContainerImpl
        get() = property.container

    override val typeParameterTable: TypeParameterTable
        get() = property.typeParameterTable

    override val dispatchReceiverParameter: ReceiverParameterDescriptor?
        get() = property.dispatchReceiverParameter
    override val extensionReceiverParameter: ReceiverParameterDescriptor?
        get() = property.extensionReceiverParameter
    override val valueParameters: List<ValueParameterDescriptor>
        get() = emptyList()
    override val typeParameters: List<TypeParameterDescriptor>
        get() = property.typeParameters
    override val returnType: KotlinType
        get() = property.returnType
    override val isReal: Boolean
        get() = property.isReal

    override fun hasSynthesizedParameterNames(): Boolean = false

    override val flags: Flags
        get() = property.property.getterFlags
}

internal class PropertySetterDescriptorImpl(
    override val property: PropertyDescriptorImpl
) : AbstractFunctionDescriptor(), PropertySetterDescriptor {
    override val name: Name
        get() = TODO() // TODO: shouldn't be called
    override val methodForAnnotations: JvmMethodSignature?
        get() = property.property.setterSignature

    override val module: ModuleDescriptor
        get() = property.module
    override val containingClass: ClassDescriptorImpl?
        get() = property.containingClass
    override val container: KDeclarationContainerImpl
        get() = property.container

    override val typeParameterTable: TypeParameterTable
        get() = property.typeParameterTable

    override val dispatchReceiverParameter: ReceiverParameterDescriptor?
        get() = property.dispatchReceiverParameter
    override val extensionReceiverParameter: ReceiverParameterDescriptor?
        get() = property.extensionReceiverParameter
    override val valueParameters: List<ValueParameterDescriptor> =
        listOf(PropertySetterParameterDescriptor(property.property.setterParameter, this))
    override val typeParameters: List<TypeParameterDescriptor>
        get() = property.typeParameters
    override val returnType: KotlinType
        get() = module.findClass("kotlin/Unit")!!.defaultType // TODO: use stdlib module?
    override val isReal: Boolean
        get() = property.isReal

    override fun hasSynthesizedParameterNames(): Boolean = false

    override val flags: Flags
        get() = property.property.setterFlags
}

internal class TypeParameterDescriptorImpl(
    private val typeParameter: KmTypeParameter,
    private val module: ModuleDescriptor,
    override val containingDeclaration: DeclarationDescriptor,
    private val typeParameterTable: TypeParameterTable
) : TypeParameterDescriptor {
    internal val id: Int get() = typeParameter.id

    override val name: Name
        get() = typeParameter.name
    override val annotations: Annotations
        get() = TODO() // typeParameter.annotations

    override val upperBounds: List<KotlinType>
        get() = typeParameter.upperBounds.map { it.toKotlinType(module, typeParameterTable) }
            .ifEmpty { listOf(KotlinBuiltInsImpl.anyNType) }

    override val variance: KVariance
        get() = typeParameter.variance.toVariance()
    override val isReified: Boolean
        get() = Flag.TypeParameter.IS_REIFIED(typeParameter.flags)

    override fun equals(other: Any?): Boolean =
        other is TypeParameterDescriptor && name == other.name && containingDeclaration == other.containingDeclaration

    override fun hashCode(): Int =
        name.hashCode() * 31 + containingDeclaration.hashCode()
}

internal class ValueParameterDescriptorImpl(
    private val valueParameter: KmValueParameter,
    override val containingDeclaration: AbstractFunctionDescriptor,
    private val index: Int,
) : ValueParameterDescriptor {
    override val name: Name
        get() = valueParameter.name
    override val annotations: Annotations
        get() {
            val callable = containingDeclaration.methodForAnnotations?.let { (name, desc) ->
                containingDeclaration.container.findMethodBySignature(name, desc)
            } ?: return Annotations.EMPTY
            return JvmAnnotations(callable.parameterAnnotations[index].toList())
        }

    override val type: KotlinType
        // TODO: fix nullability in kotlinx-metadata
        get() = valueParameter.type!!.toKotlinType(containingDeclaration.module, containingDeclaration.typeParameterTable)
    override val declaresDefaultValue: Boolean
        get() = Flag.ValueParameter.DECLARES_DEFAULT_VALUE(valueParameter.flags)
    override val varargElementType: KotlinType?
        get() = valueParameter.varargElementType?.toKotlinType(containingDeclaration.module, containingDeclaration.typeParameterTable)
}

internal class PropertySetterParameterDescriptor(
    private val parameter: KmValueParameter?,
    private val setter: PropertySetterDescriptorImpl,
) : ValueParameterDescriptor {
    override val name: Name
        get() = parameter?.name ?: "<set-?>"
    override val annotations: Annotations
        get() {
            val setter = setter.methodForAnnotations?.let { (name, desc) ->
                setter.container.findMethodBySignature(name, desc)
            } ?: return Annotations.EMPTY
            return JvmAnnotations(setter.parameterAnnotations.single().toList())
        }

    override val containingDeclaration: CallableMemberDescriptor
        get() = setter
    override val type: KotlinType
        get() = setter.property.returnType
    override val declaresDefaultValue: Boolean
        get() = false
    override val varargElementType: KotlinType?
        get() = null
}

// TODO
private fun KmType.toKotlinType(module: ModuleDescriptor, typeParameterTable: TypeParameterTable): KotlinType {
    val classifier = classifier.let { classifier ->
        when (classifier) {
            is KmClassifier.Class -> module.findClass(classifier.name) ?: TODO(classifier.name)
            is KmClassifier.TypeParameter -> typeParameterTable.get(classifier.id)
            is KmClassifier.TypeAlias -> TODO()
        }
    }
    return KotlinType(
        classifier,
        generateSequence(this, KmType::outerType).flatMap(KmType::arguments).map { (variance, type) ->
            TypeProjection(
                type?.toKotlinType(module, typeParameterTable) ?: KotlinBuiltInsImpl.anyType,
                variance == null,
                variance?.toVariance() ?: KVariance.OUT /* TODO: verify */
            )
        }.toList(),
        Flag.Type.IS_NULLABLE(flags),
        SerializedAnnotations(module, annotations),
    )
}

internal class TypeParameterTable(
    val typeParameters: List<TypeParameterDescriptorImpl>,
    private val parent: TypeParameterTable? = null
) {
    private fun getOrNull(id: Int): TypeParameterDescriptor? =
        typeParameters.find { it.id == id } ?: parent?.getOrNull(id)

    fun get(id: Int): TypeParameterDescriptor =
        getOrNull(id) ?: throw KotlinReflectionInternalError("Unknown type parameter with id=$id")

    companion object {
        @JvmField
        val EMPTY = TypeParameterTable(emptyList())
    }
}

private fun KmVariance.toVariance(): KVariance =
    when (this) {
        KmVariance.INVARIANT -> KVariance.INVARIANT
        KmVariance.IN -> KVariance.IN
        KmVariance.OUT -> KVariance.OUT
    }

private fun Flags.toVisibility(): DescriptorVisibility? =
    when {
        Flag.Common.IS_PRIVATE(this) -> DescriptorVisibility.PRIVATE
        Flag.Common.IS_PRIVATE_TO_THIS(this) -> DescriptorVisibility.PRIVATE_TO_THIS
        Flag.Common.IS_INTERNAL(this) -> DescriptorVisibility.INTERNAL
        Flag.Common.IS_PROTECTED(this) -> DescriptorVisibility.PROTECTED
        Flag.Common.IS_PUBLIC(this) -> DescriptorVisibility.PUBLIC
        Flag.Common.IS_LOCAL(this) -> null
        else -> throw KotlinReflectionInternalError("Declaration with unknown visibility")
    }
