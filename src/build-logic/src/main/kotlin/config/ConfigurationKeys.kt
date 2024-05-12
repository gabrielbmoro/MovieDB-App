package config

import org.gradle.api.JavaVersion

internal object ConfigurationKeys {

    const val APPLICATION_ID = "com.gabrielbmoro.moviedb"
    const val APP_NAME = "ComposeApp"
    const val HAS_UNIT_TESTS_DEFAULT_VALUES = true

    val ELIGIBLE_MODULES_FOR_COVERAGE = listOf(
        ModuleKeys.FEATURE_DETAILS_MODULE,
        ModuleKeys.FEATURE_SEARCH_MODULE,
        ModuleKeys.FEATURE_MOVIES_MODULE,
        ModuleKeys.FEATURE_WISHLIST_MODULE,
        ModuleKeys.DOMAIN_MODULE
    )

    internal object Sdk {
        const val MIN_SDK = 22
        const val TARGET_SDK = 34
        const val COMPILE_SDK = 34
    }

    internal object Java {
        val javaCompatibilityVersion = JavaVersion.VERSION_17
        const val JAVA_VM_TARGET = "17"
    }
}
