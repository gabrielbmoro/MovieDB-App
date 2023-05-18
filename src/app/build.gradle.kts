@file:Suppress("UnstableApiUsage")
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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

    ksp(libs.room.compiler)
    implementation(libs.bundles.room)

    implementation(libs.bundles.lifecycle)

    implementation(libs.preferences.ktx)

    implementation(libs.timber)

    implementation(libs.bundles.retrofit)

    // Dagger - Hilt
    ksp(libs.hilt.compiler)
    ksp(libs.hilt.android.compiler)
    implementation(libs.bundles.hilt)


    testImplementation(libs.bundles.test)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation(libs.bundles.compose.extras)

    // Coil
    implementation(libs.coil)

    // Navigation
    implementation(libs.navigation.compose)
}