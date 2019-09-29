import org.junit.Assert
import org.junit.Test

class Annotations {
    @Test
    fun annotationRetentionAnnotation() {
        Assert.assertEquals("OK", tests.annotations.annotationRetentionAnnotation.box())
    }

    @Test
    fun findAnnotation() {
        Assert.assertEquals("OK", tests.annotations.findAnnotation.box())
    }

    @Test
    fun genericExtensionProperty() {
        Assert.assertEquals("OK", tests.annotations.genericExtensionProperty.box())
    }

    @Test
    fun hasAnnotation() {
        Assert.assertEquals("OK", tests.annotations.hasAnnotation.box())
    }

    @Test
    fun localClassLiteral() {
        Assert.assertEquals("OK", tests.annotations.localClassLiteral.box())
    }

    @Test
    fun localClassParameterAnnotation() {
        Assert.assertEquals("OK", tests.annotations.localClassParameterAnnotation.box())
    }

    @Test
    fun openSuspendFun() {
        Assert.assertEquals("OK", tests.annotations.openSuspendFun.box())
    }

    @Test
    fun privateAnnotation() {
        Assert.assertEquals("OK", tests.annotations.privateAnnotation.box())
    }

    @Test
    fun propertyAccessors() {
        Assert.assertEquals("OK", tests.annotations.propertyAccessors.box())
    }

    @Test
    fun propertyWithoutBackingField() {
        Assert.assertEquals("OK", tests.annotations.propertyWithoutBackingField.box())
    }

    @Test
    fun retentions() {
        Assert.assertEquals("OK", tests.annotations.retentions.box())
    }

    @Test
    fun setparam() {
        Assert.assertEquals("OK", tests.annotations.setparam.box())
    }

    @Test
    fun simpleClassAnnotation() {
        Assert.assertEquals("OK", tests.annotations.simpleClassAnnotation.box())
    }

    @Test
    fun simpleConstructorAnnotation() {
        Assert.assertEquals("OK", tests.annotations.simpleConstructorAnnotation.box())
    }

    @Test
    fun simpleFunAnnotation() {
        Assert.assertEquals("OK", tests.annotations.simpleFunAnnotation.box())
    }

    @Test
    fun simpleParamAnnotation() {
        Assert.assertEquals("OK", tests.annotations.simpleParamAnnotation.box())
    }

    @Test
    fun simpleValAnnotation() {
        Assert.assertEquals("OK", tests.annotations.simpleValAnnotation.box())
    }

    class OnTypes {
        @Test
        fun arrayKClass() {
            Assert.assertEquals("OK", tests.annotations.onTypes.arrayKClass.box())
        }

        @Test
        fun classLiteralWithExpectedType() {
            Assert.assertEquals("OK", tests.annotations.onTypes.classLiteralWithExpectedType.box())
        }

        @Test
        fun differentArgumentTypes() {
            Assert.assertEquals("OK", tests.annotations.onTypes.differentArgumentTypes.box())
        }

        @Test
        fun differentPositions() {
            Assert.assertEquals("OK", tests.annotations.onTypes.differentPositions.box())
        }
    }
}

class Builtins {
    @Test
    fun collections() {
        Assert.assertEquals("OK", tests.builtins.collections.box())
    }

    @Test
    fun enumNameOrdinal() {
        Assert.assertEquals("OK", tests.builtins.enumNameOrdinal.box())
    }

    @Test
    fun stringLength() {
        Assert.assertEquals("OK", tests.builtins.stringLength.box())
    }
}

class Call {
    @Test
    fun bigArity() {
        Assert.assertEquals("OK", tests.call.bigArity.box())
    }

    @Test
    fun cannotCallEnumConstructor() {
        Assert.assertEquals("OK", tests.call.cannotCallEnumConstructor.box())
    }

    @Test
    fun disallowNullValueForNotNullField() {
        Assert.assertEquals("OK", tests.call.disallowNullValueForNotNullField.box())
    }

    @Test
    fun equalsHashCodeToString() {
        Assert.assertEquals("OK", tests.call.equalsHashCodeToString.box())
    }

    @Test
    fun exceptionHappened() {
        Assert.assertEquals("OK", tests.call.exceptionHappened.box())
    }

    @Test
    fun fakeOverride() {
        Assert.assertEquals("OK", tests.call.fakeOverride.box())
    }

    @Test
    fun fakeOverrideSubstituted() {
        Assert.assertEquals("OK", tests.call.fakeOverrideSubstituted.box())
    }

    @Test
    fun incorrectNumberOfArguments() {
        Assert.assertEquals("OK", tests.call.incorrectNumberOfArguments.box())
    }

    @Test
    fun innerClassConstructor() {
        Assert.assertEquals("OK", tests.call.innerClassConstructor.box())
    }

    @Test
    fun jvmStatic() {
        Assert.assertEquals("OK", tests.call.jvmStatic.box())
    }

    @Test
    fun jvmStaticInObjectIncorrectReceiver() {
        Assert.assertEquals("OK", tests.call.jvmStaticInObjectIncorrectReceiver.box())
    }

    @Test
    fun localClassMember() {
        Assert.assertEquals("OK", tests.call.localClassMember.box())
    }

    @Test
    fun memberOfGenericClass() {
        Assert.assertEquals("OK", tests.call.memberOfGenericClass.box())
    }

    @Test
    fun privateProperty() {
        Assert.assertEquals("OK", tests.call.privateProperty.box())
    }

    @Test
    fun propertyAccessors() {
        Assert.assertEquals("OK", tests.call.propertyAccessors.box())
    }

    @Test
    fun propertyGetterAndGetFunctionDifferentReturnType() {
        Assert.assertEquals("OK", tests.call.propertyGetterAndGetFunctionDifferentReturnType.box())
    }

    @Test
    fun protectedMembers() {
        Assert.assertEquals("OK", tests.call.protectedMembers.box())
    }

    @Test
    fun returnUnit() {
        Assert.assertEquals("OK", tests.call.returnUnit.box())
    }

    @Test
    fun simpleConstructor() {
        Assert.assertEquals("OK", tests.call.simpleConstructor.box())
    }

    @Test
    fun simpleMemberFunction() {
        Assert.assertEquals("OK", tests.call.simpleMemberFunction.box())
    }

    @Test
    fun simpleTopLevelFunctions() {
        Assert.assertEquals("OK", tests.call.simpleTopLevelFunctions.box())
    }

    class Bound {
        @Test
        fun companionObjectPropertyAccessors() {
            Assert.assertEquals("OK", tests.call.bound.companionObjectPropertyAccessors.box())
        }

        @Test
        fun extensionFunction() {
            Assert.assertEquals("OK", tests.call.bound.extensionFunction.box())
        }

        @Test
        fun extensionPropertyAccessors() {
            Assert.assertEquals("OK", tests.call.bound.extensionPropertyAccessors.box())
        }

        @Test
        fun innerClassConstructor() {
            Assert.assertEquals("OK", tests.call.bound.innerClassConstructor.box())
        }

        @Test
        fun jvmStaticCompanionObjectPropertyAccessors() {
            Assert.assertEquals("OK", tests.call.bound.jvmStaticCompanionObjectPropertyAccessors.box())
        }

        @Test
        fun jvmStaticObjectFunction() {
            Assert.assertEquals("OK", tests.call.bound.jvmStaticObjectFunction.box())
        }

        @Test
        fun jvmStaticObjectPropertyAccessors() {
            Assert.assertEquals("OK", tests.call.bound.jvmStaticObjectPropertyAccessors.box())
        }

        @Test
        fun memberFunction() {
            Assert.assertEquals("OK", tests.call.bound.memberFunction.box())
        }

        @Test
        fun memberPropertyAccessors() {
            Assert.assertEquals("OK", tests.call.bound.memberPropertyAccessors.box())
        }

        @Test
        fun objectFunction() {
            Assert.assertEquals("OK", tests.call.bound.objectFunction.box())
        }

        @Test
        fun objectPropertyAccessors() {
            Assert.assertEquals("OK", tests.call.bound.objectPropertyAccessors.box())
        }
    }

    class InlineClasses {
        @Test
        fun constructorWithInlineClassParameters() {
            Assert.assertEquals("OK", tests.call.inlineClasses.constructorWithInlineClassParameters.box())
        }

        @Test
        fun fieldAccessors() {
            Assert.assertEquals("OK", tests.call.inlineClasses.fieldAccessors.box())
        }

        @Test
        fun functionsWithInlineClassParameters() {
            Assert.assertEquals("OK", tests.call.inlineClasses.functionsWithInlineClassParameters.box())
        }

        @Test
        fun inlineClassConstructor() {
            Assert.assertEquals("OK", tests.call.inlineClasses.inlineClassConstructor.box())
        }

        @Test
        fun internalPrimaryValOfInlineClass() {
            Assert.assertEquals("OK", tests.call.inlineClasses.internalPrimaryValOfInlineClass.box())
        }

        @Test
        fun jvmStaticFieldInObject() {
            Assert.assertEquals("OK", tests.call.inlineClasses.jvmStaticFieldInObject.box())
        }

        @Test
        fun jvmStaticFunction() {
            Assert.assertEquals("OK", tests.call.inlineClasses.jvmStaticFunction.box())
        }

        @Test
        fun nonOverridingFunOfInlineClass() {
            Assert.assertEquals("OK", tests.call.inlineClasses.nonOverridingFunOfInlineClass.box())
        }

        @Test
        fun nonOverridingVarOfInlineClass() {
            Assert.assertEquals("OK", tests.call.inlineClasses.nonOverridingVarOfInlineClass.box())
        }

        @Test
        fun overridingFunOfInlineClass() {
            Assert.assertEquals("OK", tests.call.inlineClasses.overridingFunOfInlineClass.box())
        }

        @Test
        fun overridingVarOfInlineClass() {
            Assert.assertEquals("OK", tests.call.inlineClasses.overridingVarOfInlineClass.box())
        }

        @Test
        fun primaryValOfInlineClass() {
            Assert.assertEquals("OK", tests.call.inlineClasses.primaryValOfInlineClass.box())
        }

        @Test
        fun properties() {
            Assert.assertEquals("OK", tests.call.inlineClasses.properties.box())
        }
    }
}

class CallBy {
    @Test
    fun boundExtensionFunction() {
        Assert.assertEquals("OK", tests.callBy.boundExtensionFunction.box())
    }

    @Test
    fun boundExtensionPropertyAcessor() {
        Assert.assertEquals("OK", tests.callBy.boundExtensionPropertyAcessor.box())
    }

    @Test
    fun boundJvmStaticInObject() {
        Assert.assertEquals("OK", tests.callBy.boundJvmStaticInObject.box())
    }

    @Test
    fun companionObject() {
        Assert.assertEquals("OK", tests.callBy.companionObject.box())
    }

    @Test
    fun defaultAndNonDefaultIntertwined() {
        Assert.assertEquals("OK", tests.callBy.defaultAndNonDefaultIntertwined.box())
    }

    @Test
    fun defaultInSuperClass() {
        Assert.assertEquals("OK", tests.callBy.defaultInSuperClass.box())
    }

    @Test
    fun defaultInSuperInterface() {
        Assert.assertEquals("OK", tests.callBy.defaultInSuperInterface.box())
    }

    @Test
    fun emptyVarArg() {
        Assert.assertEquals("OK", tests.callBy.emptyVarArg.box())
    }

    @Test
    fun extensionFunction() {
        Assert.assertEquals("OK", tests.callBy.extensionFunction.box())
    }

    @Test
    fun inlineClassDefaultArguments() {
        Assert.assertEquals("OK", tests.callBy.inlineClassDefaultArguments.box())
    }

    @Test
    fun inlineClassFunctionsAndConstructors() {
        Assert.assertEquals("OK", tests.callBy.inlineClassFunctionsAndConstructors.box())
    }

    @Test
    fun inlineClassMembers() {
        Assert.assertEquals("OK", tests.callBy.inlineClassMembers.box())
    }

    @Test
    fun jvmStaticInCompanionObject() {
        Assert.assertEquals("OK", tests.callBy.jvmStaticInCompanionObject.box())
    }

    @Test
    fun jvmStaticInObject() {
        Assert.assertEquals("OK", tests.callBy.jvmStaticInObject.box())
    }

    @Test
    fun manyArgumentsNoneDefaultConstructor() {
        Assert.assertEquals("OK", tests.callBy.manyArgumentsNoneDefaultConstructor.box())
    }

    @Test
    fun manyArgumentsNoneDefaultFunction() {
        Assert.assertEquals("OK", tests.callBy.manyArgumentsNoneDefaultFunction.box())
    }

    @Test
    fun manyArgumentsOnlyOneDefault() {
        Assert.assertEquals("OK", tests.callBy.manyArgumentsOnlyOneDefault.box())
    }

    @Test
    fun manyMaskArguments() {
        Assert.assertEquals("OK", tests.callBy.manyMaskArguments.box())
    }

    @Test
    fun nonDefaultParameterOmitted() {
        Assert.assertEquals("OK", tests.callBy.nonDefaultParameterOmitted.box())
    }

    @Test
    fun nullValue() {
        Assert.assertEquals("OK", tests.callBy.nullValue.box())
    }

    @Test
    fun ordinaryMethodIsInvokedWhenNoDefaultValuesAreUsed() {
        Assert.assertEquals("OK", tests.callBy.ordinaryMethodIsInvokedWhenNoDefaultValuesAreUsed.box())
    }

    @Test
    fun primitiveDefaultValues() {
        Assert.assertEquals("OK", tests.callBy.primitiveDefaultValues.box())
    }

    @Test
    fun privateMemberFunction() {
        Assert.assertEquals("OK", tests.callBy.privateMemberFunction.box())
    }

    @Test
    fun simpleConstructor() {
        Assert.assertEquals("OK", tests.callBy.simpleConstructor.box())
    }

    @Test
    fun simpleMemberFunciton() {
        Assert.assertEquals("OK", tests.callBy.simpleMemberFunciton.box())
    }

    @Test
    fun simpleTopLevelFunction() {
        Assert.assertEquals("OK", tests.callBy.simpleTopLevelFunction.box())
    }
}

class ClassLiterals {
    @Test
    fun annotationClassLiteral() {
        Assert.assertEquals("OK", tests.classLiterals.annotationClassLiteral.box())
    }

    @Test
    fun arrays() {
        Assert.assertEquals("OK", tests.classLiterals.arrays.box())
    }

    @Test
    fun bareArray() {
        Assert.assertEquals("OK", tests.classLiterals.bareArray.box())
    }

    @Test
    fun builtinClassLiterals() {
        Assert.assertEquals("OK", tests.classLiterals.builtinClassLiterals.box())
    }

    @Test
    fun genericArrays() {
        Assert.assertEquals("OK", tests.classLiterals.genericArrays.box())
    }

    @Test
    fun genericClass() {
        Assert.assertEquals("OK", tests.classLiterals.genericClass.box())
    }

    @Test
    fun reifiedTypeClassLiteral() {
        Assert.assertEquals("OK", tests.classLiterals.reifiedTypeClassLiteral.box())
    }
}

class Classes {
    @Test
    fun classSimpleName() {
        Assert.assertEquals("OK", tests.classes.classSimpleName.box())
    }

    @Test
    fun companionObject() {
        Assert.assertEquals("OK", tests.classes.companionObject.box())
    }

    @Test
    fun createInstance() {
        Assert.assertEquals("OK", tests.classes.createInstance.box())
    }

    @Test
    fun javaVoid() {
        Assert.assertEquals("OK", tests.classes.javaVoid.box())
    }

    @Test
    fun jvmName() {
        Assert.assertEquals("OK", tests.classes.jvmName.box())
    }

    @Test
    fun jvmNameOfStandardClasses() {
        Assert.assertEquals("OK", tests.classes.jvmNameOfStandardClasses.box())
    }

    @Test
    fun localClassSimpleName() {
        Assert.assertEquals("OK", tests.classes.localClassSimpleName.box())
    }

    @Test
    fun nestedClasses() {
        Assert.assertEquals("OK", tests.classes.nestedClasses.box())
    }

    @Test
    fun objectInstance() {
        Assert.assertEquals("OK", tests.classes.objectInstance.box())
    }

    @Test
    fun primitiveKClassEquality() {
        Assert.assertEquals("OK", tests.classes.primitiveKClassEquality.box())
    }

    @Test
    fun qualifiedName() {
        Assert.assertEquals("OK", tests.classes.qualifiedName.box())
    }

    @Test
    fun qualifiedNameOfStandardClasses() {
        Assert.assertEquals("OK", tests.classes.qualifiedNameOfStandardClasses.box())
    }

    @Test
    fun sealedSubclasses() {
        Assert.assertEquals("OK", tests.classes.sealedSubclasses.box())
    }

    @Test
    fun starProjectedType() {
        Assert.assertEquals("OK", tests.classes.starProjectedType.box())
    }
}

class Constructors {
    @Test
    fun annotationClass() {
        Assert.assertEquals("OK", tests.constructors.annotationClass.box())
    }

    @Test
    fun classesWithoutConstructors() {
        Assert.assertEquals("OK", tests.constructors.classesWithoutConstructors.box())
    }

    @Test
    fun constructorName() {
        Assert.assertEquals("OK", tests.constructors.constructorName.box())
    }

    @Test
    fun enumEntry() {
        Assert.assertEquals("OK", tests.constructors.enumEntry.box())
    }

    @Test
    fun primaryConstructor() {
        Assert.assertEquals("OK", tests.constructors.primaryConstructor.box())
    }

    @Test
    fun simpleGetConstructors() {
        Assert.assertEquals("OK", tests.constructors.simpleGetConstructors.box())
    }
}

class CreateAnnotation {
    @Test
    fun annotationType() {
        Assert.assertEquals("OK", tests.createAnnotation.annotationType.box())
    }

    @Test
    fun arrayOfKClasses() {
        Assert.assertEquals("OK", tests.createAnnotation.arrayOfKClasses.box())
    }

    @Test
    fun callByKotlin() {
        Assert.assertEquals("OK", tests.createAnnotation.callByKotlin.box())
    }

    @Test
    fun callByWithEmptyVarArg() {
        Assert.assertEquals("OK", tests.createAnnotation.callByWithEmptyVarArg.box())
    }

    @Test
    fun callKotlin() {
        Assert.assertEquals("OK", tests.createAnnotation.callKotlin.box())
    }

    @Test
    fun createJdkAnnotationInstance() {
        Assert.assertEquals("OK", tests.createAnnotation.createJdkAnnotationInstance.box())
    }

    @Test
    fun enumKClassAnnotation() {
        Assert.assertEquals("OK", tests.createAnnotation.enumKClassAnnotation.box())
    }

    @Test
    fun equalsHashCodeToString() {
        Assert.assertEquals("OK", tests.createAnnotation.equalsHashCodeToString.box())
    }

    @Test
    fun floatingPointParameters() {
        Assert.assertEquals("OK", tests.createAnnotation.floatingPointParameters.box())
    }

    @Test
    fun parameterNamedEquals() {
        Assert.assertEquals("OK", tests.createAnnotation.parameterNamedEquals.box())
    }

    @Test
    fun primitivesAndArrays() {
        Assert.assertEquals("OK", tests.createAnnotation.primitivesAndArrays.box())
    }
}

class Functions {
    @Test
    fun enumValuesValueOf() {
        Assert.assertEquals("OK", tests.functions.enumValuesValueOf.box())
    }

    @Test
    fun functionFromStdlib() {
        Assert.assertEquals("OK", tests.functions.functionFromStdlib.box())
    }

    @Test
    fun genericOverriddenFunction() {
        Assert.assertEquals("OK", tests.functions.genericOverriddenFunction.box())
    }

    @Test
    fun isAccessibleOnAllMembers() {
        Assert.assertEquals("OK", tests.functions.isAccessibleOnAllMembers.box())
    }

    @Test
    fun platformName() {
        Assert.assertEquals("OK", tests.functions.platformName.box())
    }

    @Test
    fun privateMemberFunction() {
        Assert.assertEquals("OK", tests.functions.privateMemberFunction.box())
    }

    @Test
    fun simpleGetFunctions() {
        Assert.assertEquals("OK", tests.functions.simpleGetFunctions.box())
    }

    @Test
    fun simpleNames() {
        Assert.assertEquals("OK", tests.functions.simpleNames.box())
    }
}

class IsInstance {
    @Test
    fun isInstanceCastAndSafeCast() {
        Assert.assertEquals("OK", tests.isInstance.isInstanceCastAndSafeCast.box())
    }
}

class KClassInAnnotation {
    @Test
    fun array() {
        Assert.assertEquals("OK", tests.kClassInAnnotation.array.box())
    }

    @Test
    fun basic() {
        Assert.assertEquals("OK", tests.kClassInAnnotation.basic.box())
    }

    @Test
    fun checkcast() {
        Assert.assertEquals("OK", tests.kClassInAnnotation.checkcast.box())
    }

    @Test
    fun forceWrapping() {
        Assert.assertEquals("OK", tests.kClassInAnnotation.forceWrapping.box())
    }

    @Test
    fun vararg() {
        Assert.assertEquals("OK", tests.kClassInAnnotation.vararg.box())
    }

    @Test
    fun wrappingForCallableReferences() {
        Assert.assertEquals("OK", tests.kClassInAnnotation.wrappingForCallableReferences.box())
    }
}

class LambdaClasses {
    @Test
    fun parameterNamesAndNullability() {
        Assert.assertEquals("OK", tests.lambdaClasses.parameterNamesAndNullability.box())
    }

    @Test
    fun reflectOnDefaultWithInlineClassArgument() {
        Assert.assertEquals("OK", tests.lambdaClasses.reflectOnDefaultWithInlineClassArgument.box())
    }

    @Test
    fun reflectOnLambdaInArrayConstructor() {
        Assert.assertEquals("OK", tests.lambdaClasses.reflectOnLambdaInArrayConstructor.box())
    }

    @Test
    fun reflectOnLambdaInConstructor() {
        Assert.assertEquals("OK", tests.lambdaClasses.reflectOnLambdaInConstructor.box())
    }

    @Test
    fun reflectOnLambdaInField() {
        Assert.assertEquals("OK", tests.lambdaClasses.reflectOnLambdaInField.box())
    }

    @Test
    fun reflectOnLambdaInStaticField() {
        Assert.assertEquals("OK", tests.lambdaClasses.reflectOnLambdaInStaticField.box())
    }

    @Test
    fun reflectOnLambdaInSuspend() {
        Assert.assertEquals("OK", tests.lambdaClasses.reflectOnLambdaInSuspend.box())
    }

    @Test
    fun reflectOnLambdaInSuspendLambda() {
        Assert.assertEquals("OK", tests.lambdaClasses.reflectOnLambdaInSuspendLambda.box())
    }

    @Test
    fun reflectOnSuspendLambdaInField() {
        Assert.assertEquals("OK", tests.lambdaClasses.reflectOnSuspendLambdaInField.box())
    }
}

class Mapping {
    @Test
    fun constructor() {
        Assert.assertEquals("OK", tests.mapping.constructor.box())
    }

    @Test
    fun constructorWithInlineClassParameters() {
        Assert.assertEquals("OK", tests.mapping.constructorWithInlineClassParameters.box())
    }

    @Test
    fun extensionProperty() {
        Assert.assertEquals("OK", tests.mapping.extensionProperty.box())
    }

    @Test
    fun functions() {
        Assert.assertEquals("OK", tests.mapping.functions.box())
    }

    @Test
    fun inlineClassPrimaryVal() {
        Assert.assertEquals("OK", tests.mapping.inlineClassPrimaryVal.box())
    }

    @Test
    fun inlineReifiedFun() {
        Assert.assertEquals("OK", tests.mapping.inlineReifiedFun.box())
    }

    @Test
    fun interfaceCompanionPropertyWithJvmField() {
        Assert.assertEquals("OK", tests.mapping.interfaceCompanionPropertyWithJvmField.box())
    }

    @Test
    fun lateinitProperty() {
        Assert.assertEquals("OK", tests.mapping.lateinitProperty.box())
    }

    @Test
    fun mappedClassIsEqualToClassLiteral() {
        Assert.assertEquals("OK", tests.mapping.mappedClassIsEqualToClassLiteral.box())
    }

    @Test
    fun memberProperty() {
        Assert.assertEquals("OK", tests.mapping.memberProperty.box())
    }

    @Test
    fun methodsFromObject() {
        Assert.assertEquals("OK", tests.mapping.methodsFromObject.box())
    }

    @Test
    fun methodsFromSuperInterface() {
        Assert.assertEquals("OK", tests.mapping.methodsFromSuperInterface.box())
    }

    @Test
    fun openSuspendFun() {
        Assert.assertEquals("OK", tests.mapping.openSuspendFun.box())
    }

    @Test
    fun privateProperty() {
        Assert.assertEquals("OK", tests.mapping.privateProperty.box())
    }

    @Test
    fun propertyAccessorsWithJvmName() {
        Assert.assertEquals("OK", tests.mapping.propertyAccessorsWithJvmName.box())
    }

    @Test
    fun syntheticFields() {
        Assert.assertEquals("OK", tests.mapping.syntheticFields.box())
    }

    @Test
    fun topLevelProperty() {
        Assert.assertEquals("OK", tests.mapping.topLevelProperty.box())
    }

    class FakeOverrides {
        @Test
        fun javaFieldGetterSetter() {
            Assert.assertEquals("OK", tests.mapping.fakeOverrides.javaFieldGetterSetter.box())
        }

        @Test
        fun javaMethod() {
            Assert.assertEquals("OK", tests.mapping.fakeOverrides.javaMethod.box())
        }
    }

    class JvmStatic {
        @Test
        fun companionObjectFunction() {
            Assert.assertEquals("OK", tests.mapping.jvmStatic.companionObjectFunction.box())
        }

        @Test
        fun objectFunction() {
            Assert.assertEquals("OK", tests.mapping.jvmStatic.objectFunction.box())
        }
    }

    class Types {
        @Test
        fun allSupertypes() {
            Assert.assertEquals("OK", tests.mapping.types.allSupertypes.box())
        }

        @Test
        fun annotationConstructorParameters() {
            Assert.assertEquals("OK", tests.mapping.types.annotationConstructorParameters.box())
        }

        @Test
        fun array() {
            Assert.assertEquals("OK", tests.mapping.types.array.box())
        }

        @Test
        fun constructors() {
            Assert.assertEquals("OK", tests.mapping.types.constructors.box())
        }

        @Test
        fun createType() {
            Assert.assertEquals("OK", tests.mapping.types.createType.box())
        }

        @Test
        fun genericArrayElementType() {
            Assert.assertEquals("OK", tests.mapping.types.genericArrayElementType.box())
        }

        @Test
        fun inlineClassInSignature() {
            Assert.assertEquals("OK", tests.mapping.types.inlineClassInSignature.box())
        }

        @Test
        fun inlineClassPrimaryVal() {
            Assert.assertEquals("OK", tests.mapping.types.inlineClassPrimaryVal.box())
        }

        @Test
        fun innerGenericTypeArgument() {
            Assert.assertEquals("OK", tests.mapping.types.innerGenericTypeArgument.box())
        }

        @Test
        fun memberFunctions() {
            Assert.assertEquals("OK", tests.mapping.types.memberFunctions.box())
        }

        @Test
        fun overrideAnyWithPrimitive() {
            Assert.assertEquals("OK", tests.mapping.types.overrideAnyWithPrimitive.box())
        }

        @Test
        fun parameterizedTypeArgument() {
            Assert.assertEquals("OK", tests.mapping.types.parameterizedTypeArgument.box())
        }

        @Test
        fun parameterizedTypes() {
            Assert.assertEquals("OK", tests.mapping.types.parameterizedTypes.box())
        }

        @Test
        fun propertyAccessors() {
            Assert.assertEquals("OK", tests.mapping.types.propertyAccessors.box())
        }

        @Test
        fun supertypes() {
            Assert.assertEquals("OK", tests.mapping.types.supertypes.box())
        }

        @Test
        fun suspendFun() {
            Assert.assertEquals("OK", tests.mapping.types.suspendFun.box())
        }

        @Test
        fun topLevelFunctions() {
            Assert.assertEquals("OK", tests.mapping.types.topLevelFunctions.box())
        }

        @Test
        fun typeParameters() {
            Assert.assertEquals("OK", tests.mapping.types.typeParameters.box())
        }

        @Test
        fun unit() {
            Assert.assertEquals("OK", tests.mapping.types.unit.box())
        }

        @Test
        fun withNullability() {
            Assert.assertEquals("OK", tests.mapping.types.withNullability.box())
        }
    }
}

class MethodsFromAny {
    @Test
    fun adaptedCallableReferencesNotEqualToCallablesFromAPI() {
        Assert.assertEquals("OK", tests.methodsFromAny.adaptedCallableReferencesNotEqualToCallablesFromAPI.box())
    }

    @Test
    fun builtinFunctionsToString() {
        Assert.assertEquals("OK", tests.methodsFromAny.builtinFunctionsToString.box())
    }

    @Test
    fun callableReferencesEqualToCallablesFromAPI() {
        Assert.assertEquals("OK", tests.methodsFromAny.callableReferencesEqualToCallablesFromAPI.box())
    }

    @Test
    fun classToString() {
        Assert.assertEquals("OK", tests.methodsFromAny.classToString.box())
    }

    @Test
    fun extensionPropertyReceiverToString() {
        Assert.assertEquals("OK", tests.methodsFromAny.extensionPropertyReceiverToString.box())
    }

    @Test
    fun fakeOverrideEqualsHashCode() {
        Assert.assertEquals("OK", tests.methodsFromAny.fakeOverrideEqualsHashCode.box())
    }

    @Test
    fun fakeOverrideToString() {
        Assert.assertEquals("OK", tests.methodsFromAny.fakeOverrideToString.box())
    }

    @Test
    fun fakeOverrideToString2() {
        Assert.assertEquals("OK", tests.methodsFromAny.fakeOverrideToString2.box())
    }

    @Test
    fun functionEqualsHashCode() {
        Assert.assertEquals("OK", tests.methodsFromAny.functionEqualsHashCode.box())
    }

    @Test
    fun functionFromStdlibMultiFileFacade() {
        Assert.assertEquals("OK", tests.methodsFromAny.functionFromStdlibMultiFileFacade.box())
    }

    @Test
    fun functionFromStdlibSingleFileFacade() {
        Assert.assertEquals("OK", tests.methodsFromAny.functionFromStdlibSingleFileFacade.box())
    }

    @Test
    fun functionToString() {
        Assert.assertEquals("OK", tests.methodsFromAny.functionToString.box())
    }

    @Test
    fun memberExtensionToString() {
        Assert.assertEquals("OK", tests.methodsFromAny.memberExtensionToString.box())
    }

    @Test
    fun parametersEqualsHashCode() {
        Assert.assertEquals("OK", tests.methodsFromAny.parametersEqualsHashCode.box())
    }

    @Test
    fun parametersEqualsWithClearCaches() {
        Assert.assertEquals("OK", tests.methodsFromAny.parametersEqualsWithClearCaches.box())
    }

    @Test
    fun parametersToString() {
        Assert.assertEquals("OK", tests.methodsFromAny.parametersToString.box())
    }

    @Test
    fun propertyEqualsHashCode() {
        Assert.assertEquals("OK", tests.methodsFromAny.propertyEqualsHashCode.box())
    }

    @Test
    fun propertyToString() {
        Assert.assertEquals("OK", tests.methodsFromAny.propertyToString.box())
    }

    @Test
    fun typeEqualsHashCode() {
        Assert.assertEquals("OK", tests.methodsFromAny.typeEqualsHashCode.box())
    }

    @Test
    fun typeParametersEqualsHashCode() {
        Assert.assertEquals("OK", tests.methodsFromAny.typeParametersEqualsHashCode.box())
    }

    @Test
    fun typeParametersToString() {
        Assert.assertEquals("OK", tests.methodsFromAny.typeParametersToString.box())
    }

    @Test
    fun typeToString() {
        Assert.assertEquals("OK", tests.methodsFromAny.typeToString.box())
    }

    @Test
    fun typeToStringInnerGeneric() {
        Assert.assertEquals("OK", tests.methodsFromAny.typeToStringInnerGeneric.box())
    }
}

class Modifiers {
    @Test
    fun callableModality() {
        Assert.assertEquals("OK", tests.modifiers.callableModality.box())
    }

    @Test
    fun callableVisibility() {
        Assert.assertEquals("OK", tests.modifiers.callableVisibility.box())
    }

    @Test
    fun classModality() {
        Assert.assertEquals("OK", tests.modifiers.classModality.box())
    }

    @Test
    fun classVisibility() {
        Assert.assertEquals("OK", tests.modifiers.classVisibility.box())
    }

    @Test
    fun functions() {
        Assert.assertEquals("OK", tests.modifiers.functions.box())
    }

    @Test
    fun properties() {
        Assert.assertEquals("OK", tests.modifiers.properties.box())
    }

    @Test
    fun typeParameters() {
        Assert.assertEquals("OK", tests.modifiers.typeParameters.box())
    }
}

class Parameters {
    @Test
    fun bigArity() {
        Assert.assertEquals("OK", tests.parameters.bigArity.box())
    }

    @Test
    fun boundInnerClassConstructor() {
        Assert.assertEquals("OK", tests.parameters.boundInnerClassConstructor.box())
    }

    @Test
    fun boundObjectMemberReferences() {
        Assert.assertEquals("OK", tests.parameters.boundObjectMemberReferences.box())
    }

    @Test
    fun boundReferences() {
        Assert.assertEquals("OK", tests.parameters.boundReferences.box())
    }

    @Test
    fun functionParameterNameAndIndex() {
        Assert.assertEquals("OK", tests.parameters.functionParameterNameAndIndex.box())
    }

    @Test
    fun instanceExtensionReceiverAndValueParameters() {
        Assert.assertEquals("OK", tests.parameters.instanceExtensionReceiverAndValueParameters.box())
    }

    @Test
    fun instanceParameterOfFakeOverride() {
        Assert.assertEquals("OK", tests.parameters.instanceParameterOfFakeOverride.box())
    }

    @Test
    fun isMarkedNullable() {
        Assert.assertEquals("OK", tests.parameters.isMarkedNullable.box())
    }

    @Test
    fun isOptional() {
        Assert.assertEquals("OK", tests.parameters.isOptional.box())
    }

    @Test
    fun kinds() {
        Assert.assertEquals("OK", tests.parameters.kinds.box())
    }

    @Test
    fun propertySetter() {
        Assert.assertEquals("OK", tests.parameters.propertySetter.box())
    }
}

class Properties {
    @Test
    fun allVsDeclared() {
        Assert.assertEquals("OK", tests.properties.allVsDeclared.box())
    }

    @Test
    fun callPrivatePropertyFromGetProperties() {
        Assert.assertEquals("OK", tests.properties.callPrivatePropertyFromGetProperties.box())
    }

    @Test
    fun fakeOverridesInSubclass() {
        Assert.assertEquals("OK", tests.properties.fakeOverridesInSubclass.box())
    }

    @Test
    fun genericClassLiteralPropertyReceiverIsStar() {
        Assert.assertEquals("OK", tests.properties.genericClassLiteralPropertyReceiverIsStar.box())
    }

    @Test
    fun genericOverriddenProperty() {
        Assert.assertEquals("OK", tests.properties.genericOverriddenProperty.box())
    }

    @Test
    fun genericProperty() {
        Assert.assertEquals("OK", tests.properties.genericProperty.box())
    }

    @Test
    fun getExtensionPropertiesMutableVsReadonly() {
        Assert.assertEquals("OK", tests.properties.getExtensionPropertiesMutableVsReadonly.box())
    }

    @Test
    fun getPropertiesMutableVsReadonly() {
        Assert.assertEquals("OK", tests.properties.getPropertiesMutableVsReadonly.box())
    }

    @Test
    fun invokeKProperty() {
        Assert.assertEquals("OK", tests.properties.invokeKProperty.box())
    }

    @Test
    fun memberAndMemberExtensionWithSameName() {
        Assert.assertEquals("OK", tests.properties.memberAndMemberExtensionWithSameName.box())
    }

    @Test
    fun privateClassVal() {
        Assert.assertEquals("OK", tests.properties.privateClassVal.box())
    }

    @Test
    fun privateClassVar() {
        Assert.assertEquals("OK", tests.properties.privateClassVar.box())
    }

    @Test
    fun privateFakeOverrideFromSuperclass() {
        Assert.assertEquals("OK", tests.properties.privateFakeOverrideFromSuperclass.box())
    }

    @Test
    fun privateJvmStaticVarInObject() {
        Assert.assertEquals("OK", tests.properties.privateJvmStaticVarInObject.box())
    }

    @Test
    fun privatePropertyCallIsAccessibleOnAccessors() {
        Assert.assertEquals("OK", tests.properties.privatePropertyCallIsAccessibleOnAccessors.box())
    }

    @Test
    fun privateToThisAccessors() {
        Assert.assertEquals("OK", tests.properties.privateToThisAccessors.box())
    }

    @Test
    fun propertyOfNestedClassAndArrayType() {
        Assert.assertEquals("OK", tests.properties.propertyOfNestedClassAndArrayType.box())
    }

    @Test
    fun protectedClassVar() {
        Assert.assertEquals("OK", tests.properties.protectedClassVar.box())
    }

    @Test
    fun publicClassValAccessible() {
        Assert.assertEquals("OK", tests.properties.publicClassValAccessible.box())
    }

    @Test
    fun simpleGetProperties() {
        Assert.assertEquals("OK", tests.properties.simpleGetProperties.box())
    }

    class Accessors {
        @Test
        fun accessorNames() {
            Assert.assertEquals("OK", tests.properties.accessors.accessorNames.box())
        }

        @Test
        fun extensionPropertyAccessors() {
            Assert.assertEquals("OK", tests.properties.accessors.extensionPropertyAccessors.box())
        }

        @Test
        fun memberExtensions() {
            Assert.assertEquals("OK", tests.properties.accessors.memberExtensions.box())
        }

        @Test
        fun memberPropertyAccessors() {
            Assert.assertEquals("OK", tests.properties.accessors.memberPropertyAccessors.box())
        }

        @Test
        fun topLevelPropertyAccessors() {
            Assert.assertEquals("OK", tests.properties.accessors.topLevelPropertyAccessors.box())
        }
    }

    class GetDelegate {
        @Test
        fun booleanPropertyNameStartsWithIs() {
            Assert.assertEquals("OK", tests.properties.getDelegate.booleanPropertyNameStartsWithIs.box())
        }

        @Test
        fun boundExtensionProperty() {
            Assert.assertEquals("OK", tests.properties.getDelegate.boundExtensionProperty.box())
        }

        @Test
        fun boundMemberProperty() {
            Assert.assertEquals("OK", tests.properties.getDelegate.boundMemberProperty.box())
        }

        @Test
        fun extensionProperty() {
            Assert.assertEquals("OK", tests.properties.getDelegate.extensionProperty.box())
        }

        @Test
        fun fakeOverride() {
            Assert.assertEquals("OK", tests.properties.getDelegate.fakeOverride.box())
        }

        @Test
        fun getExtensionDelegate() {
            Assert.assertEquals("OK", tests.properties.getDelegate.getExtensionDelegate.box())
        }

        @Test
        fun kPropertyForDelegatedProperty() {
            Assert.assertEquals("OK", tests.properties.getDelegate.kPropertyForDelegatedProperty.box())
        }

        @Test
        fun memberExtensionProperty() {
            Assert.assertEquals("OK", tests.properties.getDelegate.memberExtensionProperty.box())
        }

        @Test
        fun memberProperty() {
            Assert.assertEquals("OK", tests.properties.getDelegate.memberProperty.box())
        }

        @Test
        fun nameClashClassAndCompanion() {
            Assert.assertEquals("OK", tests.properties.getDelegate.nameClashClassAndCompanion.box())
        }

        @Test
        fun noSetAccessibleTrue() {
            Assert.assertEquals("OK", tests.properties.getDelegate.noSetAccessibleTrue.box())
        }

        @Test
        fun notDelegatedProperty() {
            Assert.assertEquals("OK", tests.properties.getDelegate.notDelegatedProperty.box())
        }

        @Test
        fun overrideDelegatedByDelegated() {
            Assert.assertEquals("OK", tests.properties.getDelegate.overrideDelegatedByDelegated.box())
        }

        @Test
        fun topLevelProperty() {
            Assert.assertEquals("OK", tests.properties.getDelegate.topLevelProperty.box())
        }
    }

    class JvmField {
        @Test
        fun annotationCompanionWithAnnotation() {
            Assert.assertEquals("OK", tests.properties.jvmField.annotationCompanionWithAnnotation.box())
        }

        @Test
        fun interfaceCompanion() {
            Assert.assertEquals("OK", tests.properties.jvmField.interfaceCompanion.box())
        }

        @Test
        fun interfaceCompanionWithAnnotation() {
            Assert.assertEquals("OK", tests.properties.jvmField.interfaceCompanionWithAnnotation.box())
        }
    }

    class LocalDelegated {
        @Test
        fun defaultImpls() {
            Assert.assertEquals("OK", tests.properties.localDelegated.defaultImpls.box())
        }

        @Test
        fun inlineFun() {
            Assert.assertEquals("OK", tests.properties.localDelegated.inlineFun.box())
        }

        @Test
        fun localAndNonLocal() {
            Assert.assertEquals("OK", tests.properties.localDelegated.localAndNonLocal.box())
        }

        @Test
        fun localDelegatedProperty() {
            Assert.assertEquals("OK", tests.properties.localDelegated.localDelegatedProperty.box())
        }

        @Test
        fun variableOfGenericType() {
            Assert.assertEquals("OK", tests.properties.localDelegated.variableOfGenericType.box())
        }
    }
}

class Supertypes {
    @Test
    fun builtInClassSupertypes() {
        Assert.assertEquals("OK", tests.supertypes.builtInClassSupertypes.box())
    }

    @Test
    fun genericSubstitution() {
        Assert.assertEquals("OK", tests.supertypes.genericSubstitution.box())
    }

    @Test
    fun isSubclassOfIsSuperclassOf() {
        Assert.assertEquals("OK", tests.supertypes.isSubclassOfIsSuperclassOf.box())
    }

    @Test
    fun primitives() {
        Assert.assertEquals("OK", tests.supertypes.primitives.box())
    }

    @Test
    fun simpleSupertypes() {
        Assert.assertEquals("OK", tests.supertypes.simpleSupertypes.box())
    }
}

class TypeOf {
    @Test
    fun classes() {
        Assert.assertEquals("OK", tests.typeOf.classes.box())
    }

    @Test
    fun inlineClasses() {
        Assert.assertEquals("OK", tests.typeOf.inlineClasses.box())
    }

    @Test
    fun manyTypeArguments() {
        Assert.assertEquals("OK", tests.typeOf.manyTypeArguments.box())
    }

    @Test
    fun multipleLayers() {
        Assert.assertEquals("OK", tests.typeOf.multipleLayers.box())
    }

    @Test
    fun typeOfCapturedStar() {
        Assert.assertEquals("OK", tests.typeOf.typeOfCapturedStar.box())
    }

    class NonReifiedTypeParameters {
        @Test
        fun defaultUpperBound() {
            Assert.assertEquals("OK", tests.typeOf.nonReifiedTypeParameters.defaultUpperBound.box())
        }

        @Test
        fun equalsOnClassParameters() {
            Assert.assertEquals("OK", tests.typeOf.nonReifiedTypeParameters.equalsOnClassParameters.box())
        }

        @Test
        fun equalsOnClassParametersWithReflectAPI() {
            Assert.assertEquals("OK", tests.typeOf.nonReifiedTypeParameters.equalsOnClassParametersWithReflectAPI.box())
        }

        @Test
        fun equalsOnFunctionParameters() {
            Assert.assertEquals("OK", tests.typeOf.nonReifiedTypeParameters.equalsOnFunctionParameters.box())
        }

        @Test
        fun innerGeneric() {
            Assert.assertEquals("OK", tests.typeOf.nonReifiedTypeParameters.innerGeneric.box())
        }

        @Test
        fun simpleClassParameter() {
            Assert.assertEquals("OK", tests.typeOf.nonReifiedTypeParameters.simpleClassParameter.box())
        }

        @Test
        fun simpleFunctionParameter() {
            Assert.assertEquals("OK", tests.typeOf.nonReifiedTypeParameters.simpleFunctionParameter.box())
        }

        @Test
        fun simplePropertyParameter() {
            Assert.assertEquals("OK", tests.typeOf.nonReifiedTypeParameters.simplePropertyParameter.box())
        }

        @Test
        fun starProjectionInUpperBound() {
            Assert.assertEquals("OK", tests.typeOf.nonReifiedTypeParameters.starProjectionInUpperBound.box())
        }

        @Test
        fun typeParameterFlags() {
            Assert.assertEquals("OK", tests.typeOf.nonReifiedTypeParameters.typeParameterFlags.box())
        }

        @Test
        fun upperBoundUsesOuterClassParameter() {
            Assert.assertEquals("OK", tests.typeOf.nonReifiedTypeParameters.upperBoundUsesOuterClassParameter.box())
        }

        @Test
        fun upperBounds() {
            Assert.assertEquals("OK", tests.typeOf.nonReifiedTypeParameters.upperBounds.box())
        }
    }
}

class TypeParameters {
    @Test
    fun declarationSiteVariance() {
        Assert.assertEquals("OK", tests.typeParameters.declarationSiteVariance.box())
    }

    @Test
    fun innerGenericParameter() {
        Assert.assertEquals("OK", tests.typeParameters.innerGenericParameter.box())
    }

    @Test
    fun typeParametersAndNames() {
        Assert.assertEquals("OK", tests.typeParameters.typeParametersAndNames.box())
    }

    @Test
    fun upperBounds() {
        Assert.assertEquals("OK", tests.typeParameters.upperBounds.box())
    }
}

class Types {
    @Test
    fun classifierIsClass() {
        Assert.assertEquals("OK", tests.types.classifierIsClass.box())
    }

    @Test
    fun classifierIsTypeParameter() {
        Assert.assertEquals("OK", tests.types.classifierIsTypeParameter.box())
    }

    @Test
    fun classifiersOfBuiltInTypes() {
        Assert.assertEquals("OK", tests.types.classifiersOfBuiltInTypes.box())
    }

    @Test
    fun innerGenericArguments() {
        Assert.assertEquals("OK", tests.types.innerGenericArguments.box())
    }

    @Test
    fun jvmErasureOfClass() {
        Assert.assertEquals("OK", tests.types.jvmErasureOfClass.box())
    }

    @Test
    fun jvmErasureOfTypeParameter() {
        Assert.assertEquals("OK", tests.types.jvmErasureOfTypeParameter.box())
    }

    @Test
    fun typeArguments() {
        Assert.assertEquals("OK", tests.types.typeArguments.box())
    }

    @Test
    fun useSiteVariance() {
        Assert.assertEquals("OK", tests.types.useSiteVariance.box())
    }

    class CreateType {
        @Test
        fun equality() {
            Assert.assertEquals("OK", tests.types.createType.equality.box())
        }

        @Test
        fun innerGeneric() {
            Assert.assertEquals("OK", tests.types.createType.innerGeneric.box())
        }

        @Test
        fun simpleCreateType() {
            Assert.assertEquals("OK", tests.types.createType.simpleCreateType.box())
        }

        @Test
        fun typeParameter() {
            Assert.assertEquals("OK", tests.types.createType.typeParameter.box())
        }

        @Test
        fun wrongNumberOfArguments() {
            Assert.assertEquals("OK", tests.types.createType.wrongNumberOfArguments.box())
        }
    }

    class Subtyping {
        @Test
        fun simpleGenericTypes() {
            Assert.assertEquals("OK", tests.types.subtyping.simpleGenericTypes.box())
        }

        @Test
        fun simpleSubtypeSupertype() {
            Assert.assertEquals("OK", tests.types.subtyping.simpleSubtypeSupertype.box())
        }

        @Test
        fun typeProjection() {
            Assert.assertEquals("OK", tests.types.subtyping.typeProjection.box())
        }
    }
}

