package config

import org.gradle.api.JavaVersion

object Config {
    internal const val APPLICATION_ID = "com.gabrielbmoro.moviedb"
    internal const val APP_NAME = "ComposeApp"
    internal const val MIN_SDK = 22
    internal const val TARGET_SDK = 34
    internal const val COMPILE_SDK = 34

    private const val LOCAL_VERSION_CODE = 10
    private const val LOCAL_VERSION_NAME = "1.0.0"
    internal val javaCompatibilityVersion = JavaVersion.VERSION_17
    internal const val JAVA_VM_TARGET = "17"
    internal const val HAS_UNIT_TESTS_DEFAULT_VALUES = true

    internal val ELIGIBLE_MODULES_FOR_COVERAGE = listOf(
        Module.FEATURE_DETAILS_MODULE,
        Module.FEATURE_SEARCH_MODULE,
        Module.FEATURE_MOVIES_MODULE,
        Module.FEATURE_WISHLIST_MODULE,
        Module.DOMAIN_MODULE
    )

    internal fun versionCode(): Int {
        val versionCode =
            try {
                System.getenv("BITRISE_BUILD_NUMBER").toIntOrNull()
            } catch (nullPointerException: NullPointerException) {
                null
            }
        return versionCode ?: LOCAL_VERSION_CODE
    }

    internal fun versionName(): String {
        val versionName =
            try {
                "1.8.${System.getenv("BITRISE_BUILD_NUMBER")}"
            } catch (nullPointerException: NullPointerException) {
                null
            }

        return versionName?.ifEmpty { LOCAL_VERSION_NAME } ?: LOCAL_VERSION_NAME
    }

    internal object Module {
        const val FEATURE_DETAILS_MODULE = "details"
        const val FEATURE_SEARCH_MODULE = "search"
        const val FEATURE_MOVIES_MODULE = "movies"
        const val FEATURE_WISHLIST_MODULE = "wishlist"
        const val DOMAIN_MODULE = "domain"
    }
}
