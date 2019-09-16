package org.jetbrains.kotlin.descriptors

import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.KotlinType

class ReceiverParameterDescriptorImpl(
    override val type: KotlinType
) : ReceiverParameterDescriptor {
    override val containingDeclaration: CallableMemberDescriptor
        get() = error("Shouldn't be called")

    override val annotations: Annotations
        get() = Annotations.EMPTY

    override val name: Name
        get() = "<this>"
}
