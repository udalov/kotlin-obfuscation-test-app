package org.jetbrains.kotlin.descriptors

import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.KotlinType

internal fun <D : CallableMemberDescriptor> overrides(derived: D, base: D): Boolean {
    require(derived.name == base.name) { "Names should be equal: $derived, $base" }
    if (derived is PropertyDescriptor && base is PropertyDescriptor) {
        // TODO (!)
        return true
    } else if (derived is FunctionDescriptor && base is FunctionDescriptor) {
        // TODO (!)
        return true
    } else {
        error("Unknown members: $derived, $base")
    }
}

internal fun <D : CallableMemberDescriptor> addFakeOverrides(
    klass: ClassDescriptor,
    realMembers: List<D>,
    selector: (ClassDescriptor) -> List<D>,
    createFakeOverride: (List<D>) -> D
): List<D> {
    val fromDerived = realMembers.groupBy(CallableMemberDescriptor::name)

    val fromBase = mutableMapOf<Name, MutableList<D>>()
    for (supertype in klass.supertypes) {
        val superclass = supertype.descriptor as? ClassDescriptor ?: continue
        val allMembers = selector(superclass).filter { it.visibility?.isPrivate() != true }
        for ((name, members) in allMembers.groupBy(CallableMemberDescriptor::name)) {
            fromBase.getOrPut(name) { ArrayList(1) }.addAll(members)
        }
    }

    val result = mutableListOf<D>()
    for ((name, members) in fromBase) {
        val notOverridden = members.filterNot { baseMember ->
            fromDerived[name].orEmpty().any { derivedMember -> overrides(derivedMember, baseMember)}
        }
        if (notOverridden.isEmpty()) continue
        // TODO (!): if > 1, group by extension receiver and parameter types
        // TODO: filterOutOverridden (so that `isAbstract = notOverridden.all { it.isAbstract }` would work)
        result.add(createFakeOverride(notOverridden))
    }

    return result
}

internal fun addPropertyFakeOverrides(klass: ClassDescriptor, realProperties: List<PropertyDescriptor>): List<PropertyDescriptor> =
    addFakeOverrides(klass, realProperties, { it.memberScope.properties }) { members ->
        val representative = members.first()
        // TODO (!): type substitution
        FakeOverridePropertyDescriptor(
            klass.module,
            klass,
            representative.extensionReceiverParameter,
            representative.valueParameters,
            representative.typeParameters,
            representative.returnType,
            members
        )
    }

internal fun addFunctionFakeOverrides(klass: ClassDescriptor, realFunctions: List<FunctionDescriptor>): List<FunctionDescriptor> =
    addFakeOverrides(klass, realFunctions, { it.memberScope.functions }) { members ->
        // TODO (!): type substitution
        val representative = members.first()
        FakeOverrideFunctionDescriptor(
            klass.module,
            klass,
            representative.extensionReceiverParameter,
            representative.valueParameters,
            representative.typeParameters,
            representative.returnType,
            members
        )
    }

internal abstract class FakeOverrideCallableMemberDescriptor(
    override val module: ModuleDescriptor,
    override val containingClass: ClassDescriptor,
    override val extensionReceiverParameter: ReceiverParameterDescriptor?,
    override val valueParameters: List<ValueParameterDescriptor>,
    override val typeParameters: List<TypeParameterDescriptor>,
    override val returnType: KotlinType,
    val overridden: List<CallableMemberDescriptor>
) : CallableMemberDescriptor {
    init {
        require(overridden.isNotEmpty())
    }

    override val name: Name
        get() = overridden.first().name
    override val visibility: DescriptorVisibility?
        get() = overridden.first().visibility // TODO
    override val annotations: Annotations
        get() = overridden.first().annotations // TODO?

    override val dispatchReceiverParameter: ReceiverParameterDescriptor
        get() = containingClass.thisAsReceiverParameter

    override val isFinal: Boolean
        get() = overridden.any(CallableMemberDescriptor::isFinal)
    override val isAbstract: Boolean
        get() = overridden.all(CallableMemberDescriptor::isAbstract)
    override val isOpen: Boolean
        get() = !isFinal && !isAbstract
    override val isExternal: Boolean
        get() = overridden.first().isExternal // TODO?

    override val isReal: Boolean
        get() = false
    override fun hasSynthesizedParameterNames(): Boolean =
        overridden.first().hasSynthesizedParameterNames() // TODO?

    override fun render(): String = TODO()
}

internal class FakeOverrideFunctionDescriptor(
    module: ModuleDescriptor,
    containingClass: ClassDescriptor,
    extensionReceiverParameter: ReceiverParameterDescriptor?,
    valueParameters: List<ValueParameterDescriptor>,
    typeParameters: List<TypeParameterDescriptor>,
    returnType: KotlinType,
    overridden: List<FunctionDescriptor>
) : FakeOverrideCallableMemberDescriptor(
    module, containingClass, extensionReceiverParameter, valueParameters, typeParameters, returnType, overridden
), FunctionDescriptor {
    @Suppress("UNCHECKED_CAST")
    val overriddenFunctions: List<FunctionDescriptor>
        get() = super.overridden as List<FunctionDescriptor>

    override val isInline: Boolean
        get() = overriddenFunctions.first().isInline // TODO?
    override val isOperator: Boolean
        get() = overriddenFunctions.first().isOperator // TODO?
    override val isInfix: Boolean
        get() = overriddenFunctions.first().isInfix // TODO?
    override val isSuspend: Boolean
        get() = overriddenFunctions.first().isSuspend // TODO?
}

internal class FakeOverridePropertyDescriptor(
    module: ModuleDescriptor,
    containingClass: ClassDescriptor,
    extensionReceiverParameter: ReceiverParameterDescriptor?,
    valueParameters: List<ValueParameterDescriptor>,
    typeParameters: List<TypeParameterDescriptor>,
    returnType: KotlinType,
    overridden: List<PropertyDescriptor>
) : FakeOverrideCallableMemberDescriptor(
    module, containingClass, extensionReceiverParameter, valueParameters, typeParameters, returnType, overridden
), PropertyDescriptor {
    @Suppress("UNCHECKED_CAST")
    val overriddenProperties: List<PropertyDescriptor>
        get() = super.overridden as List<PropertyDescriptor>

    override val isVar: Boolean
        get() = overriddenProperties.any(PropertyDescriptor::isVar)
    override val isLateInit: Boolean
        get() = false
    override val isConst: Boolean
        get() = false
    override val isDelegated: Boolean
        get() = false

    override val getter: PropertyGetterDescriptor? = FakeOverridePropertyGetterDescriptor(this)
    override val setter: PropertySetterDescriptor? =
        if (isVar) FakeOverridePropertySetterDescriptor(this) else null
}

// TODO: find out how to reduce duplication with Property{G,S}etterDescriptorImpl

internal class FakeOverridePropertyGetterDescriptor(
    property: FakeOverridePropertyDescriptor
) : FakeOverrideCallableMemberDescriptor(
    property.module,
    property.containingClass,
    property.extensionReceiverParameter,
    property.valueParameters,
    property.typeParameters,
    property.returnType,
    property.overriddenProperties.mapNotNull(PropertyDescriptor::getter)
), PropertyGetterDescriptor {
    @Suppress("UNCHECKED_CAST")
    private val overriddenPropertyGetters: List<PropertyGetterDescriptor>
        get() = super.overridden as List<PropertyGetterDescriptor>

    override val isInline: Boolean
        get() = overriddenPropertyGetters.first().isInline // TODO?
    override val isOperator: Boolean get() = false
    override val isInfix: Boolean get() = false
    override val isSuspend: Boolean get() = false
}

internal class FakeOverridePropertySetterDescriptor(
    property: FakeOverridePropertyDescriptor
) : FakeOverrideCallableMemberDescriptor(
    property.module,
    property.containingClass,
    property.extensionReceiverParameter,
    property.valueParameters,
    property.typeParameters,
    property.returnType,
    property.overriddenProperties.mapNotNull(PropertyDescriptor::setter)
), PropertySetterDescriptor {
    @Suppress("UNCHECKED_CAST")
    private val overriddenPropertySetters: List<PropertySetterDescriptor>
        get() = super.overridden as List<PropertySetterDescriptor>

    override val isInline: Boolean
        get() = overriddenPropertySetters.first().isInline // TODO?
    override val isOperator: Boolean get() = false
    override val isInfix: Boolean get() = false
    override val isSuspend: Boolean get() = false
}
