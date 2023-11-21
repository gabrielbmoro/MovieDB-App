import config.Config
import ext.getLibrariesFromCatalogs
import ext.getLibraryFromCatalogs
import ext.getVersionFromCatalogs
import gradle.kotlin.dsl.accessors._c89c08324d0c311f4189ceef586b2d42.androidTestImplementation
import gradle.kotlin.dsl.accessors._c89c08324d0c311f4189ceef586b2d42.debugImplementation
import gradle.kotlin.dsl.accessors._c89c08324d0c311f4189ceef586b2d42.implementation
import gradle.kotlin.dsl.accessors._c89c08324d0c311f4189ceef586b2d42.kapt
import gradle.kotlin.dsl.accessors._c89c08324d0c311f4189ceef586b2d42.testImplementation

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdkVersion(Config.compileSdk)

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode()
        versionName = Config.versionName()

        testInstrumentationRunner = Config.testInstrumentationRunner
        vectorDrawables.useSupportLibrary = true

        applicationId = Config.applicationId

        vectorDrawables.useSupportLibrary = true

        multiDexEnabled = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    compileOptions {
        sourceCompatibility = Config.javaCompatibilityVersion
        targetCompatibility = Config.javaCompatibilityVersion
    }

    composeOptions {
        kotlinCompilerExtensionVersion = getVersionFromCatalogs("compose.compiler")
    }
    buildFeatures.compose = true

    namespace = Config.applicationId

    kotlinOptions {
        jvmTarget = Config.javaVMTarget
    }
}

dependencies {
    implementation(getLibraryFromCatalogs("core.ktx"))
    implementation(getLibraryFromCatalogs("appcompat"))
    implementation(getLibraryFromCatalogs("material"))
    testImplementation(getLibraryFromCatalogs("junit"))
    androidTestImplementation(getLibraryFromCatalogs("androidx.test.ext.junit"))
    androidTestImplementation(getLibraryFromCatalogs("espresso.core"))

    getLibrariesFromCatalogs("hilt").forEach { implementation(it) }
    kapt(getLibraryFromCatalogs("hilt.android.compiler"))

    getLibrariesFromCatalogs("lifecycle").forEach { implementation(it) }

    // Compose
    implementation(platform(getLibraryFromCatalogs("compose.bom")))
    getLibrariesFromCatalogs("compose.impl").forEach { implementation(it) }
    getLibrariesFromCatalogs("compose.debug.impl").forEach { debugImplementation(it) }
    getLibrariesFromCatalogs("compose.extras").forEach { implementation(it) }

    // Test
    getLibrariesFromCatalogs("test").forEach { testImplementation(it) }
    getLibrariesFromCatalogs("test").forEach { androidTestImplementation(it) }
    androidTestImplementation(getLibraryFromCatalogs("ui.compose.test"))
}