# kotlin-obfuscation-test-app

This project provides an example application that uses Kotlin reflection, which can be used as a test for bytecode obfuscation tools. Since Kotlin reflection relies heavily on the contents of the `@kotlin.Metadata` annotation generated on .class files produced by the Kotlin compiler, changing the bytecode without adapting the `@kotlin.Metadata` annotation contents is very likely to break Kotlin reflection.

At the moment, this project consists of the majority of tests on reflection [from the main Kotlin repo](https://github.com/JetBrains/kotlin/tree/master/compiler/testData/codegen/box/reflection). Run with:

    ./gradlew clean build
    java -jar build/libs/kotlin-obfuscation-test-app-1.0-SNAPSHOT-all.jar

(substitute `./gradlew` with `gradlew` on Windows)

The application will run each of the tests, printing "OK" if succeeded, or the error message if not. At the end, the total number of passed tests is printed:

    annotations.annotationRetentionAnnotation: OK
    annotations.findAnnotation: OK
    annotations.genericExtensionProperty: OK
    ...
    types.subtyping.typeProjection: OK
    types.typeArguments: OK
    types.useSiteVariance: OK
    328/328 tests passed
