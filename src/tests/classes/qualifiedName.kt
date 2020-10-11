
package tests.classes.qualifiedName

import kotlin.test.assertEquals

class Klass {
    class Nested
    class `Nested$With$Dollars`
    companion object
}

fun box(): String {
    assertEquals("tests.classes.qualifiedName.Klass", Klass::class.qualifiedName)
    assertEquals("tests.classes.qualifiedName.Klass.Nested", Klass.Nested::class.qualifiedName)
    assertEquals("tests.classes.qualifiedName.Klass.Nested\$With\$Dollars", Klass.`Nested$With$Dollars`::class.qualifiedName)
    assertEquals("tests.classes.qualifiedName.Klass.Companion", Klass.Companion::class.qualifiedName)

    assertEquals("java.util.Date", java.util.Date::class.qualifiedName)
    assertEquals("kotlin.jvm.internal.Ref.ObjectRef", kotlin.jvm.internal.Ref.ObjectRef::class.qualifiedName)

    class Local
    assertEquals(null, Local::class.qualifiedName)

    val o = object {}
    assertEquals(null, o.javaClass.kotlin.qualifiedName)

    return "OK"
}