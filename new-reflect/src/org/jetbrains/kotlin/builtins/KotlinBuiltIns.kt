package org.jetbrains.kotlin.builtins

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.types.KotlinType

object StandardNames {
    val BUILT_INS_PACKAGE_FQ_NAME = FqName("kotlin")

    val FQ_NAMES = FqNames()
    class FqNames {
        val array = FqName("kotlin.Array")

        val any = ClassId(BUILT_INS_PACKAGE_FQ_NAME, "Any")
    }
}

object KotlinBuiltIns {
    fun isSpecialClassWithNoSupertypes(descriptor: ClassDescriptor): Boolean =
        descriptor.classId == StandardNames.FQ_NAMES.any
}

object KotlinBuiltInsImpl {
    val anyType: KotlinType = TODO()
}
