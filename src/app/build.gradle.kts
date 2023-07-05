@file:Suppress("UnstableApiUsage")
@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdk = 33

    defaultConfig {
        applicationId = ConfigData.APPLICATION_ID
        minSdk = ConfigData.MIN_SDK
        targetSdk = ConfigData.TARGET_SDK

        versionCode = ConfigData.versionCode()
        versionName = ConfigData.versionName()

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

    signingConfigs {
        create("release") {
            keyAlias = System.getenv("BITRISEIO_ANDROID_KEYSTORE_ALIAS")
            keyPassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD")

            storeFile = file(System.getenv("HOME").plus( "/keystores/moviedb-keystore"))
            storePassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PASSWORD")
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_TOKEN", "\"${debugAPIAuth()}\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "API_TOKEN", "\"${releaseAPIAuth()}\"")
            signingConfig = signingConfigs.getByName("release")
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
    namespace = ConfigData.APPLICATION_ID
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.kotlin)

    implementation(libs.appcompat)

    implementation(libs.swipe.refresh.layout)

    implementation(libs.cardview)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.analytics.ktx)

    ksp(libs.room.compiler)
    implementation(libs.bundles.room)

    implementation(libs.bundles.lifecycle)

    implementation(libs.preferences.ktx)

    implementation(libs.timber)

    implementation(libs.bundles.retrofit)

    // Dagger - Hilt
    implementation(libs.bundles.koin)


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