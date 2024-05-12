@file:Suppress("UnstableApiUsage")

import config.ConfigurationKeys
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
    compileSdk = ConfigurationKeys.sdkConfiguration.compileSdk

    configureDefaultConfig()
    configureCompileOptions()
    configureTestOptions()

    namespace = ConfigurationKeys.APPLICATION_ID.plus(name)
}

kotlin {
    configurePlatformTargets()
}
