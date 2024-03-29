@file:Suppress("UnstableApiUsage")

plugins {
    id("kmp-app-plugin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    alias(libs.plugins.kover)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrains.compose)
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
            commonMain.dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.uiToolingPreview)

                implementation(libs.voyager.navigator)
                implementation(projects.platform)
                implementation(projects.designSystem)
                implementation(projects.feature.wishlist)
                implementation(projects.feature.search)
                implementation(projects.feature.details)
                implementation(projects.feature.movies)
                implementation(projects.data)
                implementation(projects.domain)

                implementation(project.dependencies.platform(libs.koin.bom))
                implementation(libs.bundles.koin.impl)
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
