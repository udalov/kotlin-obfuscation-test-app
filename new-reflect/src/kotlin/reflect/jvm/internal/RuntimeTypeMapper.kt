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

import kotlinx.metadata.jvm.*
import org.jetbrains.kotlin.builtins.*
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.misc.classId
import org.jetbrains.kotlin.misc.desc
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

sealed class JvmFunctionSignature {
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

sealed class JvmPropertySignature {
    /**
     * Returns the JVM signature of the getter of this property. In case the property doesn't have a getter,
     * constructs the signature of its imaginary default getter. See CallableReference#getSignature for more information
     */
    abstract fun asString(): String

    class KotlinProperty(
        private val descriptor: PropertyDescriptorImpl,
        val fieldSignature: JvmFieldSignature?,
        val getterSignature: JvmMethodSignature?,
        val setterSignature: JvmMethodSignature?
    ) : JvmPropertySignature() {
        private val string: String = getterSignature?.asString() ?: run {
            val (name, desc) = fieldSignature ?: throw KotlinReflectionInternalError("No field signature for property: $descriptor")
            JvmAbi.getterName(name) + getManglingSuffix() + "()" + desc
        }

        private fun getManglingSuffix(): String {
            // TODO
/*
            val containingDeclaration = descriptor.containingDeclaration
            if (descriptor.visibility == Visibilities.INTERNAL && containingDeclaration is DeserializedClassDescriptor) {
                val classProto = containingDeclaration.classProto
                val moduleName = classProto.getExtensionOrNull(JvmProtoBuf.classModuleName)?.let(nameResolver::getString)
                    ?: JvmProtoBufUtil.DEFAULT_MODULE_NAME
                return "$" + NameUtils.sanitizeAsJavaIdentifier(moduleName)
            }
            if (descriptor.visibility == Visibilities.PRIVATE && containingDeclaration is PackageFragmentDescriptor) {
                val packagePartSource = (descriptor as DeserializedPropertyDescriptor).containerSource
                if (packagePartSource is JvmPackagePartSource && packagePartSource.facadeClassName != null) {
                    return "$" + packagePartSource.simpleName.asString()
                }
            }
*/

            return ""
        }

        override fun asString(): String = string
    }

    class JavaMethodProperty(val getterMethod: Method, val setterMethod: Method?) : JvmPropertySignature() {
        override fun asString(): String = getterMethod.signature
    }

    class JavaField(val field: Field) : JvmPropertySignature() {
        override fun asString(): String =
            JvmAbi.getterName(field.name) + "()" + field.type.desc
    }

    class MappedKotlinProperty(
        val getterSignature: JvmFunctionSignature.KotlinFunction,
        val setterSignature: JvmFunctionSignature.KotlinFunction?
    ) : JvmPropertySignature() {
        override fun asString(): String = getterSignature.asString()
    }
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

        // TODO: AbstractFunctionDescriptor.signature

        if (function is FunctionDescriptorImpl) {
            return JvmFunctionSignature.KotlinFunction(function.function.signature ?: error("No signature for ${function.render()}"))
        }
        if (function is PropertyGetterDescriptorImpl) {
            return JvmFunctionSignature.KotlinFunction(function.property.property.getterSignature ?: error("No getter signature for ${function.render()}"))
        }
        if (function is PropertySetterDescriptorImpl) {
            return JvmFunctionSignature.KotlinFunction(function.property.property.setterSignature ?: error("No setter signature for ${function.render()}"))
        }

        // throw KotlinReflectionInternalError("Unknown origin of $function (${function.javaClass})")

        TODO()
    }

    fun mapPropertySignature(possiblyOverriddenProperty: PropertyDescriptor): JvmPropertySignature {
        val property = possiblyOverriddenProperty

        if (property is PropertyDescriptorImpl) {
            return JvmPropertySignature.KotlinProperty(
                property,
                property.property.fieldSignature,
                property.property.getterSignature,
                property.property.setterSignature
            )
        }

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
