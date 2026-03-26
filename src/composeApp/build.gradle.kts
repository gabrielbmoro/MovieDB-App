
plugins {
    id("kmp-app-plugin")
    id("kotlin-parcelize")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    id("io.kotzilla.kotzilla-plugin")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.koin.android)
            implementation(libs.kotzilla.sdk.ktor3)
        }
        iosMain.dependencies {
            implementation(libs.koin.core)
        }
        commonMain.dependencies {
            implementation(libs.bundles.compose.multiplatform)
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
