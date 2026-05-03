plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven(url = "https://plugins.gradle.org/m2/")
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.gradle)
    implementation(libs.com.google.devtools.ksp.gradle.plugin)
    implementation(libs.popcorn.guineapig)
    implementation(libs.koin.compiler.plugin)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
}

gradlePlugin {
    plugins {
        create("koinConvention") {
            id = "koin-compiler-setup"
            implementationClass = "plugins.KoinCompilerSetupPlugin"
        }
    }
}
