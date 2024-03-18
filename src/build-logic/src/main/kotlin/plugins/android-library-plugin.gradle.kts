@file:Suppress("UnstableApiUsage")

import config.Config

import org.gradle.kotlin.dsl.kotlin

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
}

android {
    compileSdk = Config.COMPILE_SDK

    defaultConfig {
        minSdk = Config.MIN_SDK
    }

    compileOptions {
        sourceCompatibility = Config.javaCompatibilityVersion
        targetCompatibility = Config.javaCompatibilityVersion
    }

    testOptions {
        unitTests.isReturnDefaultValues = Config.HAS_UNIT_TESTS_DEFAULT_VALUES
    }

    kotlinOptions {
        jvmTarget = Config.JAVA_VM_TARGET
    }
}