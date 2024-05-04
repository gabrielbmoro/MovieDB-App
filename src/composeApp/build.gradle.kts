@file:Suppress("UnstableApiUsage")

import com.codingfeline.buildkonfig.compiler.FieldSpec


plugins {
    id("kmp-app-plugin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    alias(libs.plugins.kover)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.buildkonfig.plugin)
}

kotlin {
    androidTarget()

    sourceSets {
        sourceSets {
            androidMain.dependencies {
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.koin.android)
                implementation(projects.platform)
            }
            iosMain.dependencies {
                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.bundles.koin.impl)
            }
            commonMain.dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.uiToolingPreview)

                implementation(libs.voyager.navigator)
                implementation(libs.rinku.core)
                implementation(libs.rinku.compose.ext)
                implementation(projects.platform)
                implementation(projects.designsystem)
                implementation(projects.feature.wishlist)
                implementation(projects.feature.search)
                implementation(projects.feature.details)
                implementation(projects.feature.movies)
                implementation(projects.data)
                implementation(projects.domain)

                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.bundles.koin.impl)

                implementation(projects.resources)
            }
        }
    }
}

android {
    buildFeatures {
        buildConfig = true
    }
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

ktlint {
    filter {
        exclude("**/generated/**")
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
