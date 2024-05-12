@file:Suppress("UnstableApiUsage")

import config.Config
import ext.configureCompileOptions
import ext.configureDefaultConfig
import ext.configurePlatformTargets
import ext.configureTestOptions

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
}

android {
    compileSdk = Config.COMPILE_SDK

    configureDefaultConfig()
    configureCompileOptions()
    configureTestOptions()

    namespace = Config.APPLICATION_ID.plus(name)
}

kotlin {
    configurePlatformTargets()
}
