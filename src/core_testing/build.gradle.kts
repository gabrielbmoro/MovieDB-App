@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.gabrielbmoro.moviedb.core.testing"
}

dependencies {
    // Test
    implementation(libs.bundles.test)
    implementation(libs.bundles.test)
    implementation(libs.ui.compose.test)
}