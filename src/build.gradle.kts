@file:Suppress("UnstableApiUsage")

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin

plugins {
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ktlint)
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
            jvmTarget = Config.javaVMTarget
        }
    }
}

val ktLintPluginId: String = libs.plugins.ktlint.get().pluginId

subprojects {
    apply(plugin = ktLintPluginId) // Version should be inherited from parent

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
    compileSdkVersion(Config.compileSdk)

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode()
        versionName = Config.versionName()

        testInstrumentationRunner = Config.testInstrumentationRunner
        vectorDrawables.useSupportLibrary = true

        applicationId = Config.applicationId

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
        sourceCompatibility = Config.javaCompatibilityVersion
        targetCompatibility = Config.javaCompatibilityVersion
    }

    buildFeatures.compose = true

    namespace = Config.applicationId
}

// Extension function on `LibraryExtension`
fun LibraryExtension.applyCommons() {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    compileOptions {
        sourceCompatibility = Config.javaCompatibilityVersion
        targetCompatibility = Config.javaCompatibilityVersion
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    buildFeatures.compose = true
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

object Config {
    const val applicationId = "com.gabrielbmoro.moviedb"
    const val minSdk = 22
    const val targetSdk = 33
    const val compileSdk = 33
    private const val localVersionCode = 10
    private const val localVersionName = "1.0.0"
    val javaCompatibilityVersion = JavaVersion.VERSION_17
    const val javaVMTarget = "17"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    fun versionCode(): Int {
        val versionCode = try {
            System.getenv("BITRISE_BUILD_NUMBER").toIntOrNull()
        } catch (nullPointerException: NullPointerException) {
            null
        }
        return versionCode ?: localVersionCode
    }

    fun versionName(): String {
        val versionName = try {
            "1.8.${System.getenv("BITRISE_BUILD_NUMBER")}"
        } catch (nullPointerException: NullPointerException) {
            null
        }

        return versionName?.ifEmpty { localVersionName } ?: localVersionName
    }
}
