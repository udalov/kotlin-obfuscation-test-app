package org.jetbrains.kotlin.descriptors

import kotlinx.metadata.ClassName
import org.jetbrains.kotlin.descriptors.annotations.Annotated
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.TypeProjection
import kotlin.reflect.KVariance
import kotlin.reflect.jvm.internal.KClassImpl
import kotlin.reflect.jvm.internal.KDeclarationContainerImpl

internal interface ModuleDescriptor {
    fun findClass(name: ClassName): ClassDescriptor?
}

internal interface DeclarationDescriptor : Annotated {
    val name: Name
}

internal interface ParameterDescriptor : DeclarationDescriptor {
    val containingDeclaration: CallableMemberDescriptor
    val type: KotlinType
}

internal interface ValueParameterDescriptor : ParameterDescriptor {
    val declaresDefaultValue: Boolean
    val varargElementType: KotlinType?
}

internal interface ReceiverParameterDescriptor : ParameterDescriptor

internal interface CallableMemberDescriptor : DeclarationDescriptor {
    val module: ModuleDescriptor
    val containingClass: ClassDescriptor?
    val container: KDeclarationContainerImpl

    val dispatchReceiverParameter: ReceiverParameterDescriptor?
    val extensionReceiverParameter: ReceiverParameterDescriptor?
    val valueParameters: List<ValueParameterDescriptor>
    val typeParameters: List<TypeParameterDescriptor>

    val returnType: KotlinType

    val isFinal: Boolean
    val isOpen: Boolean
    val isAbstract: Boolean
    val isExternal: Boolean
    val visibility: DescriptorVisibility?

    val isReal: Boolean
    fun hasSynthesizedParameterNames(): Boolean
    fun render(): String
}

internal interface FunctionDescriptor : CallableMemberDescriptor {
    val isInline: Boolean
    val isOperator: Boolean
    val isInfix: Boolean
    val isSuspend: Boolean
}

internal interface PropertyDescriptor : CallableMemberDescriptor {
    val isVar: Boolean
    val isLateInit: Boolean
    val isConst: Boolean
    val isDelegated: Boolean

    val getter: PropertyGetterDescriptor?
    val setter: PropertySetterDescriptor?
}

internal interface PropertyAccessorDescriptor : FunctionDescriptor
internal interface PropertyGetterDescriptor : PropertyAccessorDescriptor
internal interface PropertySetterDescriptor : PropertyAccessorDescriptor

internal interface ConstructorDescriptor : FunctionDescriptor {
    val isPrimary: Boolean
    val constructedClass: ClassDescriptor
}

internal interface ClassifierDescriptor : DeclarationDescriptor {
    override fun equals(other: Any?): Boolean
    override fun hashCode(): Int
}

internal interface TypeParameterDescriptor : ClassifierDescriptor {
    val containingDeclaration: DeclarationDescriptor
    val upperBounds: List<KotlinType>
    val variance: KVariance
    val isReified: Boolean
}

internal interface TypeAliasDescriptor : ClassifierDescriptor

internal interface ClassDescriptor : ClassifierDescriptor {
    val classId: ClassId
    val module: ModuleDescriptor

    // Note that it's a KClass for `[Ljava.lang.Object;` in case this class descriptor represents kotlin/Array.
    val kClass: KClassImpl<*>

    val isInterface: Boolean
    val isAnnotationClass: Boolean
    val isObject: Boolean

    val isFinal: Boolean
    val isOpen: Boolean
    val isAbstract: Boolean
    val isSealed: Boolean
    val visibility: DescriptorVisibility?

    val isData: Boolean
    val isInner: Boolean
    val isInline: Boolean
    val isCompanionObject: Boolean
    val isFun: Boolean

    val declaredTypeParameters: List<TypeParameterDescriptor>
    val supertypes: List<KotlinType>

    val containingClass: ClassDescriptor?
    val thisAsReceiverParameter: ReceiverParameterDescriptor

    val constructors: List<ConstructorDescriptor>
    val nestedClasses: List<ClassDescriptor>
    val sealedSubclasses: List<ClassDescriptor>
    val memberScope: MemberScope
    val staticScope: MemberScope
}

internal interface FileDescriptor {
    val scope: MemberScope
}

internal class MemberScope(
    val properties: List<PropertyDescriptor>,
    val functions: List<FunctionDescriptor>
)

internal fun MemberScope.getFunctions(name: Name): List<FunctionDescriptor> =
    functions.filter { it.name == name }

internal fun MemberScope.getProperties(name: Name): List<PropertyDescriptor> =
    properties.filter { it.name == name }

enum class DescriptorVisibility {
    PRIVATE,
    PRIVATE_TO_THIS,
    INTERNAL,
    PROTECTED,
    PUBLIC,
    // TODO
    ;

    fun isPrivate(): Boolean =
        this == PRIVATE || this == PRIVATE_TO_THIS
}

enum class Modality {
    FINAL,
    SEALED,
    OPEN,
    ABSTRACT,
}

object DescriptorVisibilities {
    fun compare(a: DescriptorVisibility, b: DescriptorVisibility): Int? {
        TODO()
    }
}

internal fun ValueParameterDescriptor.declaresOrInheritsDefaultValue(): Boolean {
    // TODO
    /*
    return DFS.ifAny(
        listOf(this),
        { current -> current.overriddenDescriptors.map(ValueParameterDescriptor::getOriginal) },
        ValueParameterDescriptor::declaresDefaultValue
    )*/
    return declaresDefaultValue
}

internal fun shouldHideConstructorDueToInlineClassTypeValueParameters(function: FunctionDescriptor): Boolean {
    // TODO
    return false
}

internal fun createDefaultGetter(property: PropertyDescriptor, annotations: Annotations): PropertyGetterDescriptor {
    TODO()
}

internal fun createDefaultSetter(property: PropertyDescriptor, annotations: Annotations, parameterAnnotations: Annotations): PropertySetterDescriptor {
    TODO()
}

internal fun CallableMemberDescriptor.isGetterOfUnderlyingPropertyOfInlineClass(): Boolean {
    // TODO
    return false
}

internal fun CallableMemberDescriptor.isUnderlyingPropertyOfInlineClass(): Boolean {
    // TODO
    return false
}

internal val ClassifierDescriptor.parameters: List<TypeParameterDescriptor>
    // TODO
    get() = (this as? ClassDescriptor)?.declaredTypeParameters.orEmpty()

internal val ClassifierDescriptor.defaultType: KotlinType
    get() = KotlinType(
        this,
        (this as? ClassDescriptor)?.parameters?.map {
            TypeProjection(it.defaultType, false, KVariance.INVARIANT)
        }.orEmpty(),
        false,
        Annotations.EMPTY
    )
