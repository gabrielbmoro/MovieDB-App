@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin

plugins {
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
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
}

subprojects {
    // Accessing the `PluginContainer` in order to use `whenPluginAdded` function
    project.plugins.configure(project = project)
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

    defaultConfig.apply {
        minSdk = ConfigData.MIN_SDK
        targetSdk = ConfigData.TARGET_SDK
        versionCode = ConfigData.versionCode()
        versionName = ConfigData.versionName()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    namespace = ConfigData.APPLICATION_ID
}

// Extension function on `LibraryExtension`
fun LibraryExtension.applyCommons() {
    compileSdk = ConfigData.COMPILE_SDK

    defaultConfig.apply {
        minSdk = ConfigData.MIN_SDK
        targetSdk = ConfigData.TARGET_SDK
    }

    compileOptions.apply {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}


tasks.register("clean",Delete::class){
    delete(rootProject.buildDir)
}