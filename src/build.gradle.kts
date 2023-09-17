@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin

plugins {
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
    id("org.jlleitschuh.gradle.ktlint") version "11.5.1"
}

buildscript {
    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.google.services)
        classpath(libs.firebase.crashlytics.gradle)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).configureEach {
        kotlinOptions {
            jvmTarget = ConfigData.JAVA_VM_TARGET
        }
    }
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint") // Version should be inherited from parent

    // Accessing the `PluginContainer` in order to use `whenPluginAdded` function
    project.plugins.configure(project = project)

    // Optionally configure plugin
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        debug.set(true)
    }
}

// Extension function on `PluginContainer`
fun PluginContainer.configure(project: Project) {
    whenPluginAdded {
        when (this) {
            is AppPlugin -> {
                project.extensions
                    .getByType<AppExtension>()
                    .apply {
                        applyCommons()
                    }
            }

            is LibraryPlugin -> {
                project.extensions
                    .getByType<LibraryExtension>()
                    .apply {
                        applyCommons()
                    }
            }
        }
    }
}

// Extension function on `AppExtension`
fun AppExtension.applyCommons() {
    compileSdkVersion(ConfigData.COMPILE_SDK)

    defaultConfig {
        minSdk = ConfigData.MIN_SDK
        targetSdk = ConfigData.TARGET_SDK
        versionCode = ConfigData.versionCode()
        versionName = ConfigData.versionName()

        testInstrumentationRunner = ConfigData.TEST_INSTRUMENTATION_RUNNER
        vectorDrawables.useSupportLibrary = true

        applicationId = ConfigData.APPLICATION_ID

        vectorDrawables.useSupportLibrary = true

        multiDexEnabled = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    compileOptions {
        sourceCompatibility = ConfigData.JAVA_COMPATIBILITY_VERSION
        targetCompatibility = ConfigData.JAVA_COMPATIBILITY_VERSION
    }

    buildFeatures.compose = true

    namespace = ConfigData.APPLICATION_ID
}

// Extension function on `LibraryExtension`
fun LibraryExtension.applyCommons() {
    compileSdk = ConfigData.COMPILE_SDK

    defaultConfig {
        minSdk = ConfigData.MIN_SDK
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    compileOptions {
        sourceCompatibility = ConfigData.JAVA_COMPATIBILITY_VERSION
        targetCompatibility = ConfigData.JAVA_COMPATIBILITY_VERSION
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    buildFeatures.compose = true
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

object ConfigData {
    const val APPLICATION_ID = "com.gabrielbmoro.moviedb"
    const val MIN_SDK = 22
    const val TARGET_SDK = 33
    const val COMPILE_SDK = 33
    private const val LOCAL_VERSION_CODE = 10
    private const val LOCAL_VERSION_NAME = "1.0.0"
    val JAVA_COMPATIBILITY_VERSION = JavaVersion.VERSION_17
    const val JAVA_VM_TARGET = "17"
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
            "1.6.${System.getenv("BITRISE_BUILD_NUMBER")}"
        } catch (nullPointerException: NullPointerException) {
            null
        }

        return versionName?.ifEmpty { LOCAL_VERSION_NAME } ?: LOCAL_VERSION_NAME
    }
}
