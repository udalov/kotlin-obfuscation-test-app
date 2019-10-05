package org.jetbrains.kotlin.descriptors

import kotlinx.metadata.ClassName
import org.jetbrains.kotlin.descriptors.annotations.Annotated
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.KotlinType
import org.jetbrains.kotlin.types.TypeProjection
import kotlin.reflect.KVariance

interface ModuleDescriptor {
    fun findClass(name: ClassName): ClassDescriptor?
}

interface DeclarationDescriptor : Annotated {
    val name: Name
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
    val module: ModuleDescriptor
    val containingClass: ClassDescriptor?

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
    val variance: KVariance
    val isReified: Boolean
}

interface TypeAliasDescriptor : ClassifierDescriptor

interface ClassDescriptor : ClassifierDescriptor {
    val classId: ClassId
    val module: ModuleDescriptor
    val jClass: Class<*>

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

interface FileDescriptor {
    val scope: MemberScope
}

class MemberScope(
    val properties: List<PropertyDescriptor>,
    val functions: List<FunctionDescriptor>
)

fun MemberScope.getFunctions(name: Name): List<FunctionDescriptor> =
    functions.filter { it.name == name }

fun MemberScope.getProperties(name: Name): List<PropertyDescriptor> =
    properties.filter { it.name == name }

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

val ClassifierDescriptor.parameters: List<TypeParameterDescriptor>
    // TODO
    get() = (this as? ClassDescriptor)?.declaredTypeParameters.orEmpty()

val ClassifierDescriptor.defaultType: KotlinType
    get() = KotlinType(
        this,
        (this as? ClassDescriptor)?.parameters?.map {
            TypeProjection(it.defaultType, false, KVariance.INVARIANT)
        }.orEmpty(),
        false,
        Annotations.EMPTY
    )
