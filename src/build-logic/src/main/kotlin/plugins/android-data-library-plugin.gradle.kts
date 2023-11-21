import config.Config

import ext.getLibrariesFromCatalogs
import ext.getLibraryFromCatalogs
import gradle.kotlin.dsl.accessors._c89c08324d0c311f4189ceef586b2d42.android
import gradle.kotlin.dsl.accessors._c89c08324d0c311f4189ceef586b2d42.androidTestImplementation
import gradle.kotlin.dsl.accessors._c89c08324d0c311f4189ceef586b2d42.implementation
import gradle.kotlin.dsl.accessors._c89c08324d0c311f4189ceef586b2d42.kapt
import gradle.kotlin.dsl.accessors._c89c08324d0c311f4189ceef586b2d42.testImplementation

import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
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

    // Test
    getLibrariesFromCatalogs("test").forEach { testImplementation(it) }
}