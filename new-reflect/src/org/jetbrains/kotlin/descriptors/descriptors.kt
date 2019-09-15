package org.jetbrains.kotlin.descriptors

import org.jetbrains.kotlin.descriptors.annotations.Annotated
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.Variance

interface DeclarationDescriptor : Annotated {
    val name: Name
    val visibility: DescriptorVisibility
}

interface ParameterDescriptor : DeclarationDescriptor {
    val containingDeclaration: CallableMemberDescriptor
    val type: KotlinType
}

interface ValueParameterDescriptor : ParameterDescriptor {
    val declaresDefaultValue: Boolean
    val varargElementType: KotlinType?
}

interface ReceiverParameterDescriptor : ParameterDescriptor

interface CallableMemberDescriptor : DeclarationDescriptor {
    val containingDeclaration: ClassDescriptor

    val dispatchReceiverParameter: ReceiverParameterDescriptor?
    val extensionReceiverParameter: ReceiverParameterDescriptor?
    val valueParameters: List<ValueParameterDescriptor>
    val typeParameters: List<TypeParameterDescriptor>

    val returnType: KotlinType

    val isExternal: Boolean

    val modality: Modality
    val isReal: Boolean
    fun hasSynthesizedParameterNames(): Boolean
    fun render(): String
}

interface FunctionDescriptor : CallableMemberDescriptor {
    val isInline: Boolean
    val isOperator: Boolean
    val isInfix: Boolean
    val isSuspend: Boolean
}

interface PropertyDescriptor : CallableMemberDescriptor {
    val isVar: Boolean
    val isLateInit: Boolean
    val isConst: Boolean
    val isDelegated: Boolean

    val getter: PropertyGetterDescriptor?
    val setter: PropertySetterDescriptor?
}

interface PropertyAccessorDescriptor : FunctionDescriptor
interface PropertyGetterDescriptor : PropertyAccessorDescriptor
interface PropertySetterDescriptor : PropertyAccessorDescriptor

interface ConstructorDescriptor : FunctionDescriptor {
    val isPrimary: Boolean
    val constructedClass: ClassDescriptor
}

interface ClassifierDescriptor : DeclarationDescriptor
interface TypeParameterDescriptor : ClassifierDescriptor {
    val upperBounds: List<KotlinType>
    val variance: Variance
    val isReified: Boolean
}

interface TypeAliasDescriptor : ClassifierDescriptor
interface ClassDescriptor : ClassifierDescriptor {
    val kind: ClassKind
    val modality: Modality
    val isData: Boolean
    val isInner: Boolean
    val isInline: Boolean
    val isCompanionObject: Boolean
    val isFun: Boolean

    val defaultType: KotlinType

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

interface MemberScope {
    fun getContributedDescriptors(): List<DeclarationDescriptor>
    fun getContributedVariables(name: Name): List<PropertyDescriptor>
    fun getContributedFunctions(name: Name): List<FunctionDescriptor>
}

enum class ClassKind {
    CLASS, INTERFACE, ENUM_CLASS, ENUM_ENTRY, ANNOTATION_CLASS, OBJECT
}

enum class DescriptorVisibility {
    PRIVATE,
    PRIVATE_TO_THIS,
    INTERNAL,
    PROTECTED,
    PUBLIC,
    // TODO
}

enum class Modality {
    FINAL,
    SEALED,
    OPEN,
    ABSTRACT,
}

object DescriptorVisibilities {
    val PRIVATE = DescriptorVisibility.PRIVATE
    val PRIVATE_TO_THIS = DescriptorVisibility.PRIVATE_TO_THIS
    val INTERNAL = DescriptorVisibility.INTERNAL
    val PROTECTED = DescriptorVisibility.PROTECTED
    val PUBLIC = DescriptorVisibility.PUBLIC

    fun compare(a: DescriptorVisibility, b: DescriptorVisibility): Int? {
        TODO()
    }
}

fun ValueParameterDescriptor.declaresOrInheritsDefaultValue(): Boolean {
    // TODO
    /*
    return DFS.ifAny(
        listOf(this),
        { current -> current.overriddenDescriptors.map(ValueParameterDescriptor::getOriginal) },
        ValueParameterDescriptor::declaresDefaultValue
    )*/
    return declaresDefaultValue
}

fun shouldHideConstructorDueToInlineClassTypeValueParameters(function: FunctionDescriptor): Boolean {
    // TODO
    return false
}

fun createDefaultGetter(property: PropertyDescriptor, annotations: Annotations): PropertyGetterDescriptor {
    TODO()
}

fun createDefaultSetter(property: PropertyDescriptor, annotations: Annotations, parameterAnnotations: Annotations): PropertySetterDescriptor {
    TODO()
}

fun CallableMemberDescriptor.isGetterOfUnderlyingPropertyOfInlineClass(): Boolean {
    // TODO
    return false
}

fun CallableMemberDescriptor.isUnderlyingPropertyOfInlineClass(): Boolean {
    // TODO
    return false
}

val ClassifierDescriptor?.classId: ClassId?
    get() = TODO()

val ClassifierDescriptor.parameters: List<TypeParameterDescriptor>
    // TODO
    get() = (this as? ClassDescriptor)?.declaredTypeParameters.orEmpty()
