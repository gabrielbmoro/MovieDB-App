import config.Config
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
    compileSdk = Config.COMPILE_SDK
    namespace = Config.APPLICATION_ID

    defaultConfig {
        applicationId = Config.APPLICATION_ID
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
