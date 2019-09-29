#!/usr/bin/env kotlin

import java.io.File
import java.io.PrintWriter

val INDENT = "    "
val ROOT = File("src")

var indent = ""
val out = PrintWriter("test/GeneratedTests.kt")

fun visit(dir: File) {
    out.println(indent + "class ${dir.name.capitalize()} {")
    val allFiles = dir.listFiles() ?: error("Not a directory: $dir")
    val (files, dirs) = allFiles.sortedBy(File::getName).partition(File::isFile)

    indent += INDENT

    for (file in files) {
        out.println(indent + "@Test")
        out.println(indent + "fun ${file.nameWithoutExtension}() {")
        val pkg = file.relativeTo(ROOT).path.substringBeforeLast(".kt").replace("/", ".")
        out.println(indent + INDENT + "Assert.assertEquals(\"OK\", " + pkg + ".box())")
        out.println(indent + "}")
        if (file != files.last() || dirs.isNotEmpty()) out.println()
    }

    for (subdir in dirs) {
        visit(subdir)
        if (subdir != dirs.last()) out.println()
    }

    indent = indent.substring(0, indent.length - INDENT.length)
    out.println(indent + "}")
}

out.println("import org.junit.Assert")
out.println("import org.junit.Test")
out.println()
for (dir in File(ROOT, "tests").listFiles()!!.sortedBy(File::getName)) {
    if (!dir.isDirectory) continue
    visit(dir)
    out.println()
}
out.close()
