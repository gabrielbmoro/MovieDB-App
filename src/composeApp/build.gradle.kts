@file:Suppress("UnstableApiUsage")

import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.Properties

plugins {
    id("kmp-app-plugin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.buildkonfig.plugin)
    id("io.kotzilla.kotzilla-plugin")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation("io.kotzilla:kotzilla-sdk-ktor3:1.3.1")
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
            implementation(libs.koin.coroutines)

            implementation(projects.platform)
            implementation(projects.designsystem)
            implementation(projects.feature.featureWishlist)
            implementation(projects.feature.featureSearch)
            implementation(projects.feature.featureDetails)
            implementation(projects.feature.featureMovies)
            implementation(projects.util.logging)
        }
    }
}

buildkonfig {
    packageName = "com.gabrielbmoro.moviedb"

    val properties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        properties.load(localPropertiesFile.inputStream())
    }

    defaultConfigs {
        val apiToken = properties.getProperty("MOVIE_DB_API_TOKEN")
            ?: findProperty("MOVIE_DB_API_TOKEN") as? String
            ?: System.getenv("MOVIE_DB_API_TOKEN")
        buildConfigField(FieldSpec.Type.STRING, "API_TOKEN", apiToken)
    }
}
