
package tests.typeOf.multipleLayers

import kotlin.reflect.typeOf
import kotlin.test.assertEquals

interface C

inline fun <reified T> get() = typeOf<T>()

inline fun <reified U> get1() = get<U?>()

inline fun <reified V> get2() = get1<Map<in V?, Array<V>>>()

fun box(): String {
    assertEquals("kotlin.collections.Map<in tests.typeOf.multipleLayers.C?, kotlin.Array<tests.typeOf.multipleLayers.C>>?", get2<C>().toString())
    assertEquals("kotlin.collections.Map<in kotlin.collections.List<tests.typeOf.multipleLayers.C>?, kotlin.Array<kotlin.collections.List<tests.typeOf.multipleLayers.C>>>?", get2<List<C>>().toString())
    return "OK"
}