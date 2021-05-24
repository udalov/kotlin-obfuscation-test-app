package org.jetbrains.kotlin.types

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
import org.jetbrains.kotlin.descriptors.annotations.Annotated
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import kotlin.reflect.KVariance

internal class KotlinType(
    val descriptor: ClassifierDescriptor,
    val arguments: List<TypeProjection>,
    val isMarkedNullable: Boolean,
    override val annotations: Annotations
) : Annotated {
    fun render(): String = buildString {
        when (descriptor) {
            is ClassDescriptor -> renderClassType(descriptor, arguments, 0)
            is TypeParameterDescriptor -> append(descriptor.name)
            else -> TODO(descriptor.toString())
        }
        if (isMarkedNullable) {
            append("?")
        }
    }

    private fun Appendable.renderClassType(descriptor: ClassDescriptor, arguments: List<TypeProjection>, start: Int) {
        val typeParameters = descriptor.declaredTypeParameters.size
        if (descriptor.isInner) {
            renderClassType(descriptor.containingClass!!, arguments, start + typeParameters)
            append(".").append(descriptor.name)
        } else {
            append(descriptor.classId.asSingleFqName().asString())
        }
        if (typeParameters > 0) {
            arguments.subList(start, start + typeParameters).joinTo(this, prefix = "<", postfix = ">") {
                if (it.isStarProjection) "*"
                else when (it.projectionKind) {
                    KVariance.INVARIANT -> it.type.render()
                    KVariance.IN -> "in " + it.type.render()
                    KVariance.OUT -> "out " + it.type.render()
                }
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        // TODO: isSubtypeOf(this, other) && isSubtypeOf(other, this)
        return other is KotlinType &&
            other.descriptor == descriptor &&
            other.arguments == arguments &&
            other.isMarkedNullable == isMarkedNullable
    }

    override fun hashCode(): Int =
        (descriptor.hashCode() * 31 + arguments.hashCode()) * 31 + isMarkedNullable.hashCode()
}

internal class TypeProjection(
    val type: KotlinType,
    val isStarProjection: Boolean,
    val projectionKind: KVariance
) {
    override fun equals(other: Any?): Boolean =
        other is TypeProjection &&
            other.type == type &&
            other.isStarProjection == isStarProjection &&
            other.projectionKind == projectionKind

    override fun hashCode(): Int =
        (type.hashCode() * 31 + isStarProjection.hashCode()) * 31 + projectionKind.hashCode()
}

internal fun KotlinType.isNullableType(): Boolean =
    // This needs to be changed once KT-31545 is fixed.
    isMarkedNullable || (descriptor is TypeParameterDescriptor && descriptor.upperBounds.any { it.isNullableType() })

internal fun KotlinType.isInlineClassType(): Boolean =
    // TODO
    (descriptor as? ClassDescriptor)?.isInline == true
