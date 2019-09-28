package org.jetbrains.kotlin.types

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
import org.jetbrains.kotlin.descriptors.annotations.Annotated
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import kotlin.reflect.KVariance

class KotlinType(
    val descriptor: ClassifierDescriptor,
    val arguments: List<TypeProjection>,
    val isMarkedNullable: Boolean,
    override val annotations: Annotations
) : Annotated {
    fun render(): String = buildString {
        when (descriptor) {
            is ClassDescriptor -> append(descriptor.classId.asSingleFqName().asString())
            is TypeParameterDescriptor -> append(descriptor.name)
            else -> TODO(descriptor.toString())
        }
        if (arguments.isNotEmpty()) {
            arguments.joinTo(this, prefix = "<", postfix = ">") {
                if (it.isStarProjection) "*"
                else when (it.projectionKind) {
                    KVariance.INVARIANT -> it.type.render()
                    KVariance.IN -> "in " + it.type.render()
                    KVariance.OUT -> "out " + it.type.render()
                }
            }
        }
        if (isMarkedNullable) {
            append("?")
        }
    }
}

class TypeProjection(
    val type: KotlinType,
    val isStarProjection: Boolean,
    val projectionKind: KVariance
)

fun KotlinType.isNullableType(): Boolean =
    // TODO: TypeUtils.isNullableType
    isMarkedNullable

fun KotlinType.isInlineClassType(): Boolean =
    // TODO
    (descriptor as? ClassDescriptor)?.isInline == true
