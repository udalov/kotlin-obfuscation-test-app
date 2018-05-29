object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        runTests()

        println("$passed/${passed + failed} tests passed")
    }

    private fun runTests() {
        // ------------------------ >8 ------------------------
        test("mapping.fakeOverrides.javaFieldGetterSetter") { tests.mapping.fakeOverrides.javaFieldGetterSetter.box() }
        test("mapping.fakeOverrides.javaMethod") { tests.mapping.fakeOverrides.javaMethod.box() }
        test("mapping.jvmStatic.companionObjectFunction") { tests.mapping.jvmStatic.companionObjectFunction.box() }
        test("mapping.jvmStatic.objectFunction") { tests.mapping.jvmStatic.objectFunction.box() }
        test("mapping.types.unit") { tests.mapping.types.unit.box() }
        test("mapping.types.genericArrayElementType") { tests.mapping.types.genericArrayElementType.box() }
        test("mapping.types.innerGenericTypeArgument") { tests.mapping.types.innerGenericTypeArgument.box() }
        test("mapping.types.topLevelFunctions") { tests.mapping.types.topLevelFunctions.box() }
        test("mapping.types.supertypes") { tests.mapping.types.supertypes.box() }
        test("mapping.types.parameterizedTypes") { tests.mapping.types.parameterizedTypes.box() }
        test("mapping.types.constructors") { tests.mapping.types.constructors.box() }
        test("mapping.types.memberFunctions") { tests.mapping.types.memberFunctions.box() }
        test("mapping.types.propertyAccessors") { tests.mapping.types.propertyAccessors.box() }
        test("mapping.types.array") { tests.mapping.types.array.box() }
        test("mapping.types.withNullability") { tests.mapping.types.withNullability.box() }
        test("mapping.types.parameterizedTypeArgument") { tests.mapping.types.parameterizedTypeArgument.box() }
        test("mapping.types.typeParameters") { tests.mapping.types.typeParameters.box() }
        test("mapping.types.overrideAnyWithPrimitive") { tests.mapping.types.overrideAnyWithPrimitive.box() }
        test("mapping.types.annotationConstructorParameters") { tests.mapping.types.annotationConstructorParameters.box() }
        test("mapping.mappedClassIsEqualToClassLiteral") { tests.mapping.mappedClassIsEqualToClassLiteral.box() }
        test("mapping.topLevelProperty") { tests.mapping.topLevelProperty.box() }
        test("mapping.syntheticFields") { tests.mapping.syntheticFields.box() }
        test("mapping.memberProperty") { tests.mapping.memberProperty.box() }
        test("mapping.extensionProperty") { tests.mapping.extensionProperty.box() }
        test("mapping.propertyAccessors") { tests.mapping.propertyAccessors.box() }
        test("mapping.inlineReifiedFun") { tests.mapping.inlineReifiedFun.box() }
        test("mapping.constructor") { tests.mapping.constructor.box() }
        test("mapping.openSuspendFun") { tests.mapping.openSuspendFun.box() }
        test("mapping.functions") { tests.mapping.functions.box() }
        test("mapping.propertyAccessorsWithJvmName") { tests.mapping.propertyAccessorsWithJvmName.box() }
        test("modifiers.classVisibility") { tests.modifiers.classVisibility.box() }
        test("modifiers.classes") { tests.modifiers.classes.box() }
        test("modifiers.callableModality") { tests.modifiers.callableModality.box() }
        test("modifiers.properties") { tests.modifiers.properties.box() }
        test("modifiers.typeParameters") { tests.modifiers.typeParameters.box() }
        test("modifiers.callableVisibility") { tests.modifiers.callableVisibility.box() }
        test("modifiers.functions") { tests.modifiers.functions.box() }
        test("modifiers.classModality") { tests.modifiers.classModality.box() }
        test("supertypes.genericSubstitution") { tests.supertypes.genericSubstitution.box() }
        test("supertypes.simpleSupertypes") { tests.supertypes.simpleSupertypes.box() }
        test("supertypes.builtInClassSupertypes") { tests.supertypes.builtInClassSupertypes.box() }
        test("supertypes.isSubclassOfIsSuperclassOf") { tests.supertypes.isSubclassOfIsSuperclassOf.box() }
        test("supertypes.primitives") { tests.supertypes.primitives.box() }
        test("types.classifierIsClass") { tests.types.classifierIsClass.box() }
        test("types.innerGenericArguments") { tests.types.innerGenericArguments.box() }
        test("types.jvmErasureOfClass") { tests.types.jvmErasureOfClass.box() }
        test("types.useSiteVariance") { tests.types.useSiteVariance.box() }
        test("types.classifiersOfBuiltInTypes") { tests.types.classifiersOfBuiltInTypes.box() }
        test("types.classifierIsTypeParameter") { tests.types.classifierIsTypeParameter.box() }
        test("types.createType.equality") { tests.types.createType.equality.box() }
        test("types.createType.innerGeneric") { tests.types.createType.innerGeneric.box() }
        test("types.createType.typeParameter") { tests.types.createType.typeParameter.box() }
        test("types.createType.simpleCreateType") { tests.types.createType.simpleCreateType.box() }
        test("types.createType.wrongNumberOfArguments") { tests.types.createType.wrongNumberOfArguments.box() }
        test("types.subtyping.simpleGenericTypes") { tests.types.subtyping.simpleGenericTypes.box() }
        test("types.subtyping.simpleSubtypeSupertype") { tests.types.subtyping.simpleSubtypeSupertype.box() }
        test("types.subtyping.typeProjection") { tests.types.subtyping.typeProjection.box() }
        test("types.jvmErasureOfTypeParameter") { tests.types.jvmErasureOfTypeParameter.box() }
        test("types.typeArguments") { tests.types.typeArguments.box() }
        test("classes.objectInstance") { tests.classes.objectInstance.box() }
        test("classes.nestedClasses") { tests.classes.nestedClasses.box() }
        test("classes.localClassSimpleName") { tests.classes.localClassSimpleName.box() }
        test("classes.companionObject") { tests.classes.companionObject.box() }
        test("classes.createInstance") { tests.classes.createInstance.box() }
        test("classes.primitiveKClassEquality") { tests.classes.primitiveKClassEquality.box() }
        test("classes.jvmNameOfStandardClasses") { tests.classes.jvmNameOfStandardClasses.box() }
        test("classes.qualifiedNameOfStandardClasses") { tests.classes.qualifiedNameOfStandardClasses.box() }
        test("classes.starProjectedType") { tests.classes.starProjectedType.box() }
        test("classes.javaVoid") { tests.classes.javaVoid.box() }
        test("classes.classSimpleName") { tests.classes.classSimpleName.box() }
        test("createAnnotation.callByKotlin") { tests.createAnnotation.callByKotlin.box() }
        test("createAnnotation.parameterNamedEquals") { tests.createAnnotation.parameterNamedEquals.box() }
        test("createAnnotation.primitivesAndArrays") { tests.createAnnotation.primitivesAndArrays.box() }
        test("createAnnotation.enumKClassAnnotation") { tests.createAnnotation.enumKClassAnnotation.box() }
        test("createAnnotation.annotationType") { tests.createAnnotation.annotationType.box() }
        test("createAnnotation.arrayOfKClasses") { tests.createAnnotation.arrayOfKClasses.box() }
        test("createAnnotation.callKotlin") { tests.createAnnotation.callKotlin.box() }
        test("createAnnotation.createJdkAnnotationInstance") { tests.createAnnotation.createJdkAnnotationInstance.box() }
        test("createAnnotation.floatingPointParameters") { tests.createAnnotation.floatingPointParameters.box() }
        test("kClassInAnnotation.basic") { tests.kClassInAnnotation.basic.box() }
        test("kClassInAnnotation.vararg") { tests.kClassInAnnotation.vararg.box() }
        test("kClassInAnnotation.array") { tests.kClassInAnnotation.array.box() }
        test("kClassInAnnotation.checkcast") { tests.kClassInAnnotation.checkcast.box() }
        test("kClassInAnnotation.wrappingForCallableReferences") { tests.kClassInAnnotation.wrappingForCallableReferences.box() }
        test("kClassInAnnotation.forceWrapping") { tests.kClassInAnnotation.forceWrapping.box() }
        test("callBy.boundJvmStaticInObject") { tests.callBy.boundJvmStaticInObject.box() }
        test("callBy.defaultAndNonDefaultIntertwined") { tests.callBy.defaultAndNonDefaultIntertwined.box() }
        test("callBy.manyArgumentsNoneDefaultConstructor") { tests.callBy.manyArgumentsNoneDefaultConstructor.box() }
        test("callBy.nonDefaultParameterOmitted") { tests.callBy.nonDefaultParameterOmitted.box() }
        test("callBy.jvmStaticInObject") { tests.callBy.jvmStaticInObject.box() }
        test("callBy.nullValue") { tests.callBy.nullValue.box() }
        test("callBy.simpleConstructor") { tests.callBy.simpleConstructor.box() }
        test("callBy.manyMaskArguments") { tests.callBy.manyMaskArguments.box() }
        test("callBy.manyArgumentsNoneDefaultFunction") { tests.callBy.manyArgumentsNoneDefaultFunction.box() }
        test("callBy.companionObject") { tests.callBy.companionObject.box() }
        test("callBy.ordinaryMethodIsInvokedWhenNoDefaultValuesAreUsed") { tests.callBy.ordinaryMethodIsInvokedWhenNoDefaultValuesAreUsed.box() }
        test("callBy.simpleTopLevelFunction") { tests.callBy.simpleTopLevelFunction.box() }
        test("callBy.jvmStaticInCompanionObject") { tests.callBy.jvmStaticInCompanionObject.box() }
        test("callBy.boundExtensionPropertyAcessor") { tests.callBy.boundExtensionPropertyAcessor.box() }
        test("callBy.extensionFunction") { tests.callBy.extensionFunction.box() }
        test("callBy.privateMemberFunction") { tests.callBy.privateMemberFunction.box() }
        test("callBy.boundExtensionFunction") { tests.callBy.boundExtensionFunction.box() }
        test("callBy.manyArgumentsOnlyOneDefault") { tests.callBy.manyArgumentsOnlyOneDefault.box() }
        test("callBy.simpleMemberFunciton") { tests.callBy.simpleMemberFunciton.box() }
        test("callBy.primitiveDefaultValues") { tests.callBy.primitiveDefaultValues.box() }
        test("methodsFromAny.parametersEqualsHashCode") { tests.methodsFromAny.parametersEqualsHashCode.box() }
        test("methodsFromAny.extensionPropertyReceiverToString") { tests.methodsFromAny.extensionPropertyReceiverToString.box() }
        test("methodsFromAny.typeParametersToString") { tests.methodsFromAny.typeParametersToString.box() }
        test("methodsFromAny.functionEqualsHashCode") { tests.methodsFromAny.functionEqualsHashCode.box() }
        test("methodsFromAny.callableReferencesEqualToCallablesFromAPI") { tests.methodsFromAny.callableReferencesEqualToCallablesFromAPI.box() }
        test("methodsFromAny.parametersToString") { tests.methodsFromAny.parametersToString.box() }
        test("methodsFromAny.typeParametersEqualsHashCode") { tests.methodsFromAny.typeParametersEqualsHashCode.box() }
        test("methodsFromAny.typeEqualsHashCode") { tests.methodsFromAny.typeEqualsHashCode.box() }
        test("methodsFromAny.propertyEqualsHashCode") { tests.methodsFromAny.propertyEqualsHashCode.box() }
        test("classLiterals.genericClass") { tests.classLiterals.genericClass.box() }
        test("classLiterals.genericArrays") { tests.classLiterals.genericArrays.box() }
        test("classLiterals.builtinClassLiterals") { tests.classLiterals.builtinClassLiterals.box() }
        test("classLiterals.arrays") { tests.classLiterals.arrays.box() }
        test("classLiterals.reifiedTypeClassLiteral") { tests.classLiterals.reifiedTypeClassLiteral.box() }
        test("classLiterals.annotationClassLiteral") { tests.classLiterals.annotationClassLiteral.box() }
        test("annotations.privateAnnotation") { tests.annotations.privateAnnotation.box() }
        test("annotations.findAnnotation") { tests.annotations.findAnnotation.box() }
        test("annotations.annotationRetentionAnnotation") { tests.annotations.annotationRetentionAnnotation.box() }
        test("annotations.simpleFunAnnotation") { tests.annotations.simpleFunAnnotation.box() }
        test("annotations.simpleParamAnnotation") { tests.annotations.simpleParamAnnotation.box() }
        test("annotations.propertyAccessors") { tests.annotations.propertyAccessors.box() }
        test("annotations.simpleConstructorAnnotation") { tests.annotations.simpleConstructorAnnotation.box() }
        test("annotations.simpleClassAnnotation") { tests.annotations.simpleClassAnnotation.box() }
        test("annotations.simpleValAnnotation") { tests.annotations.simpleValAnnotation.box() }
        test("annotations.retentions") { tests.annotations.retentions.box() }
        test("annotations.openSuspendFun") { tests.annotations.openSuspendFun.box() }
        test("annotations.propertyWithoutBackingField") { tests.annotations.propertyWithoutBackingField.box() }
        test("constructors.simpleGetConstructors") { tests.constructors.simpleGetConstructors.box() }
        test("constructors.constructorName") { tests.constructors.constructorName.box() }
        test("constructors.primaryConstructor") { tests.constructors.primaryConstructor.box() }
        test("constructors.annotationClass") { tests.constructors.annotationClass.box() }
        test("constructors.classesWithoutConstructors") { tests.constructors.classesWithoutConstructors.box() }
        test("call.cannotCallEnumConstructor") { tests.call.cannotCallEnumConstructor.box() }
        test("call.jvmStaticInObjectIncorrectReceiver") { tests.call.jvmStaticInObjectIncorrectReceiver.box() }
        test("call.fakeOverride") { tests.call.fakeOverride.box() }
        test("call.simpleConstructor") { tests.call.simpleConstructor.box() }
        test("call.jvmStatic") { tests.call.jvmStatic.box() }
        test("call.simpleMemberFunction") { tests.call.simpleMemberFunction.box() }
        test("call.localClassMember") { tests.call.localClassMember.box() }
        test("call.propertyAccessors") { tests.call.propertyAccessors.box() }
        test("call.simpleTopLevelFunctions") { tests.call.simpleTopLevelFunctions.box() }
        test("call.innerClassConstructor") { tests.call.innerClassConstructor.box() }
        test("call.propertyGetterAndGetFunctionDifferentReturnType") { tests.call.propertyGetterAndGetFunctionDifferentReturnType.box() }
        test("call.exceptionHappened") { tests.call.exceptionHappened.box() }
        test("call.memberOfGenericClass") { tests.call.memberOfGenericClass.box() }
        test("call.bound.companionObjectPropertyAccessors") { tests.call.bound.companionObjectPropertyAccessors.box() }
        test("call.bound.objectPropertyAccessors") { tests.call.bound.objectPropertyAccessors.box() }
        test("call.bound.innerClassConstructor") { tests.call.bound.innerClassConstructor.box() }
        test("call.bound.jvmStaticObjectPropertyAccessors") { tests.call.bound.jvmStaticObjectPropertyAccessors.box() }
        test("call.bound.memberFunction") { tests.call.bound.memberFunction.box() }
        test("call.bound.extensionFunction") { tests.call.bound.extensionFunction.box() }
        test("call.bound.jvmStaticCompanionObjectPropertyAccessors") { tests.call.bound.jvmStaticCompanionObjectPropertyAccessors.box() }
        test("call.bound.jvmStaticObjectFunction") { tests.call.bound.jvmStaticObjectFunction.box() }
        test("call.bound.extensionPropertyAccessors") { tests.call.bound.extensionPropertyAccessors.box() }
        test("call.bound.objectFunction") { tests.call.bound.objectFunction.box() }
        test("call.bound.memberPropertyAccessors") { tests.call.bound.memberPropertyAccessors.box() }
        test("call.fakeOverrideSubstituted") { tests.call.fakeOverrideSubstituted.box() }
        test("call.equalsHashCodeToString") { tests.call.equalsHashCodeToString.box() }
        test("call.protectedMembers") { tests.call.protectedMembers.box() }
        test("call.disallowNullValueForNotNullField") { tests.call.disallowNullValueForNotNullField.box() }
        test("call.returnUnit") { tests.call.returnUnit.box() }
        test("call.incorrectNumberOfArguments") { tests.call.incorrectNumberOfArguments.box() }
        test("call.privateProperty") { tests.call.privateProperty.box() }
        test("properties.simpleGetProperties") { tests.properties.simpleGetProperties.box() }
        test("properties.getDelegate.noSetAccessibleTrue") { tests.properties.getDelegate.noSetAccessibleTrue.box() }
        test("properties.getDelegate.booleanPropertyNameStartsWithIs") { tests.properties.getDelegate.booleanPropertyNameStartsWithIs.box() }
        test("properties.getDelegate.getExtensionDelegate") { tests.properties.getDelegate.getExtensionDelegate.box() }
        test("properties.getDelegate.fakeOverride") { tests.properties.getDelegate.fakeOverride.box() }
        test("properties.getDelegate.topLevelProperty") { tests.properties.getDelegate.topLevelProperty.box() }
        test("properties.getDelegate.overrideDelegatedByDelegated") { tests.properties.getDelegate.overrideDelegatedByDelegated.box() }
        test("properties.getDelegate.notDelegatedProperty") { tests.properties.getDelegate.notDelegatedProperty.box() }
        test("properties.getDelegate.memberProperty") { tests.properties.getDelegate.memberProperty.box() }
        test("properties.getDelegate.extensionProperty") { tests.properties.getDelegate.extensionProperty.box() }
        test("properties.getDelegate.boundExtensionProperty") { tests.properties.getDelegate.boundExtensionProperty.box() }
        test("properties.getDelegate.kPropertyForDelegatedProperty") { tests.properties.getDelegate.kPropertyForDelegatedProperty.box() }
        test("properties.getDelegate.memberExtensionProperty") { tests.properties.getDelegate.memberExtensionProperty.box() }
        test("properties.getDelegate.nameClashClassAndCompanion") { tests.properties.getDelegate.nameClashClassAndCompanion.box() }
        test("properties.getDelegate.boundMemberProperty") { tests.properties.getDelegate.boundMemberProperty.box() }
        test("properties.allVsDeclared") { tests.properties.allVsDeclared.box() }
        test("properties.privatePropertyCallIsAccessibleOnAccessors") { tests.properties.privatePropertyCallIsAccessibleOnAccessors.box() }
        test("properties.getExtensionPropertiesMutableVsReadonly") { tests.properties.getExtensionPropertiesMutableVsReadonly.box() }
        test("properties.getPropertiesMutableVsReadonly") { tests.properties.getPropertiesMutableVsReadonly.box() }
        test("properties.publicClassValAccessible") { tests.properties.publicClassValAccessible.box() }
        test("properties.accessors.topLevelPropertyAccessors") { tests.properties.accessors.topLevelPropertyAccessors.box() }
        test("properties.accessors.accessorNames") { tests.properties.accessors.accessorNames.box() }
        test("properties.accessors.extensionPropertyAccessors") { tests.properties.accessors.extensionPropertyAccessors.box() }
        test("properties.accessors.memberPropertyAccessors") { tests.properties.accessors.memberPropertyAccessors.box() }
        test("properties.accessors.memberExtensions") { tests.properties.accessors.memberExtensions.box() }
        test("properties.privateClassVal") { tests.properties.privateClassVal.box() }
        test("properties.localDelegated.inlineFun") { tests.properties.localDelegated.inlineFun.box() }
        test("properties.localDelegated.defaultImpls") { tests.properties.localDelegated.defaultImpls.box() }
        test("properties.localDelegated.variableOfGenericType") { tests.properties.localDelegated.variableOfGenericType.box() }
        test("properties.localDelegated.localDelegatedProperty") { tests.properties.localDelegated.localDelegatedProperty.box() }
        test("properties.invokeKProperty") { tests.properties.invokeKProperty.box() }
        test("properties.genericClassLiteralPropertyReceiverIsStar") { tests.properties.genericClassLiteralPropertyReceiverIsStar.box() }
        test("properties.protectedClassVar") { tests.properties.protectedClassVar.box() }
        test("properties.privateJvmStaticVarInObject") { tests.properties.privateJvmStaticVarInObject.box() }
        test("properties.privateFakeOverrideFromSuperclass") { tests.properties.privateFakeOverrideFromSuperclass.box() }
        test("properties.privateClassVar") { tests.properties.privateClassVar.box() }
        test("properties.fakeOverridesInSubclass") { tests.properties.fakeOverridesInSubclass.box() }
        test("properties.privateToThisAccessors") { tests.properties.privateToThisAccessors.box() }
        test("properties.callPrivatePropertyFromGetProperties") { tests.properties.callPrivatePropertyFromGetProperties.box() }
        test("properties.propertyOfNestedClassAndArrayType") { tests.properties.propertyOfNestedClassAndArrayType.box() }
        test("properties.memberAndMemberExtensionWithSameName") { tests.properties.memberAndMemberExtensionWithSameName.box() }
        test("parameters.functionParameterNameAndIndex") { tests.parameters.functionParameterNameAndIndex.box() }
        test("parameters.boundObjectMemberReferences") { tests.parameters.boundObjectMemberReferences.box() }
        test("parameters.instanceExtensionReceiverAndValueParameters") { tests.parameters.instanceExtensionReceiverAndValueParameters.box() }
        test("parameters.propertySetter") { tests.parameters.propertySetter.box() }
        test("parameters.isOptional") { tests.parameters.isOptional.box() }
        test("parameters.isMarkedNullable") { tests.parameters.isMarkedNullable.box() }
        test("parameters.kinds") { tests.parameters.kinds.box() }
        test("parameters.boundReferences") { tests.parameters.boundReferences.box() }
        test("parameters.boundInnerClassConstructor") { tests.parameters.boundInnerClassConstructor.box() }
        test("lambdaClasses.parameterNamesAndNullability") { tests.lambdaClasses.parameterNamesAndNullability.box() }
        test("functions.simpleNames") { tests.functions.simpleNames.box() }
        test("functions.platformName") { tests.functions.platformName.box() }
        test("functions.privateMemberFunction") { tests.functions.privateMemberFunction.box() }
        test("functions.functionFromStdlib") { tests.functions.functionFromStdlib.box() }
        test("functions.simpleGetFunctions") { tests.functions.simpleGetFunctions.box() }
        test("isInstance.isInstanceCastAndSafeCast") { tests.isInstance.isInstanceCastAndSafeCast.box() }
        test("typeParameters.typeParametersAndNames") { tests.typeParameters.typeParametersAndNames.box() }
        test("typeParameters.declarationSiteVariance") { tests.typeParameters.declarationSiteVariance.box() }
        test("typeParameters.upperBounds") { tests.typeParameters.upperBounds.box() }
        // ----------------------------------------------------
    }
}

private var passed = 0
private var failed = 0

private inline fun test(name: String, box: () -> String) {
    printTestName(name)
    try {
        ok(box())
    } catch (e: Throwable) {
        fail(e)
    }
}

private fun printTestName(name: String) {
    print(name)
    print(": ")
}

private fun ok(result: String) {
    println(result)
    if (result == "OK") passed++ else failed++
}

private fun fail(e: Throwable) {
    println("EXCEPTION")
    e.printStackTrace()
    failed++
}
