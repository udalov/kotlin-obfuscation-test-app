package org.jetbrains.kotlin.builtins

import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

enum class PrimitiveType(val typeName: Name) {
    BOOLEAN("Boolean"), CHAR("Char"), BYTE("Byte"), SHORT("Short"), INT("Int"), FLOAT("Float"), LONG("Long"), DOUBLE("Double");

    val arrayTypeName: Name = typeName + "Array"

    val typeFqName: FqName = StandardNames.BUILT_INS_PACKAGE_FQ_NAME.child(typeName)
    val arrayTypeFqName: FqName = StandardNames.BUILT_INS_PACKAGE_FQ_NAME.child(arrayTypeName)
}
