package config

import model.JavaConfiguration
import model.SdkConfiguration
import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

internal object ConfigurationKeys {

    const val APPLICATION_ID = "com.gabrielbmoro.moviedb"
    const val APP_NAME = "ComposeApp"
    const val HAS_UNIT_TESTS_DEFAULT_VALUES = true

    val javaConfiguration = JavaConfiguration(
        javaVmTarget = "17",
        version = JavaVersion.VERSION_17,
        jvmTarget = JvmTarget.JVM_17,
    )

    val sdkConfiguration = SdkConfiguration(
        minSdk = 28,
        targetSdk = 35,
        compileSdk = 35,
    )

    val ELIGIBLE_MODULES_FOR_COVERAGE = listOf(
        ModuleKeys.FEATURE_DETAILS_MODULE,
        ModuleKeys.FEATURE_SEARCH_MODULE,
        ModuleKeys.FEATURE_MOVIES_MODULE,
        ModuleKeys.FEATURE_WISHLIST_MODULE,
        ModuleKeys.DOMAIN_MODULE,
    )
}
