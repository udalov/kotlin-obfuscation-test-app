#!/usr/bin/env kotlin

// This script copies test cases on reflection from the main Kotlin project,
// and adapts them to be runnable together in one application.
//
// It takes one argument -- the path to the checked out Kotlin project. If not specified,
// "../kotlin" is used by default

import java.io.File

val kotlinRoot = File(args.getOrNull(0) ?: "../kotlin")
if (!kotlinRoot.exists()) error("$kotlinRoot does not exist! Should point to the Kotlin project root")

val reflect = File(kotlinRoot, "compiler/testData/codegen/box/reflection")
if (!reflect.exists()) error("$reflect does not exist!")

val destinationDirectory = File("src/tests")
if (destinationDirectory.exists()) destinationDirectory.deleteRecursively()

val ignored = listOf(
    "classLiterals/simpleClassLiteral.kt",
    "properties/getDelegate/nameClashExtensionProperties.kt",
    "enclosing/",
    "genericSignature/",
    "noReflectAtRuntime/",
    "typeOf/mutableCollections_before.kt",
    "typeOf/noReflect/",
)

val result = mutableListOf<String>()

fun isTestDirective(line: String): Boolean =
    listOf(
        "TODO: muted automatically",
        "TODO: investigate should",
        "IGNORE_BACKEND",
        "TARGET_BACKEND",
        "WASM_MUTE_REASON",
        "WITH_STDLIB",
        "WITH_REFLECT",
        "WITH_COROUTINES",
        "KJS_WITH_FULL_RUNTIME",
        "FULL_JDK",
        "SKIP_JDK6",
        "!LANGUAGE",
        "!USE_EXPERIMENTAL",
        "!OPT_IN",
    ).any(line::contains)

val PACKAGE_REPLACEMENT_REGEX = "([^A-Za-z0-9.:])test\\.".toRegex()

fun replacePackage(line: String, pkg: String): String =
    line.replace(PACKAGE_REPLACEMENT_REGEX, "$1$pkg.")

for (file in reflect.walk()) {
    if (!file.isFile || file.extension != "kt") continue

    val path = file.relativeTo(reflect).path
    if (ignored.any { path.startsWith(it) }) {
        println("Skipping ignored test $path")
        continue
    }

    val allContent = file.readLines()
    if (allContent.any { "TARGET_BACKEND: JS" in it }) {
        println("Skipping non-JVM-targeted test $path")
        continue
    }

    val content = allContent.filterNot(::isTestDirective).toMutableList()
    if (content.count { "// FILE" in it } > 1) {
        println("Skipping multi-file test $path")
        continue
    }

    if (content.any { it.startsWith("// FILE") && it.endsWith(".java") }) {
        println("Skipping test with Java $path")
        continue
    }

    val packageLine = content.indexOfFirst { it.startsWith("package ") }
    val destinationPackage = "tests." + path.substringBeforeLast(".kt").replace("/", ".")
    val packageDirective = "package $destinationPackage"
    if (packageLine != -1) {
        content[packageLine] = packageDirective
    } else {
        while (content.first().isEmpty()) content.removeAt(0)
        content.add(0, "")
        content.add(0, packageDirective)
    }

    for (i in content.indices) {
        content[i] = replacePackage(content[i], destinationPackage)
    }

    val destination = File(destinationDirectory, path)
    destination.parentFile.mkdirs()
    destination.writeText(content.joinToString("\n"))

    println("" + file + " -> " + destination)

    result.add(destinationPackage)
}

val mainKt = File("src/Main.kt")
enum class State { TAKE, SKIP }
val newLines = arrayListOf<String>().apply {
    var state = State.TAKE
    for (line in mainKt.readLines()) {
        if (state == State.TAKE) add(line)
        when (line.trim()) {
            "// ------------------------ >8 ------------------------" -> {
                state = State.SKIP
                for (testName in result.sorted()) {
                    add("        test(\"${testName.substringAfter("tests.")}\") { $testName.box() }")
                }
            }
            "// ----------------------------------------------------" -> {
                state = State.TAKE
                add(line)
            }
        }
    }
}

mainKt.bufferedWriter().use { writer ->
    for (line in newLines) {
        writer.appendLine(line)
    }
}
