package tests.mapping.interfaceCompanionPropertyWithJvmField

import kotlin.reflect.jvm.*
import kotlin.test.assertEquals

interface Foo {
    companion object {
        @JvmField
        val value = "OK"
    }
}

fun box(): String {
    val field = Foo.Companion::value.javaField!!
    return field.get(null) as String
}