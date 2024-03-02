@file:Suppress("UnstableApiUsage")

plugins {
    id("android-app-plugin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    alias(libs.plugins.kover)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.ksp)
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.kotlin)

    implementation(projects.data)
    implementation(projects.core)
    implementation(projects.domain)
    implementation(projects.featureWishlist)
    implementation(projects.featureMovies)
    implementation(projects.featureDetails)
    implementation(projects.featureSearch)

    implementation(libs.gson)

    implementation(libs.swipe.refresh.layout)

    implementation(libs.cardview)
    implementation(libs.firebase.crashlytics.ktx)
    implementation(libs.firebase.analytics.ktx)

    implementation(libs.bundles.lifecycle)

    implementation(libs.preferences.ktx)

    implementation(libs.timber)

    // Navigation
    implementation(libs.navigation.compose)

    // Kover
    rootProject.subprojects.forEach { kover(it) }

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)

    implementation(libs.bundles.lifecycle)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose.impl)
    debugImplementation(libs.bundles.compose.debug.impl)
    implementation(libs.bundles.compose.extras)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin.impl)

    // Test
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.test)
    androidTestImplementation(libs.ui.compose.test)
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
