package tests.annotations.repeatable.jvmRepeatableKotlinAnnotation

// JVM_TARGET: 1.8

// Android doesn't have @Repeatable, so findAnnotations can't unpack repeatable annotations.

import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.hasAnnotation

@java.lang.annotation.Repeatable(A.Container::class)
annotation class A(val value: String) {
    annotation class Container(val value: Array<A>)
}

@A("O")
@A("")
@A("K")
fun f() {}

fun box(): String {
    val element = ::f
    if (element.hasAnnotation<A>()) return "Fail hasAnnotation $element"
    val find = element.findAnnotation<A>()
    if (find != null) return "Fail findAnnotation $element: $find"

    val all = (element.annotations.single() as A.Container).value.asList()
    val findAll = element.findAnnotations<A>()
    if (all != findAll) throw AssertionError("Fail findAnnotations $element: $all != $findAll")

    return all.fold("") { acc, it -> acc + it.value }
}