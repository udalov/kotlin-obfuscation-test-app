/*
 * Copyright 2000-2018 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package org.jetbrains.kotlin.misc

import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.PropertyDescriptor

internal object DescriptorsJvmAbiUtil {
    fun isPropertyWithBackingFieldInOuterClass(propertyDescriptor: PropertyDescriptor): Boolean {
        if (!propertyDescriptor.isReal) return false
        val container = propertyDescriptor.containingClass ?: return false
        if (!container.isCompanionObject) return false
        return isClassCompanionObjectWithBackingFieldsInOuter(container) || hasJvmFieldAnnotation(propertyDescriptor)
    }

    private fun isClassCompanionObjectWithBackingFieldsInOuter(companionObject: ClassDescriptor): Boolean {
        val container = companionObject.containingClass!!
        return !container.isInterface && !container.isAnnotationClass &&
            !CompanionObjectMapping.isMappedIntrinsicCompanionObject(companionObject)
    }

    private fun hasJvmFieldAnnotation(property: PropertyDescriptor): Boolean =
        property.annotations.getAll().any { it.annotationClass == JvmField::class }
}
