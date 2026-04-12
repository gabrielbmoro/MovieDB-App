
plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    sourceSets {
        iosMain.dependencies {
            implementation(libs.koin.core)
        }
        commonMain.dependencies {
            implementation(libs.kotzilla.sdk.compose)
            implementation(libs.bundles.compose.multiplatform)
            implementation(libs.navigation.compose)
            implementation(libs.rinku.core)
            implementation(libs.rinku.compose.ext)
            implementation(libs.koin.core)
            implementation(libs.koin.coroutines)

            api(projects.designsystem)
            implementation(projects.platform)
            implementation(projects.feature.featureWishlist)
            implementation(projects.feature.featureSearch)
            implementation(projects.feature.featureDetails)
            implementation(projects.feature.featureMovies)
            implementation(projects.util.logging)
        }
    }
}
