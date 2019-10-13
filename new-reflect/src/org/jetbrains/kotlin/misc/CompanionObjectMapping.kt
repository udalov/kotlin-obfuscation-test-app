package org.jetbrains.kotlin.misc

import org.jetbrains.kotlin.builtins.PrimitiveType
import org.jetbrains.kotlin.builtins.StandardNames.FQ_NAMES
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.name.ClassId
import java.util.*

internal object CompanionObjectMapping {
    private val classIds: Set<ClassId> =
        (PrimitiveType.NUMBER_TYPES.map(PrimitiveType::typeFqName) +
                FQ_NAMES.string +
                FQ_NAMES._boolean +
                FQ_NAMES._enum).mapTo(linkedSetOf(), (ClassId)::topLevel)

    fun allClassesWithIntrinsicCompanions(): Set<ClassId> =
        Collections.unmodifiableSet(classIds)

    fun isMappedIntrinsicCompanionObject(classDescriptor: ClassDescriptor): Boolean =
        // DescriptorUtils.isCompanionObject(classDescriptor) && classDescriptor.classId?.outerClassId in classIds
        false // TODO
}
