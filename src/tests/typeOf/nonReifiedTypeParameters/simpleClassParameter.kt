
package tests.typeOf.nonReifiedTypeParameters.simpleClassParameter

import kotlin.reflect.typeOf
import kotlin.test.assertEquals

class Container<T>

class C<X> {
    fun notNull() = typeOf<Container<X>>()
    fun nullable() = typeOf<Container<X?>>()
}

fun box(): String {
    val fqn = className("tests.typeOf.nonReifiedTypeParameters.simpleClassParameter.Container")
    assertEquals("$fqn<X>", C<Any>().notNull().toString())
    assertEquals("$fqn<X?>", C<Any>().nullable().toString())
    return "OK"
}

fun className(fqName: String): String {
    val isJS = 1 as Any is Double
    return if (isJS) fqName.substringAfterLast('.') else fqName
}