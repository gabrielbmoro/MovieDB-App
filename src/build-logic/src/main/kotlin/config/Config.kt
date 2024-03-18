package config

import org.gradle.api.JavaVersion

object Config {
    const val APPLICATION_ID = "com.gabrielbmoro.moviedb"
    const val MIN_SDK = 22
    const val TARGET_SDK = 34
    const val COMPILE_SDK = 34
    private const val LOCAL_VERSION_CODE = 10
    private const val LOCAL_VERSION_NAME = "1.0.0"
    val javaCompatibilityVersion = JavaVersion.VERSION_17
    const val JAVA_VM_TARGET = "17"
    const val HAS_UNIT_TESTS_DEFAULT_VALUES = true
    const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"

    fun versionCode(): Int {
        val versionCode = try {
            System.getenv("BITRISE_BUILD_NUMBER").toIntOrNull()
        } catch (nullPointerException: NullPointerException) {
            null
        }
        return versionCode ?: LOCAL_VERSION_CODE
    }

    fun versionName(): String {
        val versionName = try {
            "1.8.${System.getenv("BITRISE_BUILD_NUMBER")}"
        } catch (nullPointerException: NullPointerException) {
            null
        }

        return versionName?.ifEmpty { LOCAL_VERSION_NAME } ?: LOCAL_VERSION_NAME
    }
}