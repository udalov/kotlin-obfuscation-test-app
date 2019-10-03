package org.jetbrains.kotlin.builtins

object JvmAbi {
    // TODO
    fun getterName(name: String): String = "get" + name.capitalize()

    const val INSTANCE_FIELD = "INSTANCE"
    const val DEFAULT_PARAMS_IMPL_SUFFIX = "\$default"
    const val DEFAULT_IMPLS_CLASS_NAME = "DefaultImpls"
    const val DEFAULT_IMPLS_SUFFIX = "$$DEFAULT_IMPLS_CLASS_NAME"
    const val IMPL_SUFFIX_FOR_INLINE_CLASS_MEMBERS = "-impl"
}
