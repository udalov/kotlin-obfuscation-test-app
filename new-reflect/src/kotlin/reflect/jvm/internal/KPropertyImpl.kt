/*
 * Copyright 2010-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package kotlin.reflect.jvm.internal

import org.jetbrains.kotlin.descriptors.*
import org.jetbrains.kotlin.descriptors.annotations.Annotations
import org.jetbrains.kotlin.descriptors.annotations.hasAnnotation
import org.jetbrains.kotlin.types.isNullableType
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import kotlin.jvm.internal.CallableReference
import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.IllegalPropertyDelegateAccessException
import kotlin.reflect.jvm.internal.JvmPropertySignature.*
import kotlin.reflect.jvm.internal.calls.*
import kotlin.reflect.jvm.internal.calls.CallerImpl

internal abstract class KPropertyImpl<out V> private constructor(
    override val container: KDeclarationContainerImpl,
    override val name: String,
    val signature: String,
    descriptorInitialValue: PropertyDescriptor?,
    private val rawBoundReceiver: Any?
) : KCallableImpl<V>(), KProperty<V> {
    constructor(container: KDeclarationContainerImpl, name: String, signature: String, boundReceiver: Any?) : this(
        container, name, signature, null, boundReceiver
    )

    constructor(container: KDeclarationContainerImpl, descriptor: PropertyDescriptor) : this(
        container,
        descriptor.name,
        RuntimeTypeMapper.mapPropertySignature(descriptor).asString(),
        descriptor,
        CallableReference.NO_RECEIVER
    )

    val boundReceiver
        get() = rawBoundReceiver.coerceToExpectedReceiverType(descriptor)

    override val isBound: Boolean get() = rawBoundReceiver != CallableReference.NO_RECEIVER

    private val _javaField: ReflectProperties.LazyVal<Field?> = ReflectProperties.lazy {
        val jvmSignature = RuntimeTypeMapper.mapPropertySignature(descriptor)
        when (jvmSignature) {
            is KotlinProperty -> {
                jvmSignature.fieldSignature?.let {
                    val owner = if (false /* TODO: DescriptorsJvmAbiUtil.isPropertyWithBackingFieldInOuterClass(descriptor) ||
                        JvmProtoBufUtil.isMovedFromInterfaceCompanion(jvmSignature.proto) */
                    ) {
                        container.jClass.enclosingClass
                    } else descriptor.containingClass?.jClass ?: container.jClass

                    try {
                        owner.getDeclaredField(it.name)
                    } catch (e: NoSuchFieldException) {
                        null
                    }
                }
            }
            is JavaField -> jvmSignature.field
            is JavaMethodProperty -> null
            is MappedKotlinProperty -> null
        }
    }

    val javaField: Field? get() = _javaField()

    protected fun computeDelegateField(): Field? =
        if (descriptor.isDelegated) javaField else null

    protected fun getDelegate(field: Field?, receiver: Any?): Any? =
        try {
            if (receiver === EXTENSION_PROPERTY_DELEGATE) {
                if (descriptor.extensionReceiverParameter == null) {
                    throw RuntimeException(
                        "'$this' is not an extension property and thus getExtensionDelegate() " +
                                "is not going to work, use getDelegate() instead"
                    )
                }
            }
            field?.get(receiver)
        } catch (e: IllegalAccessException) {
            throw IllegalPropertyDelegateAccessException(e)
        }

    abstract override val getter: Getter<V>

    private val _descriptor = ReflectProperties.lazySoft(descriptorInitialValue) {
        container.findPropertyDescriptor(name, signature)
    }

    override val descriptor: PropertyDescriptor get() = _descriptor()

    override val caller: Caller<*> get() = getter.caller

    override val defaultCaller: Caller<*>? get() = getter.defaultCaller

    override val isLateinit: Boolean get() = descriptor.isLateInit

    override val isConst: Boolean get() = descriptor.isConst

    override val isSuspend: Boolean get() = false

    override fun equals(other: Any?): Boolean {
        val that = other.asKPropertyImpl() ?: return false
        return container == that.container && name == that.name && signature == that.signature && rawBoundReceiver == that.rawBoundReceiver
    }

    override fun hashCode(): Int =
        (container.hashCode() * 31 + name.hashCode()) * 31 + signature.hashCode()

    override fun toString(): String =
        ReflectionObjectRenderer.renderProperty(descriptor)

    abstract class Accessor<out PropertyType, out ReturnType> :
        KCallableImpl<ReturnType>(), KProperty.Accessor<PropertyType>, KFunction<ReturnType> {
        abstract override val property: KPropertyImpl<PropertyType>

        abstract override val descriptor: PropertyAccessorDescriptor

        override val container: KDeclarationContainerImpl get() = property.container

        override val defaultCaller: Caller<*>? get() = null

        override val isBound: Boolean get() = property.isBound

        override val isInline: Boolean get() = descriptor.isInline
        override val isExternal: Boolean get() = descriptor.isExternal
        override val isOperator: Boolean get() = descriptor.isOperator
        override val isInfix: Boolean get() = descriptor.isInfix
        override val isSuspend: Boolean get() = descriptor.isSuspend
    }

    abstract class Getter<out V> : Accessor<V, V>(), KProperty.Getter<V> {
        override val name: String get() = "<get-${property.name}>"

        override val descriptor: PropertyGetterDescriptor by ReflectProperties.lazySoft {
            // TODO: default getter created this way won't have any source information
            property.descriptor.getter ?: createDefaultGetter(property.descriptor, Annotations.EMPTY)
        }

        override val caller: Caller<*> by ReflectProperties.lazy {
            computeCallerForAccessor(isGetter = true)
        }

        override fun toString(): String = "getter of $property"

        override fun equals(other: Any?): Boolean =
            other is Getter<*> && property == other.property

        override fun hashCode(): Int =
            property.hashCode()
    }

    abstract class Setter<V> : Accessor<V, Unit>(), KMutableProperty.Setter<V> {
        override val name: String get() = "<set-${property.name}>"

        override val descriptor: PropertySetterDescriptor by ReflectProperties.lazySoft {
            // TODO: default setter created this way won't have any source information
            property.descriptor.setter ?: createDefaultSetter(property.descriptor, Annotations.EMPTY, Annotations.EMPTY)
        }

        override val caller: Caller<*> by ReflectProperties.lazy {
            computeCallerForAccessor(isGetter = false)
        }

        override fun toString(): String = "setter of $property"

        override fun equals(other: Any?): Boolean =
            other is Setter<*> && property == other.property

        override fun hashCode(): Int =
            property.hashCode()
    }

    companion object {
        val EXTENSION_PROPERTY_DELEGATE = Any()
    }
}

internal val KPropertyImpl.Accessor<*, *>.boundReceiver
    get() = property.boundReceiver

private fun KPropertyImpl.Accessor<*, *>.computeCallerForAccessor(isGetter: Boolean): Caller<*> {
    if (KDeclarationContainerImpl.LOCAL_PROPERTY_SIGNATURE.matches(property.signature)) {
        return ThrowingCaller
    }

    fun isJvmStaticProperty(): Boolean =
        property.descriptor.annotations.hasAnnotation(JVM_STATIC)

    fun isNotNullProperty(): Boolean =
        !property.descriptor.returnType.isNullableType()

    fun computeFieldCaller(field: Field): CallerImpl<Field> = when {
        property.descriptor.isJvmFieldPropertyInCompanionObject() || !Modifier.isStatic(field.modifiers) ->
            if (isGetter)
                if (isBound) CallerImpl.FieldGetter.BoundInstance(field, boundReceiver)
                else CallerImpl.FieldGetter.Instance(field)
            else
                if (isBound) CallerImpl.FieldSetter.BoundInstance(field, isNotNullProperty(), boundReceiver)
                else CallerImpl.FieldSetter.Instance(field, isNotNullProperty())
        isJvmStaticProperty() ->
            if (isGetter)
                if (isBound) CallerImpl.FieldGetter.BoundJvmStaticInObject(field)
                else CallerImpl.FieldGetter.JvmStaticInObject(field)
            else
                if (isBound) CallerImpl.FieldSetter.BoundJvmStaticInObject(field, isNotNullProperty())
                else CallerImpl.FieldSetter.JvmStaticInObject(field, isNotNullProperty())
        else ->
            if (isGetter) CallerImpl.FieldGetter.Static(field)
            else CallerImpl.FieldSetter.Static(field, isNotNullProperty())
    }

    val jvmSignature = RuntimeTypeMapper.mapPropertySignature(property.descriptor)
    return when (jvmSignature) {
        is KotlinProperty -> {
            val accessorSignature = if (isGetter) jvmSignature.getterSignature else jvmSignature.setterSignature

            val accessor = accessorSignature?.let { signature ->
                property.container.findMethodBySignature(signature.name, signature.desc)
            }

            when {
                accessor == null -> {
                    if (property.descriptor.isUnderlyingPropertyOfInlineClass() &&
                        property.descriptor.visibility == DescriptorVisibility.INTERNAL
                    ) {
                        val unboxMethod = property.descriptor.containingClass?.toInlineClass()?.getUnboxMethod(property.descriptor)
                            ?: throw KotlinReflectionInternalError("Underlying property of inline class $property should have a field")
                        if (isBound) InternalUnderlyingValOfInlineClass.Bound(unboxMethod, boundReceiver)
                        else InternalUnderlyingValOfInlineClass.Unbound(unboxMethod)
                    } else {
                        val javaField = property.javaField
                            ?: throw KotlinReflectionInternalError("No accessors or field is found for property $property")
                        computeFieldCaller(javaField)
                    }
                }
                !Modifier.isStatic(accessor.modifiers) ->
                    if (isBound) CallerImpl.Method.BoundInstance(accessor, boundReceiver)
                    else CallerImpl.Method.Instance(accessor)
                isJvmStaticProperty() ->
                    if (isBound) CallerImpl.Method.BoundJvmStaticInObject(accessor)
                    else CallerImpl.Method.JvmStaticInObject(accessor)
                else ->
                    if (isBound) CallerImpl.Method.BoundStatic(accessor, boundReceiver)
                    else CallerImpl.Method.Static(accessor)
            }
        }
        is JavaField -> {
            computeFieldCaller(jvmSignature.field)
        }
        is JavaMethodProperty -> {
            val method =
                if (isGetter) jvmSignature.getterMethod
                else jvmSignature.setterMethod ?: throw KotlinReflectionInternalError(
                    "No source found for setter of Java method property: ${jvmSignature.getterMethod}"
                )
            if (isBound) CallerImpl.Method.BoundInstance(method, boundReceiver)
            else CallerImpl.Method.Instance(method)
        }
        is MappedKotlinProperty -> {
            val signature =
                if (isGetter) jvmSignature.getterSignature
                else (jvmSignature.setterSignature ?: throw KotlinReflectionInternalError("No setter found for property $property"))
            val accessor =
                property.container.findMethodBySignature(signature.methodName, signature.methodDesc)
                    ?: throw KotlinReflectionInternalError("No accessor found for property $property")
            assert(!Modifier.isStatic(accessor.modifiers)) { "Mapped property cannot have a static accessor: $property" }

            return if (isBound) CallerImpl.Method.BoundInstance(accessor, boundReceiver)
            else CallerImpl.Method.Instance(accessor)
        }
    }.createInlineClassAwareCallerIfNeeded(descriptor)
}

private fun PropertyDescriptor.isJvmFieldPropertyInCompanionObject(): Boolean {
    val container = containingClass
    if (container == null || !container.isCompanionObject) return false

    val outerClass = container.containingClass
    return when {
        outerClass?.isInterface == true || outerClass?.isAnnotationClass == true ->
            // TODO
            // this is DeserializedPropertyDescriptor && JvmProtoBufUtil.isMovedFromInterfaceCompanion(proto)
            false
        else -> true
    }
}
