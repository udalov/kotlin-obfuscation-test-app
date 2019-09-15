package org.jetbrains.kotlin.builtins

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.types.KotlinType

object StandardNames {
    val BUILT_INS_PACKAGE_FQ_NAME = FqName("kotlin")

    val FQ_NAMES = FqNames()
    class FqNames {
        val array = FqName("kotlin.Array")
    }
}

object KotlinBuiltIns {
    fun isSpecialClassWithNoSupertypes(descriptor: ClassDescriptor): Boolean =
        TODO()
}

object KotlinBuiltInsImpl {
    val anyType: KotlinType = TODO()
}
