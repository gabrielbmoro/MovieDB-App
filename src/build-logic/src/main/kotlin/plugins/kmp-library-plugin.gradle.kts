@file:Suppress("UnstableApiUsage")

import config.Config

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
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