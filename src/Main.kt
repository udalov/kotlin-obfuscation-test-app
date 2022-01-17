object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        runTests()

        println("$passed/${passed + failed} tests passed")
    }

    private fun runTests() {
        // ------------------------ >8 ------------------------
        test("annotations.annotationRetentionAnnotation") { tests.annotations.annotationRetentionAnnotation.box() }
        test("annotations.findAnnotation") { tests.annotations.findAnnotation.box() }
        test("annotations.genericExtensionProperty") { tests.annotations.genericExtensionProperty.box() }
        test("annotations.hasAnnotation") { tests.annotations.hasAnnotation.box() }
        test("annotations.localClassLiteral") { tests.annotations.localClassLiteral.box() }
        test("annotations.localClassParameterAnnotation") { tests.annotations.localClassParameterAnnotation.box() }
        test("annotations.onTypes.arrayKClass") { tests.annotations.onTypes.arrayKClass.box() }
        test("annotations.onTypes.classLiteralWithExpectedType") { tests.annotations.onTypes.classLiteralWithExpectedType.box() }
        test("annotations.onTypes.differentArgumentTypes") { tests.annotations.onTypes.differentArgumentTypes.box() }
        test("annotations.onTypes.differentPositions") { tests.annotations.onTypes.differentPositions.box() }
        test("annotations.openSuspendFun") { tests.annotations.openSuspendFun.box() }
        test("annotations.privateAnnotation") { tests.annotations.privateAnnotation.box() }
        test("annotations.propertyAccessors") { tests.annotations.propertyAccessors.box() }
        test("annotations.propertyWithoutBackingField") { tests.annotations.propertyWithoutBackingField.box() }
        test("annotations.repeatable.jvmRepeatableKotlinAnnotation") { tests.annotations.repeatable.jvmRepeatableKotlinAnnotation.box() }
        test("annotations.repeatable.kotlinAnnotation") { tests.annotations.repeatable.kotlinAnnotation.box() }
        test("annotations.repeatable.nonRepeatedAnnotationWithItsContainer") { tests.annotations.repeatable.nonRepeatedAnnotationWithItsContainer.box() }
        test("annotations.retentions") { tests.annotations.retentions.box() }
        test("annotations.setparam") { tests.annotations.setparam.box() }
        test("annotations.simpleClassAnnotation") { tests.annotations.simpleClassAnnotation.box() }
        test("annotations.simpleConstructorAnnotation") { tests.annotations.simpleConstructorAnnotation.box() }
        test("annotations.simpleFunAnnotation") { tests.annotations.simpleFunAnnotation.box() }
        test("annotations.simpleParamAnnotation") { tests.annotations.simpleParamAnnotation.box() }
        test("annotations.simpleValAnnotation") { tests.annotations.simpleValAnnotation.box() }
        test("builtins.collections") { tests.builtins.collections.box() }
        test("builtins.enumNameOrdinal") { tests.builtins.enumNameOrdinal.box() }
        test("builtins.stringLength") { tests.builtins.stringLength.box() }
        test("call.bigArity") { tests.call.bigArity.box() }
        test("call.bound.companionObjectPropertyAccessors") { tests.call.bound.companionObjectPropertyAccessors.box() }
        test("call.bound.extensionFunction") { tests.call.bound.extensionFunction.box() }
        test("call.bound.extensionPropertyAccessors") { tests.call.bound.extensionPropertyAccessors.box() }
        test("call.bound.innerClassConstructor") { tests.call.bound.innerClassConstructor.box() }
        test("call.bound.jvmStaticCompanionObjectPropertyAccessors") { tests.call.bound.jvmStaticCompanionObjectPropertyAccessors.box() }
        test("call.bound.jvmStaticObjectFunction") { tests.call.bound.jvmStaticObjectFunction.box() }
        test("call.bound.jvmStaticObjectPropertyAccessors") { tests.call.bound.jvmStaticObjectPropertyAccessors.box() }
        test("call.bound.memberFunction") { tests.call.bound.memberFunction.box() }
        test("call.bound.memberPropertyAccessors") { tests.call.bound.memberPropertyAccessors.box() }
        test("call.bound.objectFunction") { tests.call.bound.objectFunction.box() }
        test("call.bound.objectPropertyAccessors") { tests.call.bound.objectPropertyAccessors.box() }
        test("call.cannotCallEnumConstructor") { tests.call.cannotCallEnumConstructor.box() }
        test("call.disallowNullValueForNotNullField") { tests.call.disallowNullValueForNotNullField.box() }
        test("call.equalsHashCodeToString") { tests.call.equalsHashCodeToString.box() }
        test("call.exceptionHappened") { tests.call.exceptionHappened.box() }
        test("call.fakeOverride") { tests.call.fakeOverride.box() }
        test("call.fakeOverrideSubstituted") { tests.call.fakeOverrideSubstituted.box() }
        test("call.incorrectNumberOfArguments") { tests.call.incorrectNumberOfArguments.box() }
        test("call.inlineClasses.constructorWithInlineClassParameters") { tests.call.inlineClasses.constructorWithInlineClassParameters.box() }
        test("call.inlineClasses.fieldAccessors") { tests.call.inlineClasses.fieldAccessors.box() }
        test("call.inlineClasses.functionsWithInlineClassParameters") { tests.call.inlineClasses.functionsWithInlineClassParameters.box() }
        test("call.inlineClasses.inlineClassConstructor") { tests.call.inlineClasses.inlineClassConstructor.box() }
        test("call.inlineClasses.internalPrimaryValOfInlineClass") { tests.call.inlineClasses.internalPrimaryValOfInlineClass.box() }
        test("call.inlineClasses.jvmStaticFieldInObject") { tests.call.inlineClasses.jvmStaticFieldInObject.box() }
        test("call.inlineClasses.jvmStaticFunction") { tests.call.inlineClasses.jvmStaticFunction.box() }
        test("call.inlineClasses.nonOverridingFunOfInlineClass") { tests.call.inlineClasses.nonOverridingFunOfInlineClass.box() }
        test("call.inlineClasses.nonOverridingVarOfInlineClass") { tests.call.inlineClasses.nonOverridingVarOfInlineClass.box() }
        test("call.inlineClasses.overridingFunOfInlineClass") { tests.call.inlineClasses.overridingFunOfInlineClass.box() }
        test("call.inlineClasses.overridingVarOfInlineClass") { tests.call.inlineClasses.overridingVarOfInlineClass.box() }
        test("call.inlineClasses.primaryValOfInlineClass") { tests.call.inlineClasses.primaryValOfInlineClass.box() }
        test("call.inlineClasses.properties") { tests.call.inlineClasses.properties.box() }
        test("call.inlineClasses.suspendFunction") { tests.call.inlineClasses.suspendFunction.box() }
        test("call.innerClassConstructor") { tests.call.innerClassConstructor.box() }
        test("call.jvmStatic") { tests.call.jvmStatic.box() }
        test("call.jvmStaticInObjectIncorrectReceiver") { tests.call.jvmStaticInObjectIncorrectReceiver.box() }
        test("call.localClassMember") { tests.call.localClassMember.box() }
        test("call.memberOfGenericClass") { tests.call.memberOfGenericClass.box() }
        test("call.privateProperty") { tests.call.privateProperty.box() }
        test("call.propertyAccessors") { tests.call.propertyAccessors.box() }
        test("call.propertyGetterAndGetFunctionDifferentReturnType") { tests.call.propertyGetterAndGetFunctionDifferentReturnType.box() }
        test("call.protectedMembers") { tests.call.protectedMembers.box() }
        test("call.returnUnit") { tests.call.returnUnit.box() }
        test("call.simpleConstructor") { tests.call.simpleConstructor.box() }
        test("call.simpleMemberFunction") { tests.call.simpleMemberFunction.box() }
        test("call.simpleTopLevelFunctions") { tests.call.simpleTopLevelFunctions.box() }
        test("callBy.boundExtensionFunction") { tests.callBy.boundExtensionFunction.box() }
        test("callBy.boundExtensionPropertyAcessor") { tests.callBy.boundExtensionPropertyAcessor.box() }
        test("callBy.boundJvmStaticInObject") { tests.callBy.boundJvmStaticInObject.box() }
        test("callBy.companionObject") { tests.callBy.companionObject.box() }
        test("callBy.defaultAndNonDefaultIntertwined") { tests.callBy.defaultAndNonDefaultIntertwined.box() }
        test("callBy.defaultInSuperClass") { tests.callBy.defaultInSuperClass.box() }
        test("callBy.defaultInSuperInterface") { tests.callBy.defaultInSuperInterface.box() }
        test("callBy.emptyVarArg") { tests.callBy.emptyVarArg.box() }
        test("callBy.extensionFunction") { tests.callBy.extensionFunction.box() }
        test("callBy.inlineClassDefaultArguments") { tests.callBy.inlineClassDefaultArguments.box() }
        test("callBy.inlineClassFunctionsAndConstructors") { tests.callBy.inlineClassFunctionsAndConstructors.box() }
        test("callBy.inlineClassMembers") { tests.callBy.inlineClassMembers.box() }
        test("callBy.jvmStaticInCompanionObject") { tests.callBy.jvmStaticInCompanionObject.box() }
        test("callBy.jvmStaticInObject") { tests.callBy.jvmStaticInObject.box() }
        test("callBy.manyArgumentsNoneDefaultConstructor") { tests.callBy.manyArgumentsNoneDefaultConstructor.box() }
        test("callBy.manyArgumentsNoneDefaultFunction") { tests.callBy.manyArgumentsNoneDefaultFunction.box() }
        test("callBy.manyArgumentsOnlyOneDefault") { tests.callBy.manyArgumentsOnlyOneDefault.box() }
        test("callBy.manyMaskArguments") { tests.callBy.manyMaskArguments.box() }
        test("callBy.nonDefaultParameterOmitted") { tests.callBy.nonDefaultParameterOmitted.box() }
        test("callBy.nullValue") { tests.callBy.nullValue.box() }
        test("callBy.ordinaryMethodIsInvokedWhenNoDefaultValuesAreUsed") { tests.callBy.ordinaryMethodIsInvokedWhenNoDefaultValuesAreUsed.box() }
        test("callBy.primitiveDefaultValues") { tests.callBy.primitiveDefaultValues.box() }
        test("callBy.privateMemberFunction") { tests.callBy.privateMemberFunction.box() }
        test("callBy.simpleConstructor") { tests.callBy.simpleConstructor.box() }
        test("callBy.simpleMemberFunciton") { tests.callBy.simpleMemberFunciton.box() }
        test("callBy.simpleTopLevelFunction") { tests.callBy.simpleTopLevelFunction.box() }
        test("classLiterals.annotationClassLiteral") { tests.classLiterals.annotationClassLiteral.box() }
        test("classLiterals.arrays") { tests.classLiterals.arrays.box() }
        test("classLiterals.bareArray") { tests.classLiterals.bareArray.box() }
        test("classLiterals.builtinClassLiterals") { tests.classLiterals.builtinClassLiterals.box() }
        test("classLiterals.genericArrays") { tests.classLiterals.genericArrays.box() }
        test("classLiterals.genericClass") { tests.classLiterals.genericClass.box() }
        test("classLiterals.lambdaClass") { tests.classLiterals.lambdaClass.box() }
        test("classLiterals.reifiedTypeClassLiteral") { tests.classLiterals.reifiedTypeClassLiteral.box() }
        test("classes.classSimpleName") { tests.classes.classSimpleName.box() }
        test("classes.companionObject") { tests.classes.companionObject.box() }
        test("classes.createInstance") { tests.classes.createInstance.box() }
        test("classes.javaVoid") { tests.classes.javaVoid.box() }
        test("classes.jvmName") { tests.classes.jvmName.box() }
        test("classes.jvmNameOfStandardClasses") { tests.classes.jvmNameOfStandardClasses.box() }
        test("classes.localClassSimpleName") { tests.classes.localClassSimpleName.box() }
        test("classes.nestedClasses") { tests.classes.nestedClasses.box() }
        test("classes.objectInstance") { tests.classes.objectInstance.box() }
        test("classes.primitiveKClassEquality") { tests.classes.primitiveKClassEquality.box() }
        test("classes.qualifiedName") { tests.classes.qualifiedName.box() }
        test("classes.qualifiedNameOfStandardClasses") { tests.classes.qualifiedNameOfStandardClasses.box() }
        test("classes.sealedSubclasses") { tests.classes.sealedSubclasses.box() }
        test("classes.starProjectedType") { tests.classes.starProjectedType.box() }
        test("constructors.annotationClass") { tests.constructors.annotationClass.box() }
        test("constructors.classesWithoutConstructors") { tests.constructors.classesWithoutConstructors.box() }
        test("constructors.constructorName") { tests.constructors.constructorName.box() }
        test("constructors.enumEntry") { tests.constructors.enumEntry.box() }
        test("constructors.primaryConstructor") { tests.constructors.primaryConstructor.box() }
        test("constructors.simpleGetConstructors") { tests.constructors.simpleGetConstructors.box() }
        test("createAnnotation.annotationType") { tests.createAnnotation.annotationType.box() }
        test("createAnnotation.arrayOfKClasses") { tests.createAnnotation.arrayOfKClasses.box() }
        test("createAnnotation.callByKotlin") { tests.createAnnotation.callByKotlin.box() }
        test("createAnnotation.callByWithEmptyVarArg") { tests.createAnnotation.callByWithEmptyVarArg.box() }
        test("createAnnotation.callKotlin") { tests.createAnnotation.callKotlin.box() }
        test("createAnnotation.createJdkAnnotationInstance") { tests.createAnnotation.createJdkAnnotationInstance.box() }
        test("createAnnotation.enumKClassAnnotation") { tests.createAnnotation.enumKClassAnnotation.box() }
        test("createAnnotation.equalsHashCodeToString") { tests.createAnnotation.equalsHashCodeToString.box() }
        test("createAnnotation.floatingPointParameters") { tests.createAnnotation.floatingPointParameters.box() }
        test("createAnnotation.parameterNamedEquals") { tests.createAnnotation.parameterNamedEquals.box() }
        test("createAnnotation.primitivesAndArrays") { tests.createAnnotation.primitivesAndArrays.box() }
        test("functions.enumValuesValueOf") { tests.functions.enumValuesValueOf.box() }
        test("functions.functionFromStdlib") { tests.functions.functionFromStdlib.box() }
        test("functions.genericOverriddenFunction") { tests.functions.genericOverriddenFunction.box() }
        test("functions.isAccessibleOnAllMembers") { tests.functions.isAccessibleOnAllMembers.box() }
        test("functions.platformName") { tests.functions.platformName.box() }
        test("functions.privateMemberFunction") { tests.functions.privateMemberFunction.box() }
        test("functions.simpleGetFunctions") { tests.functions.simpleGetFunctions.box() }
        test("functions.simpleNames") { tests.functions.simpleNames.box() }
        test("isInstance.isInstanceCastAndSafeCast") { tests.isInstance.isInstanceCastAndSafeCast.box() }
        test("kClassInAnnotation.array") { tests.kClassInAnnotation.array.box() }
        test("kClassInAnnotation.basic") { tests.kClassInAnnotation.basic.box() }
        test("kClassInAnnotation.checkcast") { tests.kClassInAnnotation.checkcast.box() }
        test("kClassInAnnotation.forceWrapping") { tests.kClassInAnnotation.forceWrapping.box() }
        test("kClassInAnnotation.vararg") { tests.kClassInAnnotation.vararg.box() }
        test("kClassInAnnotation.wrappingForCallableReferences") { tests.kClassInAnnotation.wrappingForCallableReferences.box() }
        test("lambdaClasses.parameterNamesAndNullability") { tests.lambdaClasses.parameterNamesAndNullability.box() }
        test("lambdaClasses.reflectOnDefaultWithInlineClassArgument") { tests.lambdaClasses.reflectOnDefaultWithInlineClassArgument.box() }
        test("lambdaClasses.reflectOnLambdaInArrayConstructor") { tests.lambdaClasses.reflectOnLambdaInArrayConstructor.box() }
        test("lambdaClasses.reflectOnLambdaInConstructor") { tests.lambdaClasses.reflectOnLambdaInConstructor.box() }
        test("lambdaClasses.reflectOnLambdaInField") { tests.lambdaClasses.reflectOnLambdaInField.box() }
        test("lambdaClasses.reflectOnLambdaInStaticField") { tests.lambdaClasses.reflectOnLambdaInStaticField.box() }
        test("lambdaClasses.reflectOnLambdaInSuspend") { tests.lambdaClasses.reflectOnLambdaInSuspend.box() }
        test("lambdaClasses.reflectOnLambdaInSuspendLambda") { tests.lambdaClasses.reflectOnLambdaInSuspendLambda.box() }
        test("lambdaClasses.reflectOnSuspendLambdaInField") { tests.lambdaClasses.reflectOnSuspendLambdaInField.box() }
        test("mapping.constructor") { tests.mapping.constructor.box() }
        test("mapping.constructorWithInlineClassParameters") { tests.mapping.constructorWithInlineClassParameters.box() }
        test("mapping.extensionProperty") { tests.mapping.extensionProperty.box() }
        test("mapping.fakeOverrides.javaFieldGetterSetter") { tests.mapping.fakeOverrides.javaFieldGetterSetter.box() }
        test("mapping.fakeOverrides.javaMethod") { tests.mapping.fakeOverrides.javaMethod.box() }
        test("mapping.functions") { tests.mapping.functions.box() }
        test("mapping.inlineClasses.inlineClassPrimaryVal") { tests.mapping.inlineClasses.inlineClassPrimaryVal.box() }
        test("mapping.inlineClasses.suspendFunctionWithInlineClassInSignature") { tests.mapping.inlineClasses.suspendFunctionWithInlineClassInSignature.box() }
        test("mapping.inlineReifiedFun") { tests.mapping.inlineReifiedFun.box() }
        test("mapping.interfaceCompanionPropertyWithJvmField") { tests.mapping.interfaceCompanionPropertyWithJvmField.box() }
        test("mapping.jvmStatic.companionObjectFunction") { tests.mapping.jvmStatic.companionObjectFunction.box() }
        test("mapping.jvmStatic.objectFunction") { tests.mapping.jvmStatic.objectFunction.box() }
        test("mapping.lateinitProperty") { tests.mapping.lateinitProperty.box() }
        test("mapping.mappedClassIsEqualToClassLiteral") { tests.mapping.mappedClassIsEqualToClassLiteral.box() }
        test("mapping.memberProperty") { tests.mapping.memberProperty.box() }
        test("mapping.methodsFromObject") { tests.mapping.methodsFromObject.box() }
        test("mapping.methodsFromSuperInterface") { tests.mapping.methodsFromSuperInterface.box() }
        test("mapping.openSuspendFun") { tests.mapping.openSuspendFun.box() }
        test("mapping.privateProperty") { tests.mapping.privateProperty.box() }
        test("mapping.propertyAccessorsWithJvmName") { tests.mapping.propertyAccessorsWithJvmName.box() }
        test("mapping.syntheticFields") { tests.mapping.syntheticFields.box() }
        test("mapping.topLevelProperty") { tests.mapping.topLevelProperty.box() }
        test("mapping.types.allSupertypes") { tests.mapping.types.allSupertypes.box() }
        test("mapping.types.annotationConstructorParameters") { tests.mapping.types.annotationConstructorParameters.box() }
        test("mapping.types.array") { tests.mapping.types.array.box() }
        test("mapping.types.constructors") { tests.mapping.types.constructors.box() }
        test("mapping.types.createType") { tests.mapping.types.createType.box() }
        test("mapping.types.genericArrayElementType") { tests.mapping.types.genericArrayElementType.box() }
        test("mapping.types.inlineClassInSignature") { tests.mapping.types.inlineClassInSignature.box() }
        test("mapping.types.inlineClassPrimaryVal") { tests.mapping.types.inlineClassPrimaryVal.box() }
        test("mapping.types.innerGenericTypeArgument") { tests.mapping.types.innerGenericTypeArgument.box() }
        test("mapping.types.memberFunctions") { tests.mapping.types.memberFunctions.box() }
        test("mapping.types.overrideAnyWithPrimitive") { tests.mapping.types.overrideAnyWithPrimitive.box() }
        test("mapping.types.parameterizedTypeArgument") { tests.mapping.types.parameterizedTypeArgument.box() }
        test("mapping.types.parameterizedTypes") { tests.mapping.types.parameterizedTypes.box() }
        test("mapping.types.propertyAccessors") { tests.mapping.types.propertyAccessors.box() }
        test("mapping.types.supertypes") { tests.mapping.types.supertypes.box() }
        test("mapping.types.suspendFun") { tests.mapping.types.suspendFun.box() }
        test("mapping.types.topLevelFunctions") { tests.mapping.types.topLevelFunctions.box() }
        test("mapping.types.typeParameters") { tests.mapping.types.typeParameters.box() }
        test("mapping.types.unit") { tests.mapping.types.unit.box() }
        test("mapping.types.withNullability") { tests.mapping.types.withNullability.box() }
        test("methodsFromAny.adaptedCallableReferencesNotEqualToCallablesFromAPI") { tests.methodsFromAny.adaptedCallableReferencesNotEqualToCallablesFromAPI.box() }
        test("methodsFromAny.builtinFunctionsToString") { tests.methodsFromAny.builtinFunctionsToString.box() }
        test("methodsFromAny.callableReferencesEqualToCallablesFromAPI") { tests.methodsFromAny.callableReferencesEqualToCallablesFromAPI.box() }
        test("methodsFromAny.classToString") { tests.methodsFromAny.classToString.box() }
        test("methodsFromAny.extensionPropertyReceiverToString") { tests.methodsFromAny.extensionPropertyReceiverToString.box() }
        test("methodsFromAny.fakeOverrideEqualsHashCode") { tests.methodsFromAny.fakeOverrideEqualsHashCode.box() }
        test("methodsFromAny.fakeOverrideToString") { tests.methodsFromAny.fakeOverrideToString.box() }
        test("methodsFromAny.fakeOverrideToString2") { tests.methodsFromAny.fakeOverrideToString2.box() }
        test("methodsFromAny.functionEqualsHashCode") { tests.methodsFromAny.functionEqualsHashCode.box() }
        test("methodsFromAny.functionFromStdlibMultiFileFacade") { tests.methodsFromAny.functionFromStdlibMultiFileFacade.box() }
        test("methodsFromAny.functionFromStdlibSingleFileFacade") { tests.methodsFromAny.functionFromStdlibSingleFileFacade.box() }
        test("methodsFromAny.functionToString") { tests.methodsFromAny.functionToString.box() }
        test("methodsFromAny.memberExtensionToString") { tests.methodsFromAny.memberExtensionToString.box() }
        test("methodsFromAny.parametersEqualsHashCode") { tests.methodsFromAny.parametersEqualsHashCode.box() }
        test("methodsFromAny.parametersEqualsWithClearCaches") { tests.methodsFromAny.parametersEqualsWithClearCaches.box() }
        test("methodsFromAny.parametersToString") { tests.methodsFromAny.parametersToString.box() }
        test("methodsFromAny.propertyAccessorEqualsHashCode") { tests.methodsFromAny.propertyAccessorEqualsHashCode.box() }
        test("methodsFromAny.propertyEqualsHashCode") { tests.methodsFromAny.propertyEqualsHashCode.box() }
        test("methodsFromAny.propertyToString") { tests.methodsFromAny.propertyToString.box() }
        test("methodsFromAny.typeEqualsHashCode") { tests.methodsFromAny.typeEqualsHashCode.box() }
        test("methodsFromAny.typeParametersEqualsHashCode") { tests.methodsFromAny.typeParametersEqualsHashCode.box() }
        test("methodsFromAny.typeParametersToString") { tests.methodsFromAny.typeParametersToString.box() }
        test("methodsFromAny.typeToString") { tests.methodsFromAny.typeToString.box() }
        test("methodsFromAny.typeToStringInnerGeneric") { tests.methodsFromAny.typeToStringInnerGeneric.box() }
        test("modifiers.callableModality") { tests.modifiers.callableModality.box() }
        test("modifiers.callableVisibility") { tests.modifiers.callableVisibility.box() }
        test("modifiers.classModality") { tests.modifiers.classModality.box() }
        test("modifiers.classVisibility") { tests.modifiers.classVisibility.box() }
        test("modifiers.functions") { tests.modifiers.functions.box() }
        test("modifiers.properties") { tests.modifiers.properties.box() }
        test("modifiers.typeParameters") { tests.modifiers.typeParameters.box() }
        test("parameters.bigArity") { tests.parameters.bigArity.box() }
        test("parameters.boundInnerClassConstructor") { tests.parameters.boundInnerClassConstructor.box() }
        test("parameters.boundObjectMemberReferences") { tests.parameters.boundObjectMemberReferences.box() }
        test("parameters.boundReferences") { tests.parameters.boundReferences.box() }
        test("parameters.functionParameterNameAndIndex") { tests.parameters.functionParameterNameAndIndex.box() }
        test("parameters.instanceExtensionReceiverAndValueParameters") { tests.parameters.instanceExtensionReceiverAndValueParameters.box() }
        test("parameters.instanceParameterOfFakeOverride") { tests.parameters.instanceParameterOfFakeOverride.box() }
        test("parameters.isMarkedNullable") { tests.parameters.isMarkedNullable.box() }
        test("parameters.isOptional") { tests.parameters.isOptional.box() }
        test("parameters.kinds") { tests.parameters.kinds.box() }
        test("parameters.propertySetter") { tests.parameters.propertySetter.box() }
        test("properties.accessors.accessorNames") { tests.properties.accessors.accessorNames.box() }
        test("properties.accessors.extensionPropertyAccessors") { tests.properties.accessors.extensionPropertyAccessors.box() }
        test("properties.accessors.memberExtensions") { tests.properties.accessors.memberExtensions.box() }
        test("properties.accessors.memberPropertyAccessors") { tests.properties.accessors.memberPropertyAccessors.box() }
        test("properties.accessors.topLevelPropertyAccessors") { tests.properties.accessors.topLevelPropertyAccessors.box() }
        test("properties.allVsDeclared") { tests.properties.allVsDeclared.box() }
        test("properties.callPrivatePropertyFromGetProperties") { tests.properties.callPrivatePropertyFromGetProperties.box() }
        test("properties.fakeOverridesInSubclass") { tests.properties.fakeOverridesInSubclass.box() }
        test("properties.genericClassLiteralPropertyReceiverIsStar") { tests.properties.genericClassLiteralPropertyReceiverIsStar.box() }
        test("properties.genericOverriddenProperty") { tests.properties.genericOverriddenProperty.box() }
        test("properties.genericProperty") { tests.properties.genericProperty.box() }
        test("properties.getDelegate.booleanPropertyNameStartsWithIs") { tests.properties.getDelegate.booleanPropertyNameStartsWithIs.box() }
        test("properties.getDelegate.boundExtensionProperty") { tests.properties.getDelegate.boundExtensionProperty.box() }
        test("properties.getDelegate.boundMemberProperty") { tests.properties.getDelegate.boundMemberProperty.box() }
        test("properties.getDelegate.extensionProperty") { tests.properties.getDelegate.extensionProperty.box() }
        test("properties.getDelegate.fakeOverride") { tests.properties.getDelegate.fakeOverride.box() }
        test("properties.getDelegate.getExtensionDelegate") { tests.properties.getDelegate.getExtensionDelegate.box() }
        test("properties.getDelegate.kPropertyForDelegatedProperty") { tests.properties.getDelegate.kPropertyForDelegatedProperty.box() }
        test("properties.getDelegate.memberExtensionProperty") { tests.properties.getDelegate.memberExtensionProperty.box() }
        test("properties.getDelegate.memberProperty") { tests.properties.getDelegate.memberProperty.box() }
        test("properties.getDelegate.method.delegateMethodIsNonOverridable") { tests.properties.getDelegate.method.delegateMethodIsNonOverridable.box() }
        test("properties.getDelegate.method.delegateToAnother") { tests.properties.getDelegate.method.delegateToAnother.box() }
        test("properties.getDelegate.nameClashClassAndCompanion") { tests.properties.getDelegate.nameClashClassAndCompanion.box() }
        test("properties.getDelegate.noSetAccessibleTrue") { tests.properties.getDelegate.noSetAccessibleTrue.box() }
        test("properties.getDelegate.notDelegatedProperty") { tests.properties.getDelegate.notDelegatedProperty.box() }
        test("properties.getDelegate.overrideDelegatedByDelegated") { tests.properties.getDelegate.overrideDelegatedByDelegated.box() }
        test("properties.getDelegate.topLevelProperty") { tests.properties.getDelegate.topLevelProperty.box() }
        test("properties.getExtensionPropertiesMutableVsReadonly") { tests.properties.getExtensionPropertiesMutableVsReadonly.box() }
        test("properties.getPropertiesMutableVsReadonly") { tests.properties.getPropertiesMutableVsReadonly.box() }
        test("properties.invokeKProperty") { tests.properties.invokeKProperty.box() }
        test("properties.jvmField.annotationCompanionWithAnnotation") { tests.properties.jvmField.annotationCompanionWithAnnotation.box() }
        test("properties.jvmField.interfaceCompanion") { tests.properties.jvmField.interfaceCompanion.box() }
        test("properties.jvmField.interfaceCompanionWithAnnotation") { tests.properties.jvmField.interfaceCompanionWithAnnotation.box() }
        test("properties.localDelegated.defaultImpls") { tests.properties.localDelegated.defaultImpls.box() }
        test("properties.localDelegated.inLambda") { tests.properties.localDelegated.inLambda.box() }
        test("properties.localDelegated.inlineFun") { tests.properties.localDelegated.inlineFun.box() }
        test("properties.localDelegated.localAndNonLocal") { tests.properties.localDelegated.localAndNonLocal.box() }
        test("properties.localDelegated.localDelegatedProperty") { tests.properties.localDelegated.localDelegatedProperty.box() }
        test("properties.localDelegated.variableOfGenericType") { tests.properties.localDelegated.variableOfGenericType.box() }
        test("properties.memberAndMemberExtensionWithSameName") { tests.properties.memberAndMemberExtensionWithSameName.box() }
        test("properties.privateClassVal") { tests.properties.privateClassVal.box() }
        test("properties.privateClassVar") { tests.properties.privateClassVar.box() }
        test("properties.privateFakeOverrideFromSuperclass") { tests.properties.privateFakeOverrideFromSuperclass.box() }
        test("properties.privateJvmStaticVarInObject") { tests.properties.privateJvmStaticVarInObject.box() }
        test("properties.privatePropertyCallIsAccessibleOnAccessors") { tests.properties.privatePropertyCallIsAccessibleOnAccessors.box() }
        test("properties.privateToThisAccessors") { tests.properties.privateToThisAccessors.box() }
        test("properties.propertyOfNestedClassAndArrayType") { tests.properties.propertyOfNestedClassAndArrayType.box() }
        test("properties.protectedClassVar") { tests.properties.protectedClassVar.box() }
        test("properties.publicClassValAccessible") { tests.properties.publicClassValAccessible.box() }
        test("properties.simpleGetProperties") { tests.properties.simpleGetProperties.box() }
        test("properties.withLocalType") { tests.properties.withLocalType.box() }
        test("supertypes.builtInClassSupertypes") { tests.supertypes.builtInClassSupertypes.box() }
        test("supertypes.genericSubstitution") { tests.supertypes.genericSubstitution.box() }
        test("supertypes.isSubclassOfIsSuperclassOf") { tests.supertypes.isSubclassOfIsSuperclassOf.box() }
        test("supertypes.primitives") { tests.supertypes.primitives.box() }
        test("supertypes.simpleSupertypes") { tests.supertypes.simpleSupertypes.box() }
        test("typeOf.annotatedType") { tests.typeOf.annotatedType.box() }
        test("typeOf.classes") { tests.typeOf.classes.box() }
        test("typeOf.inlineClasses") { tests.typeOf.inlineClasses.box() }
        test("typeOf.intersectionType") { tests.typeOf.intersectionType.box() }
        test("typeOf.manyTypeArguments") { tests.typeOf.manyTypeArguments.box() }
        test("typeOf.multipleLayers") { tests.typeOf.multipleLayers.box() }
        test("typeOf.mutableCollections_after") { tests.typeOf.mutableCollections_after.box() }
        test("typeOf.nonReifiedTypeParameters.defaultUpperBound") { tests.typeOf.nonReifiedTypeParameters.defaultUpperBound.box() }
        test("typeOf.nonReifiedTypeParameters.equalsOnClassParameters") { tests.typeOf.nonReifiedTypeParameters.equalsOnClassParameters.box() }
        test("typeOf.nonReifiedTypeParameters.equalsOnClassParametersWithReflectAPI") { tests.typeOf.nonReifiedTypeParameters.equalsOnClassParametersWithReflectAPI.box() }
        test("typeOf.nonReifiedTypeParameters.equalsOnFunctionParameters") { tests.typeOf.nonReifiedTypeParameters.equalsOnFunctionParameters.box() }
        test("typeOf.nonReifiedTypeParameters.innerGeneric") { tests.typeOf.nonReifiedTypeParameters.innerGeneric.box() }
        test("typeOf.nonReifiedTypeParameters.simpleClassParameter") { tests.typeOf.nonReifiedTypeParameters.simpleClassParameter.box() }
        test("typeOf.nonReifiedTypeParameters.simpleFunctionParameter") { tests.typeOf.nonReifiedTypeParameters.simpleFunctionParameter.box() }
        test("typeOf.nonReifiedTypeParameters.simplePropertyParameter") { tests.typeOf.nonReifiedTypeParameters.simplePropertyParameter.box() }
        test("typeOf.nonReifiedTypeParameters.starProjectionInUpperBound") { tests.typeOf.nonReifiedTypeParameters.starProjectionInUpperBound.box() }
        test("typeOf.nonReifiedTypeParameters.typeParameterFlags") { tests.typeOf.nonReifiedTypeParameters.typeParameterFlags.box() }
        test("typeOf.nonReifiedTypeParameters.upperBoundUsesOuterClassParameter") { tests.typeOf.nonReifiedTypeParameters.upperBoundUsesOuterClassParameter.box() }
        test("typeOf.nonReifiedTypeParameters.upperBounds") { tests.typeOf.nonReifiedTypeParameters.upperBounds.box() }
        test("typeOf.typeOfCapturedStar") { tests.typeOf.typeOfCapturedStar.box() }
        test("typeParameters.declarationSiteVariance") { tests.typeParameters.declarationSiteVariance.box() }
        test("typeParameters.innerGenericParameter") { tests.typeParameters.innerGenericParameter.box() }
        test("typeParameters.typeParametersAndNames") { tests.typeParameters.typeParametersAndNames.box() }
        test("typeParameters.upperBounds") { tests.typeParameters.upperBounds.box() }
        test("types.classifierIsClass") { tests.types.classifierIsClass.box() }
        test("types.classifierIsTypeParameter") { tests.types.classifierIsTypeParameter.box() }
        test("types.classifiersOfBuiltInTypes") { tests.types.classifiersOfBuiltInTypes.box() }
        test("types.createType.equality") { tests.types.createType.equality.box() }
        test("types.createType.innerGeneric") { tests.types.createType.innerGeneric.box() }
        test("types.createType.simpleCreateType") { tests.types.createType.simpleCreateType.box() }
        test("types.createType.typeParameter") { tests.types.createType.typeParameter.box() }
        test("types.createType.wrongNumberOfArguments") { tests.types.createType.wrongNumberOfArguments.box() }
        test("types.equalsForClassAndTypeParameterWithSameFqName") { tests.types.equalsForClassAndTypeParameterWithSameFqName.box() }
        test("types.innerGenericArguments") { tests.types.innerGenericArguments.box() }
        test("types.jvmErasureOfClass") { tests.types.jvmErasureOfClass.box() }
        test("types.jvmErasureOfTypeParameter") { tests.types.jvmErasureOfTypeParameter.box() }
        test("types.subtyping.simpleGenericTypes") { tests.types.subtyping.simpleGenericTypes.box() }
        test("types.subtyping.simpleSubtypeSupertype") { tests.types.subtyping.simpleSubtypeSupertype.box() }
        test("types.subtyping.typeProjection") { tests.types.subtyping.typeProjection.box() }
        test("types.typeArguments") { tests.types.typeArguments.box() }
        test("types.useSiteVariance") { tests.types.useSiteVariance.box() }
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
