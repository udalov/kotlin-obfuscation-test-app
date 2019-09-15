package org.jetbrains.kotlin.builtins

enum class JvmPrimitiveType(val primitiveType: PrimitiveType, val javaKeywordName: String) {
    BOOLEAN(PrimitiveType.BOOLEAN, "boolean"), CHAR(PrimitiveType.CHAR, "char"),
    BYTE(PrimitiveType.BYTE, "byte"), SHORT(PrimitiveType.SHORT, "short"), INT(PrimitiveType.INT, "int"),
    FLOAT(PrimitiveType.FLOAT, "float"), LONG(PrimitiveType.LONG, "long"),
    DOUBLE(PrimitiveType.DOUBLE, "double");

    companion object {
        private val TYPE_BY_NAME: MutableMap<String, JvmPrimitiveType> =
            HashMap()

        operator fun get(name: String): JvmPrimitiveType =
            TYPE_BY_NAME[name] ?: throw AssertionError("Non-primitive type name passed: $name")

        init {
            for (type in values()) {
                TYPE_BY_NAME[type.javaKeywordName] = type
            }
        }
    }
}
