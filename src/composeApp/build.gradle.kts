@file:Suppress("UnstableApiUsage")

import com.codingfeline.buildkonfig.compiler.FieldSpec

plugins {
    id("kmp-app-plugin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.buildkonfig.plugin)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
        }
        iosMain.dependencies {
            implementation(libs.koin.core)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.navigation.compose)
            implementation(libs.rinku.core)
            implementation(libs.rinku.compose.ext)
            implementation(libs.koin.core)

            implementation(projects.platform)
            implementation(projects.designsystem)
            implementation(projects.feature.wishlist)
            implementation(projects.feature.search)
            implementation(projects.feature.details)
            implementation(projects.feature.movies)
            implementation(projects.data)
            implementation(projects.domain)
            implementation(projects.util.logging)
        }
    }
}

buildkonfig {
    packageName = "com.gabrielbmoro.moviedb"

    defaultConfigs {
        val apiToken = findProperty("MOVIE_DB_API_TOKEN") as? String
            ?: System.getenv("MOVIE_DB_API_TOKEN")
        buildConfigField(FieldSpec.Type.STRING, "API_TOKEN", apiToken)
    }
}
