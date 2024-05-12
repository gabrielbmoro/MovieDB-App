import config.ConfigurationKeys
import ext.configureBuildTypes
import ext.configureCompileOptions
import ext.configureDefaultConfig
import ext.configurePlatformTargets
import ext.configureSigning
import ext.configureTestOptions

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.multiplatform")
}

android {
    compileSdk = ConfigurationKeys.sdkConfiguration.compileSdk
    namespace = ConfigurationKeys.APPLICATION_ID

    defaultConfig {
        applicationId = ConfigurationKeys.APPLICATION_ID
    }

    configureDefaultConfig()
    configureCompileOptions()
    configureTestOptions()
    configureSigning()
    configureBuildTypes()
}

kotlin {
    configurePlatformTargets()
}
