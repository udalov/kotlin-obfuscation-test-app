package org.jetbrains.kotlin.types

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor
import org.jetbrains.kotlin.descriptors.annotations.Annotated
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import kotlin.reflect.KVariance

class KotlinType(
    val descriptor: ClassifierDescriptor,
    val arguments: List<TypeProjection>,
    val isMarkedNullable: Boolean,
    override val annotations: Annotations
) : Annotated {
    fun render(): String =
        TODO()
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
