@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("android-feature-library-plugin")
    alias(libs.plugins.kover)
}

android {
    namespace = "com.gabrielbmoro.moviedb.feature.details"
}

dependencies {
    implementation(projects.data)
    implementation(projects.core)

    // Player
    implementation(libs.android.youtube.player)
}
