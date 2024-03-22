@file:Suppress("UnstableApiUsage")

import config.Config
import gradle.kotlin.dsl.accessors._a1701e0b1f6976f26ab7e509b39e9c83.kotlinOptions

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
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
}

kotlin {
    applyDefaultHierarchyTemplate()

    iosX64()
    iosArm64()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = Config.JAVA_VM_TARGET
            }
        }
    }
}