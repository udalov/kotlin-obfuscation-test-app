package tests.lambdaClasses.reflectOnLambdaInSuspendLambda

// FILE: a.kt

import helpers.*
import kotlin.coroutines.*
import kotlin.reflect.jvm.reflect

fun box(): String {
    lateinit var x: (String) -> Unit
    suspend {
        x = { OK: String -> }
    }.startCoroutine(EmptyContinuation)
    return x.reflect()?.parameters?.singleOrNull()?.name ?: "null"
}