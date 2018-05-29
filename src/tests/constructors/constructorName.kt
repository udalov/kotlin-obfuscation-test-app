package tests.constructors.constructorName

import kotlin.test.assertEquals

class A

fun box(): String {
    assertEquals("<init>", ::A.name)
    return "OK"
}