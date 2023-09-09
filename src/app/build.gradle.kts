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
    signingConfigs {
        create("release") {
            keyAlias = System.getenv("BITRISEIO_ANDROID_KEYSTORE_ALIAS")
            keyPassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD")

            storeFile = file(System.getenv("HOME").plus( "/moviedb-keystore"))
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
        compose = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.kotlin)

    api(project(":repository"))

    api(project(":core"))

    implementation(libs.appcompat)

    implementation(libs.gson)

    implementation(libs.swipe.refresh.layout)

    implementation(libs.cardview)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.analytics.ktx)

    implementation(libs.bundles.lifecycle)

    implementation(libs.preferences.ktx)

    implementation(libs.timber)

    // Koin
    implementation(libs.bundles.koin)

    // Test
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.test)
    androidTestImplementation(libs.ui.compose.test)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.bom.ui)
    implementation(libs.compose.bom.preview)
    implementation(libs.compose.bom.activity)
    debugImplementation(libs.compose.bom.ui.tooling)
    debugImplementation(libs.compose.bom.ui.test.manifest)
    implementation(libs.bundles.compose.extras)

    // Navigation
    implementation(libs.navigation.compose)

    // Player
    implementation(libs.android.youtube.player)
}