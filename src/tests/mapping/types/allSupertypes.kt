
package tests.mapping.types.allSupertypes

import kotlin.reflect.full.allSupertypes
import kotlin.reflect.jvm.javaType
import kotlin.test.assertEquals

interface I<T>

abstract class A<X> : Map<I<out CharSequence>, Array<List<Int>>>

fun box(): String {
    assertEquals(
        "java.util.Map<tests.mapping.types.allSupertypes.I<? extends java.lang.CharSequence>, java.util.List<? extends java.lang.Integer>[]>",
        A::class.allSupertypes.single { it.classifier == Map::class }.javaType.toString()
    )

    return "OK"
}