/*
 * Copyright 2010-2015 JetBrains s.r.o.
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

import kotlinx.metadata.jvm.JvmMethodSignature
import org.jetbrains.kotlin.builtins.JavaToKotlinClassMap
import org.jetbrains.kotlin.builtins.JvmPrimitiveType
import org.jetbrains.kotlin.builtins.PrimitiveType
import org.jetbrains.kotlin.builtins.StandardNames
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor
import org.jetbrains.kotlin.descriptors.FunctionDescriptor
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.misc.classId
import org.jetbrains.kotlin.misc.desc
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import java.lang.reflect.Constructor
import java.lang.reflect.Method

internal sealed class JvmFunctionSignature {
    abstract fun asString(): String

    class KotlinFunction(val signature: JvmMethodSignature) : JvmFunctionSignature() {
        private val _signature = signature.asString()

        val methodName: String get() = signature.name
        val methodDesc: String get() = signature.desc

        override fun asString(): String = _signature
    }

    class KotlinConstructor(val signature: JvmMethodSignature) : JvmFunctionSignature() {
        private val _signature = signature.asString()

        val constructorDesc: String get() = signature.desc

        override fun asString(): String = _signature
    }

    class JavaMethod(val method: Method) : JvmFunctionSignature() {
        override fun asString(): String = method.signature
    }

    class JavaConstructor(val constructor: Constructor<*>) : JvmFunctionSignature() {
        override fun asString(): String =
            constructor.parameterTypes.joinToString(separator = "", prefix = "<init>(", postfix = ")V") { it.desc }
    }

    class FakeJavaAnnotationConstructor(val jClass: Class<*>) : JvmFunctionSignature() {
        // Java annotations do not impose any order of methods inside them, so we consider them lexicographic here for stability
        val methods = jClass.declaredMethods.sortedBy { it.name }

        override fun asString(): String =
            methods.joinToString(separator = "", prefix = "<init>(", postfix = ")V") { it.returnType.desc }
    }
}

data class JvmPropertySignature(val signature: String) {
    fun asString(): String = signature
}

private val Method.signature: String
    get() = name +
            parameterTypes.joinToString(separator = "", prefix = "(", postfix = ")") { it.desc } +
            returnType.desc

internal object RuntimeTypeMapper {
    private val JAVA_LANG_VOID = ClassId.topLevel(FqName("java.lang.Void"))

    fun mapSignature(possiblySubstitutedFunction: FunctionDescriptor): JvmFunctionSignature {
        val function = possiblySubstitutedFunction

        if (isKnownBuiltInFunction(function)) {
            TODO()
            // return mapJvmFunctionSignature(function)
        }

        // throw KotlinReflectionInternalError("Unknown origin of $function (${function.javaClass})")

        TODO()
    }

    fun mapPropertySignature(possiblyOverriddenProperty: PropertyDescriptor): JvmPropertySignature {
        TODO()
    }

    private fun isKnownBuiltInFunction(descriptor: FunctionDescriptor): Boolean {
        // if (DescriptorFactory.isEnumValueOfMethod(descriptor) || DescriptorFactory.isEnumValuesMethod(descriptor)) return true

        // if (descriptor.name == CloneableClassScope.CLONE_NAME && descriptor.valueParameters.isEmpty()) return true

        return false
    }

    private fun mapName(descriptor: CallableMemberDescriptor): String =
        descriptor.name

    fun mapJvmClassToKotlinClassId(klass: Class<*>): ClassId {
        if (klass.isArray) {
            klass.componentType.primitiveType?.let {
                return ClassId(FqName("kotlin"), it.arrayTypeName)
            }
            return ClassId.topLevel(StandardNames.FQ_NAMES.array)
        }

        if (klass == Void.TYPE) return JAVA_LANG_VOID

        klass.primitiveType?.let {
            return ClassId(StandardNames.BUILT_INS_PACKAGE_FQ_NAME, it.typeName)
        }

        val classId = klass.classId
        if (!classId.isLocal) {
            JavaToKotlinClassMap.mapJavaToKotlin(classId.asSingleFqName())?.let { return it }
        }

        return classId
    }

    private val Class<*>.primitiveType: PrimitiveType?
        get() = if (isPrimitive) JvmPrimitiveType.get(simpleName).primitiveType else null
}
