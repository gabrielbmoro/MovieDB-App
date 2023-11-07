@file:Suppress("UnstableApiUsage")

fun Project.debugAPIAuth() = findProperty("MOVIE_DB_API_TOKEN_DEBUG")
    ?: System.getenv("MOVIE_DB_API_TOKEN_DEBUG")

fun Project.releaseAPIAuth() = findProperty("MOVIE_DB_API_TOKEN_RELEASE")
    ?: System.getenv("MOVIE_DB_API_TOKEN_RELEASE")

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    alias(libs.plugins.kover)
}

koverReport {
    filters {
        excludes {
            packages(
                "*.di",
                "*.datasources",
                "*.providers",
                "dagger.hilt.internal.aggregatedroot.codegen",
                "hilt_aggregated_deps",
                "*.dto",
                "*.core.ui.*",
                "*.widgets",
                "*.navigation"
            )

            classes(
                "*.BuildConfig",
                "*.ComposableSingletons",
                "*.Hilt_MainActivity",
                "*_Factory*",
                "*Activity",
                "*ScreenKt*",
                "*_HiltModules*"
            )
            annotatedBy("Generated")
        }
    }
}

android {
    signingConfigs {
        create("release") {
            keyAlias = System.getenv("BITRISEIO_ANDROID_KEYSTORE_ALIAS")
            keyPassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD")

            storeFile = file(System.getenv("HOME").plus("/moviedb-keystore"))
            storePassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PASSWORD")
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_TOKEN", "\"${debugAPIAuth()}\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_TOKEN", "\"${releaseAPIAuth()}\"")
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.kotlin)

    implementation(projects.data)
    implementation(projects.core)
    implementation(projects.featureWishlist)
    implementation(projects.featureMovies)
    implementation(projects.featureDetails)
    implementation(projects.featureSearch)

    implementation(libs.appcompat)

    implementation(libs.gson)

    implementation(libs.swipe.refresh.layout)

    implementation(libs.cardview)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.analytics.ktx)

    implementation(libs.bundles.lifecycle)

    implementation(libs.preferences.ktx)

    implementation(libs.timber)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Test
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.test)
    androidTestImplementation(libs.ui.compose.test)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose.impl)
    debugImplementation(libs.bundles.compose.debug.impl)
    implementation(libs.bundles.compose.extras)

    // Navigation
    implementation(libs.navigation.compose)

    // Kover
    kover(projects.core)
    kover(projects.data)
    kover(projects.featureDetails)
    kover(projects.featureMovies)
    kover(projects.featureSearch)
    kover(projects.featureWishlist)
}
