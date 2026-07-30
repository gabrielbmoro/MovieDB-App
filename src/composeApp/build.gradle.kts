
plugins {
    id("kmp-library-plugin")
    id("koin-compiler-setup")
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
            implementation(libs.bundles.compose.multiplatform)
            implementation(libs.navigation.compose)
            implementation(libs.rinku.core)
            implementation(libs.rinku.compose.ext)
            implementation(libs.koin.core)
            implementation(libs.koin.coroutines)

            api(projects.designsystem)
            implementation(projects.platform)
            implementation(projects.featureWishlist)
            implementation(projects.featureSearch)
            implementation(projects.featureDetails)
            implementation(projects.featureMovies)
            implementation(projects.featureTvshows)
            implementation(projects.data)
            implementation(projects.domain)
        }
    }
}
