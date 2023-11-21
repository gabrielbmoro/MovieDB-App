import config.Config

import ext.getLibrariesFromCatalogs
import ext.getLibraryFromCatalogs
import ext.getVersionFromCatalogs


import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
    }

    compileOptions {
        sourceCompatibility = Config.javaCompatibilityVersion
        targetCompatibility = Config.javaCompatibilityVersion
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = getVersionFromCatalogs("compose.compiler")
    }
    buildFeatures.compose = true

    kotlinOptions {
        jvmTarget = Config.javaVMTarget
    }
}

dependencies {
    implementation(getLibraryFromCatalogs("core.ktx"))
    implementation(getLibraryFromCatalogs("appcompat"))
    implementation(getLibraryFromCatalogs("material"))
    implementation(getLibraryFromCatalogs("timber"))
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