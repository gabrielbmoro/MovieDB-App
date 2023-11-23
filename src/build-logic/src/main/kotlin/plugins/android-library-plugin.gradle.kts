import config.Config

import gradle.kotlin.dsl.accessors._c89c08324d0c311f4189ceef586b2d42.android

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
        unitTests.isReturnDefaultValues = Config.haveUnitTestsDefaultValues
    }

    kotlinOptions {
        jvmTarget = Config.javaVMTarget
    }
}