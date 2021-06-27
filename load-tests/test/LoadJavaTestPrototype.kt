@file:Suppress("NO_REFLECTION_IN_CLASS_PATH")

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import org.junit.Test
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty
import kotlin.reflect.KVisibility
import kotlin.test.assertEquals

class LoadJavaTestPrototype {
    @Test
    fun testSimple() {
        val testData = File("load-tests/testData/compiledKotlin/dataClass/twoVars.kt")
        val source = SourceFile.kotlin(testData.name, testData.readText())
        val result = KotlinCompilation().apply {
            sources = listOf(source)
            // inheritClassPath = true
            messageOutputStream = System.err
            verbose = false
            // TODO: add compiler plugin to render descriptors and use result for comparison
        }.compile()
        assertEquals(KotlinCompilation.ExitCode.OK, result.exitCode)

        val actual = buildActual(result)
        val expected = File(testData.path.substringBeforeLast(".kt") + ".txt").readText()
        assertEquals(expected.trim(), actual.trim())
    }

    private fun buildActual(result: KotlinCompilation.Result): String = Printer(StringBuilder()).apply {
        appendLine("package test")
        appendLine()
        for (file in result.compiledClassAndResourceFiles) {
            if (file.extension != "class") continue
            val className = "test.${file.nameWithoutExtension}"
            appendClass(result.classLoader.loadClass(className).kotlin)
        }
    }.build()
}

private class Printer(val sb: StringBuilder) {
    private var indent = ""

    fun pushIndent() {
        indent += INDENT_UNIT
    }

    fun popIndent() {
        indent = indent.substringBeforeLast(INDENT_UNIT)
    }

    fun append(text: String) {
        appendIndent()
        sb.append(text)
    }

    fun appendLine() {
        sb.appendLine()
    }

    fun appendLine(line: String) {
        appendIndent()
        sb.appendLine(line)
    }

    fun build(): String = sb.toString()

    private fun appendIndent() {
        if (sb.lastOrNull() == '\n') sb.append(indent)
    }

    companion object {
        const val INDENT_UNIT = "    "
    }
}

private fun Printer.appendClass(klass: KClass<*>) {
    appendVisibility(klass.visibility)
    if (klass.isFinal) append("final ")
    if (klass.isOpen) append("open ")
    if (klass.isAbstract) append("abstract ")
    if (klass.isSealed) append("sealed ")
    if (klass.isData) append("data ")
    if (klass.isInner) append("inner ")
    if (klass.isCompanion) append("companion ")
    if (klass.isFun) append("fun ")
    if (klass.isValue) append("value ")
    appendLine("class ${klass.simpleName} {")
    pushIndent()
    for (constructor in klass.constructors) {
        appendConstructor(constructor)
    }
    for (property in klass.members.filterIsInstance<KProperty<*>>()) {
        appendProperty(property)
    }
    for (function in klass.members.filterIsInstance<KFunction<*>>()) {
        appendFunction(function)
    }
    popIndent()
    appendLine("}")
}

private fun Printer.appendVisibility(visibility: KVisibility?) {
    when (visibility) {
        KVisibility.PUBLIC -> append("public ")
        KVisibility.PROTECTED -> append("protected ")
        KVisibility.INTERNAL -> append("internal ")
        KVisibility.PRIVATE -> append("private ")
        null -> {
        }
    }
}

private fun Printer.appendConstructor(constructor: KFunction<*>) {
    appendVisibility(constructor.visibility)
    appendLine("constructor")
}

private fun Printer.appendProperty(property: KProperty<*>) {
    appendVisibility(property.visibility)
    appendLine("property ${property.name}")
}

private fun Printer.appendFunction(function: KFunction<*>) {
    appendVisibility(function.visibility)
    appendLine("function ${function.name}")
}
