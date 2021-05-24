package org.jetbrains.kotlin.types

import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
import org.jetbrains.kotlin.descriptors.parameters

internal class TypeSubstitutor(private val mapping: Map<TypeParameterDescriptor, TypeProjection>) {
    fun substitute(type: KotlinType): KotlinType {
        val classifier = type.descriptor
        val mapped = mapping[classifier]
        if (mapped != null) return mapped.type
        if (classifier.parameters.isEmpty() || mapping.isEmpty()) return type
        return KotlinType(
            classifier,
            type.arguments.map(::substitute),
            type.isMarkedNullable,
            type.annotations,
        )
    }

    private fun substitute(projection: TypeProjection): TypeProjection =
        TypeProjection(substitute(projection.type), projection.isStarProjection, projection.projectionKind)

    companion object {
        fun create(type: KotlinType): TypeSubstitutor {
            val parameters = type.descriptor.parameters
            return TypeSubstitutor(parameters.indices.associate { i -> parameters[i] to type.arguments[i] })
        }
    }
}
