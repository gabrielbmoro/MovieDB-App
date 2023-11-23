import ext.getVersionFromCatalogs
import org.gradle.kotlin.dsl.kotlin

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("android-library-plugin")
}

android {
    composeOptions {
        kotlinCompilerExtensionVersion = getVersionFromCatalogs("compose.compiler")
    }
    buildFeatures.compose = true
}