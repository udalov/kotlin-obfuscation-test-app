package tests.lambdaClasses.reflectOnLambdaInStaticField

import kotlin.reflect.jvm.reflect

val x = { OK: String -> }

fun box(): String {
    return x.reflect()?.parameters?.singleOrNull()?.name ?: "null"
}