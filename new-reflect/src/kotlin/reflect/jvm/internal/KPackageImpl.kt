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

import kotlinx.metadata.jvm.KotlinClassMetadata
import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.misc.classId
import org.jetbrains.kotlin.misc.safeClassLoader
import org.jetbrains.kotlin.name.Name
import kotlin.reflect.KCallable
import kotlin.reflect.jvm.internal.KDeclarationContainerImpl.MemberBelonginess.DECLARED

internal class KPackageImpl(
    override val jClass: Class<*>,
    @Suppress("unused") val usageModuleName: String? = null // may be useful for debug
) : KDeclarationContainerImpl() {
    private inner class Data {
        val scope: MemberScope by ReflectProperties.lazySoft {
            createFileDescriptor().scope
        }

        private fun createFileDescriptor(): FileDescriptorImpl {
            val module = ModuleDescriptorImpl(jClass.safeClassLoader)
            jClass.readHeader()?.let { header ->
                val metadata = KotlinClassMetadata.read(header)
                val kmPackage = when (metadata) {
                    is KotlinClassMetadata.FileFacade -> metadata.toKmPackage()
                    is KotlinClassMetadata.MultiFileClassPart -> metadata.toKmPackage()
                    else -> TODO(metadata.toString())
                }
                return FileDescriptorImpl(kmPackage, module, this@KPackageImpl)
            }

            TODO(jClass.name)
        }

        val multifileFacade: Class<*>? by ReflectProperties.lazy {
            val facadeName: String? = null // TODO
            // We need to check isNotEmpty because this is the value read from the annotation which cannot be null.
            // The default value for 'xs' is empty string, as declared in kotlin.Metadata
            if (facadeName != null && facadeName.isNotEmpty())
                jClass.safeClassLoader.loadClass(facadeName.replace('/', '.'))
            else null
        }

        val members: Collection<KCallableImpl<*>> by ReflectProperties.lazySoft {
            getMembers(scope, DECLARED)
        }
    }

    private val data = ReflectProperties.lazy { Data() }

    override val methodOwner: Class<*> get() = data().multifileFacade ?: jClass

    private val scope: MemberScope get() = data().scope

    override val members: Collection<KCallable<*>> get() = data().members

    override val constructorDescriptors: Collection<ConstructorDescriptor>
        get() = emptyList()

    override fun getProperties(name: Name): Collection<PropertyDescriptor> =
        scope.getProperties(name)

    override fun getFunctions(name: Name): Collection<FunctionDescriptor> =
        scope.getFunctions(name)

    override fun getLocalProperty(index: Int): PropertyDescriptor? {
/*
        return data().metadata?.let { (nameResolver, packageProto, metadataVersion) ->
            packageProto.getExtensionOrNull(JvmProtoBuf.packageLocalVariable, index)?.let { proto ->
                deserializeToDescriptor(
                    jClass, proto, nameResolver, TypeTable(packageProto.typeTable), metadataVersion,
                    MemberDeserializer::loadProperty
                )
            }
        }
*/
        return null
    }

    override fun equals(other: Any?): Boolean =
        other is KPackageImpl && jClass == other.jClass

    override fun hashCode(): Int =
        jClass.hashCode()

    override fun toString(): String =
        "file class ${jClass.classId.asSingleFqName()}"
}
