import config.ConfigurationKeys
import ext.configurePlatformTargets

plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.kotlin.multiplatform.library")
}

kotlin {
    android {
        minSdk = ConfigurationKeys.sdkConfiguration.minSdk
        compileSdk = ConfigurationKeys.sdkConfiguration.compileSdk

        namespace = ConfigurationKeys.APPLICATION_ID.plus(".$name")
            .replace("-", "_")

        androidResources.enable = true
    }
    configurePlatformTargets(
        isIosEnabled = hasProperty("kmp.enableIos"),
    )
}
