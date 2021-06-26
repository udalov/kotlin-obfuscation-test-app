package org.jetbrains.kotlin.descriptors

import kotlinx.metadata.ClassName
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.descriptors.annotations.JvmAnnotations
import org.jetbrains.kotlin.name.ClassId
import org.jetbrains.kotlin.name.Name
import org.jetbrains.kotlin.types.KotlinType
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
import kotlin.reflect.KVariance
import kotlin.reflect.jvm.internal.KClassImpl
import kotlin.reflect.jvm.internal.KDeclarationContainerImpl
import kotlin.reflect.jvm.internal.ReflectionObjectRenderer

internal class JavaClassDescriptor(
    override val module: ModuleDescriptor,
    override val classId: ClassId,
    override val kClass: KClassImpl<*>
) : ClassDescriptor {
    private val jClass = kClass.jClass

    override val name: Name
        get() = jClass.simpleName
    override val visibility: DescriptorVisibility?
        get() = jClass.modifiers.toVisibility()

    override val annotations: Annotations
        get() = JvmAnnotations(jClass)

    override val isInterface: Boolean
        get() = jClass.isInterface && !jClass.isAnnotation
    override val isAnnotationClass: Boolean
        get() = jClass.isAnnotation
    override val isNonCompanionObject: Boolean
        get() = false
    override val isFinal: Boolean
        get() = Modifier.isFinal(jClass.modifiers)
    override val isOpen: Boolean
        get() = !isFinal && !isAbstract
    override val isAbstract: Boolean
        get() = Modifier.isAbstract(jClass.modifiers)
    override val isSealed: Boolean
        get() = false
    override val isData: Boolean
        get() = false
    override val isInner: Boolean
        get() = jClass.declaringClass != null && !Modifier.isStatic(jClass.modifiers)
    override val isInline: Boolean
        get() = false
    override val isCompanionObject: Boolean
        get() = false
    override val isFun: Boolean
        get() = false

    override val declaredTypeParameters: List<TypeParameterDescriptor>
        get() = jClass.typeParameters.map { JavaTypeParameterDescriptor(it, module, this) }
    override val supertypes: List<KotlinType>
        get() = (listOfNotNull(jClass.genericSuperclass) + jClass.genericInterfaces).map { it.toKotlinType(module, this) }
    override val containingClass: JavaClassDescriptor?
        get() = classId.getOuterClassId()?.let { module.findClass(it.asClassName()) as JavaClassDescriptor? }
    override val thisAsReceiverParameter: ReceiverParameterDescriptor =
        ReceiverParameterDescriptorImpl(defaultType)
    override val constructors: List<ConstructorDescriptor>
        get() = TODO("Java class constructors")
    override val nestedClasses: List<ClassDescriptor>
        get() = jClass.declaredClasses.mapNotNull { module.findClass(classId.createNestedClassId(it.simpleName).asClassName()) }
    override val sealedSubclasses: List<ClassDescriptor>
        get() = emptyList()
    override val memberScope: MemberScope
        get() = MemberScope(
            emptyList(), // TODO: Java fields
            jClass.declaredMethods.map { JavaFunctionDescriptor(it, module, this) }.let { realFunctions ->
                realFunctions + addFunctionFakeOverrides(this, realFunctions)
            }
        )
    override val staticScope: MemberScope
        get() = TODO("Java class static scope")

    override fun equals(other: Any?): Boolean =
        other is ClassDescriptor && classId == other.classId

    override fun hashCode(): Int =
        classId.hashCode()
}

internal class JavaFunctionDescriptor(
    val method: Method,
    override val module: ModuleDescriptor,
    override val containingClass: JavaClassDescriptor,
) : FunctionDescriptor {
    override val container: KDeclarationContainerImpl
        get() = containingClass.kClass

    override val name: Name
        get() = method.name
    override val visibility: DescriptorVisibility?
        get() = method.modifiers.toVisibility()

    override val annotations: Annotations
        get() = JvmAnnotations(method)

    override val isFinal: Boolean
        get() = Modifier.isFinal(method.modifiers)
    override val isOpen: Boolean
        get() = !isFinal && !isAbstract
    override val isAbstract: Boolean
        get() = Modifier.isAbstract(method.modifiers)
    override val isExternal: Boolean
        get() = Modifier.isNative(method.modifiers)

    override val dispatchReceiverParameter: ReceiverParameterDescriptor
        get() = containingClass.thisAsReceiverParameter
    override val extensionReceiverParameter: ReceiverParameterDescriptor?
        get() = null
    override val valueParameters: List<ValueParameterDescriptor>
        // TODO: parameter names if `-parameters` was used
        get() = method.genericParameterTypes.withIndex().map { (index, type) ->
            JavaValueParameterDescriptor(this, index, type.toKotlinType(module, this))
        }
    override val typeParameters: List<TypeParameterDescriptor>
        get() = method.typeParameters.map {
            JavaTypeParameterDescriptor(it, module, this)
        }

    override val returnType: KotlinType
        get() = method.genericReturnType.toKotlinType(module, this)

    override val isInfix: Boolean
        get() = false
    override val isInline: Boolean
        get() = false
    override val isOperator: Boolean
        get() = false
    override val isSuspend: Boolean
        get() = false
    override val isReal: Boolean
        get() = true

    override fun hasSynthesizedParameterNames(): Boolean = true

    override fun render(): String = ReflectionObjectRenderer.renderFunction(this)
}

internal class JavaTypeParameterDescriptor(
    private val typeVariable: TypeVariable<*>,
    private val module: ModuleDescriptor,
    override val containingDeclaration: DeclarationDescriptor,
) : TypeParameterDescriptor {
    override val name: Name
        get() = typeVariable.name
    override val annotations: Annotations
        get() = JvmAnnotations(typeVariable)

    override val upperBounds: List<KotlinType>
        get() = typeVariable.bounds.map { it.toKotlinType(module, containingDeclaration) }

    override val variance: KVariance
        get() = KVariance.INVARIANT
    override val isReified: Boolean
        get() = false

    override fun equals(other: Any?): Boolean =
        other is TypeParameterDescriptor && name == other.name && containingDeclaration == other.containingDeclaration

    override fun hashCode(): Int =
        name.hashCode() * 31 + containingDeclaration.hashCode()
}

internal class JavaValueParameterDescriptor(
    override val containingDeclaration: JavaFunctionDescriptor,
    private val index: Int,
    override val type: KotlinType,
) : ValueParameterDescriptor {
    override val name: Name
        get() = "p$index"
    override val annotations: Annotations
        get() = JvmAnnotations(containingDeclaration.method.parameterAnnotations[index].toList())

    override val declaresDefaultValue: Boolean
        get() = false
    override val varargElementType: KotlinType?
        get() = null // TODO: if (containingDeclaration.method.isVarArgs) ...
}

private fun Type.toKotlinType(module: ModuleDescriptor, context: DeclarationDescriptor): KotlinType {
    return when (this) {
        is Class<*> -> KotlinType(
            module.findClass(className) ?: TODO(className),
            emptyList(),
            false,
            Annotations.EMPTY // TODO
        )
        else -> TODO("Unsupported Java type: $this (${this::class.java})")
    }
}

private val Class<*>.className: ClassName
    // TODO: wouldn't work for classes with '$' in the name.
    get() = name.replace('.', '/').replace('$', '.')

private fun Int.toVisibility(): DescriptorVisibility? =
    when {
        Modifier.isPublic(this) -> DescriptorVisibility.PUBLIC
        Modifier.isProtected(this) -> DescriptorVisibility.PROTECTED
        Modifier.isPrivate(this) -> DescriptorVisibility.PRIVATE
        else -> null
    }
