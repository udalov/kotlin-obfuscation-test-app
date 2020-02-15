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

import kotlinx.metadata.common.KotlinCommonMetadata
import kotlinx.metadata.jvm.KotlinClassMetadata
import org.jetbrains.kotlin.builtins.JavaToKotlinClassMap
import org.jetbrains.kotlin.builtins.JvmAbi
import org.jetbrains.kotlin.builtins.KotlinBuiltIns
import org.jetbrains.kotlin.builtins.KotlinBuiltInsImpl
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.misc.*
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import kotlin.jvm.internal.TypeIntrinsics
import kotlin.reflect.*
import kotlin.reflect.jvm.internal.KDeclarationContainerImpl.MemberBelonginess.DECLARED
import kotlin.reflect.jvm.internal.KDeclarationContainerImpl.MemberBelonginess.INHERITED

internal class KClassImpl<T : Any>(
    override val jClass: Class<T>
) : KDeclarationContainerImpl(), KClass<T>, KClassifierImpl, KTypeParameterOwnerImpl {
    inner class Data {
        val descriptor: ClassDescriptor by ReflectProperties.lazySoft {
            createClassDescriptor() ?: reportUnresolvedClass()
        }

        private fun createClassDescriptor(): ClassDescriptor? {
            // TODO: find out if module is necessary
            val module = ModuleDescriptorImpl(jClass.safeClassLoader)

            val builtinClassId = JavaToKotlinClassMap.mapJavaToKotlin(FqName(jClass.name))
            if (builtinClassId != null) {
                val packageName = builtinClassId.packageFqName
                // kotlin.collections -> kotlin/collections/collections.kotlin_builtins
                val resourcePath = packageName.asString().replace('.', '/') + '/' + packageName.shortName() + ".kotlin_builtins"
                val bytes = Unit::class.java.classLoader.getResourceAsStream(resourcePath)?.readBytes()
                    ?: error("No builtins metadata file found: $resourcePath") // TODO: return null
                val packageFragment = KotlinCommonMetadata.read(bytes)?.toKmModuleFragment()
                    ?: error("Incompatible metadata version: $resourcePath") // TODO
                val kmClass = packageFragment.classes.find { it.name == builtinClassId.asClassName() }
                    ?: error("Built-in class not found: $builtinClassId in $resourcePath")
                return ClassDescriptorImpl(kmClass, module, builtinClassId, this@KClassImpl)
            }

            jClass.readHeader()?.let { header ->
                val kmClass = (KotlinClassMetadata.read(header) as/*TODO*/ KotlinClassMetadata.Class).toKmClass()
                return ClassDescriptorImpl(kmClass, module, jClass.classId, this@KClassImpl)
            }

            TODO(jClass.name)
        }

        val annotations: List<Annotation> by ReflectProperties.lazySoft { descriptor.computeAnnotations() }

        val simpleName: String? by ReflectProperties.lazySoft {
            if (jClass.isAnonymousClass) return@lazySoft null

            val classId = classId
            when {
                classId.isLocal -> calculateLocalClassName(jClass)
                else -> classId.shortClassName
            }
        }

        val qualifiedName: String? by ReflectProperties.lazySoft {
            if (jClass.isAnonymousClass) return@lazySoft null

            val classId = classId
            when {
                classId.isLocal -> null
                else -> classId.asSingleFqName().asString()
            }
        }

        private fun calculateLocalClassName(jClass: Class<*>): String {
            val name = jClass.simpleName
            jClass.enclosingMethod?.let { method ->
                return name.substringAfter(method.name + "$")
            }
            jClass.enclosingConstructor?.let { constructor ->
                return name.substringAfter(constructor.name + "$")
            }
            return name.substringAfter('$')
        }

        @Suppress("UNCHECKED_CAST")
        val constructors: Collection<KFunction<T>> by ReflectProperties.lazySoft {
            constructorDescriptors.map { descriptor ->
                KFunctionImpl(this@KClassImpl, descriptor) as KFunction<T>
            }
        }

        val nestedClasses: Collection<KClass<*>> by ReflectProperties.lazySoft {
            descriptor.nestedClasses.mapNotNull { nestedClass ->
                nestedClass.toJavaClass()?.let { KClassImpl(it) }
            }
        }

        @Suppress("UNCHECKED_CAST")
        val objectInstance: T? by ReflectProperties.lazy {
            val descriptor = descriptor
            if (!descriptor.isObject) return@lazy null

            val field = if (descriptor.isCompanionObject && !CompanionObjectMapping.isMappedIntrinsicCompanionObject(descriptor)) {
                jClass.enclosingClass.getDeclaredField(descriptor.name)
            } else {
                jClass.getDeclaredField(JvmAbi.INSTANCE_FIELD)
            }
            field.get(null) as T
        }

        val typeParameters: List<KTypeParameter> by ReflectProperties.lazySoft {
            descriptor.declaredTypeParameters.map { descriptor -> KTypeParameterImpl(this@KClassImpl, descriptor) }
        }

        val supertypes: List<KType> by ReflectProperties.lazySoft {
            val kotlinTypes = descriptor.supertypes
            val result = ArrayList<KTypeImpl>(kotlinTypes.size)
            kotlinTypes.mapTo(result) { kotlinType ->
                KTypeImpl(kotlinType) {
                    val superClass = kotlinType.descriptor
                    if (superClass !is ClassDescriptor) throw KotlinReflectionInternalError("Supertype not a class: $superClass")

                    val superJavaClass = superClass.toJavaClass()
                            ?: throw KotlinReflectionInternalError("Unsupported superclass of $this: $superClass")

                    if (jClass.superclass == superJavaClass) {
                        jClass.genericSuperclass
                    } else {
                        val index = jClass.interfaces.indexOf(superJavaClass)
                        if (index < 0) throw KotlinReflectionInternalError("No superclass of $this in Java reflection for $superClass")
                        jClass.genericInterfaces[index]
                    }
                }
            }
            if (!KotlinBuiltIns.isSpecialClassWithNoSupertypes(descriptor) && result.all {
                val descriptor = it.type.descriptor as ClassDescriptor
                descriptor.isInterface || descriptor.isAnnotationClass
            }) {
                result += KTypeImpl(KotlinBuiltInsImpl.anyType) { Any::class.java }
            }
            result.compact()
        }

        val sealedSubclasses: List<KClass<out T>> by ReflectProperties.lazySoft {
            descriptor.sealedSubclasses.mapNotNull { subclass ->
                @Suppress("UNCHECKED_CAST")
                val jClass = subclass.toJavaClass() as Class<out T>?
                jClass?.let { KClassImpl(it) }
            }
        }

        val declaredNonStaticMembers: Collection<KCallableImpl<*>>
                by ReflectProperties.lazySoft { getMembers(memberScope, DECLARED) }
        private val declaredStaticMembers: Collection<KCallableImpl<*>>
                by ReflectProperties.lazySoft { getMembers(staticScope, DECLARED) }
        private val inheritedNonStaticMembers: Collection<KCallableImpl<*>>
                by ReflectProperties.lazySoft { getMembers(memberScope, INHERITED) }
        private val inheritedStaticMembers: Collection<KCallableImpl<*>>
                by ReflectProperties.lazySoft { getMembers(staticScope, INHERITED) }

        val allNonStaticMembers: Collection<KCallableImpl<*>>
                by ReflectProperties.lazySoft { declaredNonStaticMembers + inheritedNonStaticMembers }
        val allStaticMembers: Collection<KCallableImpl<*>>
                by ReflectProperties.lazySoft { declaredStaticMembers + inheritedStaticMembers }
        val declaredMembers: Collection<KCallableImpl<*>>
                by ReflectProperties.lazySoft { declaredNonStaticMembers + declaredStaticMembers }
        val allMembers: Collection<KCallableImpl<*>>
                by ReflectProperties.lazySoft { allNonStaticMembers + allStaticMembers }
    }

    val data = ReflectProperties.lazy { Data() }

    override val descriptor: ClassDescriptor get() = data().descriptor

    override val annotations: List<Annotation> get() = data().annotations

    private val classId: ClassId get() = RuntimeTypeMapper.mapJvmClassToKotlinClassId(jClass)

    // Note that we load members from the container's default type, which might be confusing. For example, a function declared in a
    // generic class "A<T>" would have "A<T>" as the receiver parameter even if a concrete type like "A<String>" was specified
    // in the function reference. Another, maybe slightly less confusing, approach would be to use the star-projected type ("A<*>").
    internal val memberScope: MemberScope get() = descriptor.memberScope

    internal val staticScope: MemberScope get() = descriptor.staticScope

    override val members: Collection<KCallable<*>> get() = data().allMembers

    override val constructorDescriptors: Collection<ConstructorDescriptor>
        get() {
            val descriptor = descriptor
            if (descriptor.isInterface || descriptor.isObject) {
                return emptyList()
            }
            return descriptor.constructors
        }

    override fun getProperties(name: Name): Collection<PropertyDescriptor> =
        memberScope.getProperties(name) + staticScope.getProperties(name)

    override fun getFunctions(name: Name): Collection<FunctionDescriptor> =
        memberScope.getFunctions(name) + staticScope.getFunctions(name)

    override fun getLocalProperty(index: Int): PropertyDescriptor? {
        // TODO: also check that this is a synthetic class (Metadata.k == 3)
        if (jClass.simpleName == JvmAbi.DEFAULT_IMPLS_CLASS_NAME) {
            jClass.declaringClass?.let { interfaceClass ->
                if (interfaceClass.isInterface) {
                    return (interfaceClass.kotlin as KClassImpl<*>).getLocalProperty(index)
                }
            }
        }

        return (descriptor as? ClassDescriptorImpl)?.getLocalProperty(index)
    }

    override val simpleName: String? get() = data().simpleName

    override val qualifiedName: String? get() = data().qualifiedName

    override val constructors: Collection<KFunction<T>> get() = data().constructors

    override val nestedClasses: Collection<KClass<*>> get() = data().nestedClasses

    override val objectInstance: T? get() = data().objectInstance

    override fun isInstance(value: Any?): Boolean {
        // TODO: use Kotlin semantics for mutable/read-only collections once KT-11754 is supported (see TypeIntrinsics)
        jClass.functionClassArity?.let { arity ->
            return TypeIntrinsics.isFunctionOfArity(value, arity)
        }
        return (jClass.wrapperByPrimitive ?: jClass).isInstance(value)
    }

    override val typeParameters: List<KTypeParameter> get() = data().typeParameters

    override val supertypes: List<KType> get() = data().supertypes

    /**
     * The list of the immediate subclasses if this class is a sealed class, or an empty list otherwise.
     */
    override val sealedSubclasses: List<KClass<out T>> get() = data().sealedSubclasses

    override val visibility: KVisibility?
        get() = descriptor.visibility?.toKVisibility()

    override val isFinal: Boolean
        get() = descriptor.isFinal

    override val isOpen: Boolean
        get() = descriptor.isOpen

    override val isAbstract: Boolean
        get() = descriptor.isAbstract

    override val isSealed: Boolean
        get() = descriptor.isSealed

    override val isData: Boolean
        get() = descriptor.isData

    override val isInner: Boolean
        get() = descriptor.isInner

    override val isCompanion: Boolean
        get() = descriptor.isCompanionObject

    override val isFun: Boolean
        get() = descriptor.isFun

    @Suppress("NOTHING_TO_OVERRIDE") // Temporary workaround for the JPS build until bootstrap
    override val isValue: Boolean
        get() = descriptor.isInline

    override fun equals(other: Any?): Boolean =
        other is KClassImpl<*> && javaObjectType == other.javaObjectType

    override fun hashCode(): Int =
        javaObjectType.hashCode()

    override fun toString(): String {
        return "class " + classId.let { classId ->
            val packageFqName = classId.packageFqName
            val packagePrefix = if (packageFqName.isRoot) "" else packageFqName.asString() + "."
            val classSuffix = classId.relativeClassName.asString().replace('.', '$')
            packagePrefix + classSuffix
        }
    }

    private fun reportUnresolvedClass(): Nothing {
/*
        val kind = ReflectKotlinClass.create(jClass)?.classHeader?.kind
        when (kind) {
            KotlinClassHeader.Kind.FILE_FACADE, KotlinClassHeader.Kind.MULTIFILE_CLASS, KotlinClassHeader.Kind.MULTIFILE_CLASS_PART -> {
                throw UnsupportedOperationException(
                    "Packages and file facades are not yet supported in Kotlin reflection. " +
                            "Meanwhile please use Java reflection to inspect this class: $jClass"
                )
            }
            KotlinClassHeader.Kind.SYNTHETIC_CLASS -> {
                throw UnsupportedOperationException(
                    "This class is an internal synthetic class generated by the Kotlin compiler, such as an anonymous class " +
                            "for a lambda, a SAM wrapper, a callable reference, etc. It's not a Kotlin class or interface, so the reflection " +
                            "library has no idea what declarations does it have. Please use Java reflection to inspect this class: $jClass"
                )
            }
            KotlinClassHeader.Kind.UNKNOWN -> {
                // Should not happen since ABI-related exception must have happened earlier
                throw KotlinReflectionInternalError("Unknown class: $jClass (kind = $kind)")
            }
            KotlinClassHeader.Kind.CLASS, null -> {
                // Should not happen since a proper Kotlin- or Java-class must have been resolved
                throw KotlinReflectionInternalError("Unresolved class: $jClass")
            }
        }
*/
        TODO()
    }
}
