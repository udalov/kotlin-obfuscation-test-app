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
    "classes/jvmName.kt",
    "classes/qualifiedName.kt",
    "classLiterals/simpleClassLiteral.kt",
    "createAnnotation/equalsHashCodeToString.kt",
    "functions/genericOverriddenFunction.kt",
    "methodsFromAny/classToString.kt",
    "methodsFromAny/functionToString.kt",
    "methodsFromAny/functionFromStdlib",
    "methodsFromAny/memberExtensionToString.kt",
    "methodsFromAny/propertyToString.kt",
    "methodsFromAny/typeToString.kt",
    "methodsFromAny/typeToStringInnerGeneric.kt",
    "properties/getDelegate/nameClashExtensionProperties.kt",
    "properties/genericProperty.kt",
    "properties/genericOverriddenProperty.kt",
    "properties/localDelegated/localAndNonLocal.kt", // Enable after migration to 1.2.60+
    "specialBuiltIns/getMembersOfStandardJavaClasses.kt",
    "enclosing/",
    "genericSignature/",
    "noReflectAtRuntime/"
)

val needPackageReplacement = listOf(
    "createAnnotation/primitivesAndArrays.kt",
    "createAnnotation/enumKClassAnnotation.kt",
    "createAnnotation/arrayOfKClasses.kt",
    "types/createType/innerGeneric.kt",
    "types/createType/simpleCreateType.kt"
)

val result = mutableListOf<String>()

fun isTestDirective(line: String): Boolean =
        "TODO: muted automatically" in line ||
                "TODO: investigate should" in line ||
                "IGNORE_BACKEND" in line ||
                "TARGET_BACKEND" in line ||
                "WITH_REFLECT" in line ||
                "FULL_JDK" in line

fun replacePackage(line: String, pkg: String): String {
    val inAnnotations = line.replace("@", "@$pkg.")
    if (inAnnotations != line) return inAnnotations

    return line.replace("assertEquals(\"", "assertEquals(\"$pkg.")
}

for (file in reflect.walk()) {
    if (!file.isFile) continue

    val path = file.relativeTo(reflect).path
    if (ignored.any { path.startsWith(it) }) {
        println("Skipping ignored test $path")
        continue
    }

    val content = file.readLines().filterNot(::isTestDirective).toMutableList()

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

    if (needPackageReplacement.any { path.startsWith(it) }) {
        for (i in content.indices) {
            content.set(i, replacePackage(content[i], destinationPackage))
        }
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
                for (testName in result) {
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
