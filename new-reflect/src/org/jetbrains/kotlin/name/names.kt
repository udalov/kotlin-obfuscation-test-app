package org.jetbrains.kotlin.name

typealias Name = String

data class FqName(val fqName: String) {
    val isRoot: Boolean get() = fqName.isEmpty()

    fun parent(): FqName = fqName.lastIndexOf('.').let { i ->
        check(i >= 0)
        FqName(fqName.substring(0, i))
    }

    fun child(name: Name): FqName = FqName("$fqName.$name")

    fun shortName(): Name = fqName.substring(fqName.lastIndexOf('.') + 1)

    fun asString(): String = fqName

    override fun toString(): String = fqName
}

data class ClassId(val packageFqName: FqName, val relativeClassName: FqName, val isLocal: Boolean = false) {
    constructor(packageFqName: FqName, relativeClassName: Name) : this(packageFqName, FqName(relativeClassName))

    val shortClassName: Name
        get() = relativeClassName.shortName()

    fun asSingleFqName(): FqName =
        if (packageFqName.isRoot) relativeClassName else FqName(packageFqName.asString() + "." + relativeClassName.asString())

    fun createNestedClassId(name: Name): ClassId =
        ClassId(packageFqName, relativeClassName.child(name), isLocal)

    private fun asString(): String {
        return if (packageFqName.isRoot) relativeClassName.asString()
        else packageFqName.asString().replace('.', '/') + "/" + relativeClassName.asString()
    }

    override fun toString(): String = if (packageFqName.isRoot) "/" + asString() else asString()

    companion object {
        fun topLevel(topLevelFqName: FqName): ClassId =
            ClassId(topLevelFqName.parent(), FqName(topLevelFqName.shortName()))
    }
}
