@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
}

android {
    compileSdk = ConfigData.compileSdkVersion

    defaultConfig {
        applicationId = ConfigData.applicationId
        minSdk = ConfigData.minSdkVersion
        targetSdk = ConfigData.targetSdkVersion

        versionCode = ConfigData.versionCode
        versionName = ConfigData.versionName

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
        kotlinCompilerExtensionVersion = Versions.composeCompilerVersion
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
    namespace = "com.gabrielbmoro.programmingchallenge"
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlinVersion}")

    implementation("androidx.appcompat:appcompat:${Versions.appcompatVersion}")

    implementation("androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefreshVersion}")

    implementation("androidx.cardview:cardview:${Versions.cardViewVersion}")

    kapt("androidx.room:room-compiler:${Versions.roomVersion}")
    implementation("androidx.room:room-ktx:${Versions.roomVersion}")
    implementation("androidx.room:room-runtime:${Versions.roomVersion}")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycleVersion}")

    implementation("androidx.lifecycle:lifecycle-extensions:${Versions.lifecycleExtensionsVersion}")

    implementation("androidx.preference:preference-ktx:${Versions.preferenceVersion}")

    implementation("com.jakewharton.timber:timber:${Versions.timberVersion}")

    implementation("com.google.code.gson:gson:${Versions.gsonVersion}")

    implementation("com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-gson:${Versions.retrofitVersion}")

    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:${Versions.hiltVersion}")
    kapt("com.google.dagger:hilt-compiler:${Versions.hiltVersion}")
    kapt("com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}")
    implementation("androidx.hilt:hilt-navigation-compose:${Versions.hiltNavigationCompose}")


    testImplementation("junit:junit:${Versions.junitVersion}")

    testImplementation("androidx.arch.core:core-common:${Versions.archTestCoreVersion}")
    testImplementation("androidx.arch.core:core-runtime:${Versions.archTestCoreVersion}")
    testImplementation("androidx.arch.core:core-testing:${Versions.archTestCoreVersion}")

    testImplementation("com.google.truth:truth:${Versions.truthVersion}")
    testImplementation("androidx.test:core:${Versions.testCoreVersion}")
    testImplementation("io.mockk:mockk:${Versions.mockKVersion}")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesVersion}")

    // Compose
    implementation(platform("androidx.compose:compose-bom:${Versions.composeBomVersion}"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.material3:material3:${Versions.composeMaterial3Version}")

    // Coil
    implementation("io.coil-kt:coil-compose:${Versions.coilVersion}")

    // Navigation
    implementation("androidx.navigation:navigation-compose:${Versions.navComposeIntegrationVersion}")
}