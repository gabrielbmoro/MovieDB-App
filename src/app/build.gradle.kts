@file:Suppress("UnstableApiUsage")
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = "com.gabrielbmoro.programmingchallenge"
        minSdk = 22
        targetSdk = 33

        versionCode = 23
        versionName = "1.5.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    /*
    * When I add the CustomPreference I needed to put this block to avoid the error:
    * - Invoke-customs are only supported starting with Android O (--min-api 26.
    * - Reference: https://stackoverflow.com/questions/49891730/invoke-customs-are-only-supported-starting-with-android-0-min-api-26
    */
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildTypes {
        debug {
            resValue("string", "api_token", findProperty("MOVIE_DB_API_TOKEN_DEBUG").toString())
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            resValue("string", "api_token", findProperty("MOVIE_DB_API_TOKEN_RELEASE").toString())
        }
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
    namespace = "com.gabrielbmoro.programmingchallenge"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.kotlin)

    implementation(libs.appcompat)

    implementation(libs.swipe.refresh.layout)

    implementation(libs.cardview)

    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)

    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.livedata.ktx)

    implementation(libs.lifecycle.extensions)

    implementation(libs.preferences.ktx)

    implementation(libs.timber)

    implementation(libs.gson)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.json)

    // Dagger - Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.navigation.compose)


    testImplementation(libs.junit)

    testImplementation(libs.arc.core.common)
    testImplementation(libs.arc.core.runtime)
    testImplementation(libs.arc.core.testing)

    testImplementation(libs.truth)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation(libs.compose.material.get3())
    implementation(libs.navigation.compose)

    // Coil
    implementation(libs.coil)

    // Navigation
    implementation(libs.navigation.compose)
}