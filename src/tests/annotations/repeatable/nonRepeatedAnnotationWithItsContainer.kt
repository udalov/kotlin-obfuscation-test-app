// JVM_TARGET: 1.8

// Android doesn't have @Repeatable.

package tests.annotations.repeatable.nonRepeatedAnnotationWithItsContainer

import kotlin.test.assertEquals
import kotlin.reflect.full.*

@java.lang.annotation.Repeatable(As::class)
annotation class A(val value: Int)

annotation class As(val value: Array<A>)

@A(1)
@As([A(2), A(3)])
class Z

@As([A(1), A(2)])
@A(3)
class ZZ

// JDK 9+ uses {} for array arguments instead of [], JDK 15+ doesn't render "value="
fun Any?.render(): String =
    toString().replace("value=", "").replace("{", "[").replace("}", "]")

// Explicit container is not unwrapped.
fun box(): String {
    assertEquals("[@tests.annotations.repeatable.nonRepeatedAnnotationWithItsContainer.A(1), @tests.annotations.repeatable.nonRepeatedAnnotationWithItsContainer.As([@tests.annotations.repeatable.nonRepeatedAnnotationWithItsContainer.A(2), @tests.annotations.repeatable.nonRepeatedAnnotationWithItsContainer.A(3)])]", Z::class.annotations.render())
    assertEquals("[@tests.annotations.repeatable.nonRepeatedAnnotationWithItsContainer.A(1)]", Z::class.findAnnotations<A>().render())
    assertEquals("@tests.annotations.repeatable.nonRepeatedAnnotationWithItsContainer.A(1)", Z::class.findAnnotation<A>().render())

    assertEquals("[@tests.annotations.repeatable.nonRepeatedAnnotationWithItsContainer.As([@tests.annotations.repeatable.nonRepeatedAnnotationWithItsContainer.A(1), @tests.annotations.repeatable.nonRepeatedAnnotationWithItsContainer.A(2)]), @tests.annotations.repeatable.nonRepeatedAnnotationWithItsContainer.A(3)]", ZZ::class.annotations.render())
    assertEquals("[@tests.annotations.repeatable.nonRepeatedAnnotationWithItsContainer.A(3)]", ZZ::class.findAnnotations<A>().render())
    assertEquals("@tests.annotations.repeatable.nonRepeatedAnnotationWithItsContainer.A(3)", ZZ::class.findAnnotation<A>().render())

    return "OK"
}