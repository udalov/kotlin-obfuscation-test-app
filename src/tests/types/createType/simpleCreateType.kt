package tests.types.createType.simpleCreateType

import kotlin.reflect.full.createType
import kotlin.reflect.KTypeProjection
import kotlin.test.assertEquals

class Foo
class Bar<T>

fun box(): String {
    assertEquals("tests.types.createType.simpleCreateType.Foo", Foo::class.createType().toString())
    assertEquals("tests.types.createType.simpleCreateType.Foo?", Foo::class.createType(nullable = true).toString())

    assertEquals("tests.types.createType.simpleCreateType.Bar<kotlin.String>", Bar::class.createType(listOf(KTypeProjection.invariant(String::class.createType()))).toString())
    assertEquals("tests.types.createType.simpleCreateType.Bar<kotlin.Int>?", Bar::class.createType(listOf(KTypeProjection.invariant(Int::class.createType())), nullable = true).toString())

    return "OK"
}