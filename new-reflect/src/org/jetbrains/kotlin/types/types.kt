package org.jetbrains.kotlin.types

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor
import org.jetbrains.kotlin.descriptors.annotations.Annotated

interface KotlinType : Annotated {
    val descriptor: ClassifierDescriptor
    val arguments: List<TypeProjection>
    val isMarkedNullable: Boolean

    fun render(): String
}

interface TypeProjection {
    val type: KotlinType
    val isStarProjection: Boolean
    val projectionKind: Variance
}

enum class Variance {
    INVARIANT, IN_VARIANCE, OUT_VARIANCE,
}

fun KotlinType.isNullableType(): Boolean =
    // TODO: TypeUtils.isNullableType
    isMarkedNullable

fun KotlinType.isInlineClassType(): Boolean =
    // TODO
    (descriptor as? ClassDescriptor)?.isInline == true
