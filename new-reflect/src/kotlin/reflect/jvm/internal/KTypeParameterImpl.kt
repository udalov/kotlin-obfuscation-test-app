/*
 * Copyright 2010-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kotlin.reflect.jvm.internal

import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor
import kotlin.jvm.internal.TypeParameterReference
import kotlin.reflect.KType
import kotlin.reflect.KTypeParameter
import kotlin.reflect.KVariance

internal class KTypeParameterImpl(
    container: KTypeParameterOwnerImpl?,
    override val descriptor: TypeParameterDescriptor,
) : KTypeParameter, KClassifierImpl {
    override val name: String
        get() = descriptor.name

    override val upperBounds: List<KType> by ReflectProperties.lazySoft { descriptor.upperBounds.map(::KTypeImpl) }

    override val variance: KVariance
        get() = descriptor.variance

    override val isReified: Boolean
        get() = descriptor.isReified

    private val container: KTypeParameterOwnerImpl = container ?: run {
        when (val declaration = descriptor.containingDeclaration) {
            is ClassDescriptor -> declaration.kClass
            is CallableMemberDescriptor -> createKCallable(declaration, declaration.container)
            else -> throw KotlinReflectionInternalError("Unknown type parameter container: $declaration")
        }
    }

    override fun equals(other: Any?) =
        other is KTypeParameterImpl && container == other.container && name == other.name

    override fun hashCode() =
        container.hashCode() * 31 + name.hashCode()

    override fun toString() =
        TypeParameterReference.toString(this)
}
