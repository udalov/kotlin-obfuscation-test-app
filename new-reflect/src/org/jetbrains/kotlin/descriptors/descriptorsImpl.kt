package org.jetbrains.kotlin.descriptors

import kotlinx.metadata.ClassName
import kotlinx.metadata.common.KotlinCommonMetadata
import kotlinx.metadata.jvm.KotlinClassHeader
import kotlinx.metadata.jvm.KotlinClassMetadata
import org.jetbrains.kotlin.builtins.JavaToKotlinClassMap
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.misc.classId
import org.jetbrains.kotlin.misc.tryLoadClass
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.KotlinType

class ModuleDescriptorImpl(val classLoader: ClassLoader) : ModuleDescriptor {
    override fun findClass(name: ClassName): ClassDescriptor? {
        // TODO: verify
        return findClass(FqName(name.replace('/', '.')))
    }

    fun findClass(kotlinFqName: FqName): ClassDescriptor? {
        val fqName = JavaToKotlinClassMap.mapKotlinToJava(kotlinFqName)?.asSingleFqName() ?: kotlinFqName
        val jClass = classLoader.tryLoadClass(fqName.asString())
        // TODO: cache
        if (jClass != null) return createClassDescriptor(jClass)
        return null
    }

    fun createClassDescriptor(jClass: Class<*>): ClassDescriptor? {
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
            return ClassDescriptorImpl(kmClass, this, builtinClassId)
        }

        jClass.readHeader()?.let { header ->
            val kmClass = (KotlinClassMetadata.read(header) as/*TODO*/ KotlinClassMetadata.Class).toKmClass()
            return ClassDescriptorImpl(kmClass, this, jClass.classId)
        }

        TODO(jClass.name)
    }

    fun createFileDescriptor(jClass: Class<*>): FileDescriptorImpl {
        jClass.readHeader()?.let { header ->
            val metadata = KotlinClassMetadata.read(header)
            val kmPackage = when (metadata) {
                is KotlinClassMetadata.FileFacade -> metadata.toKmPackage()
                is KotlinClassMetadata.MultiFileClassPart -> metadata.toKmPackage()
                else -> TODO(metadata.toString())
            }
            return FileDescriptorImpl(kmPackage, this)
        }

        TODO(jClass.name)
    }

    private fun Class<*>.readHeader(): KotlinClassHeader? {
        val metadata = getDeclaredAnnotation(Metadata::class.java) ?: return null
        with(metadata) {
            return KotlinClassHeader(kind, metadataVersion, data1, data2, extraString, packageName, extraInt)
        }
    }
}

class ReceiverParameterDescriptorImpl(
    override val type: KotlinType
) : ReceiverParameterDescriptor {
    override val containingDeclaration: CallableMemberDescriptor
        get() = error("Shouldn't be called")

    override val annotations: Annotations
        get() = Annotations.EMPTY

    override val name: Name
        get() = "<this>"
}
