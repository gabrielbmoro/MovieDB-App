@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("android-data-library-plugin")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kover)
}

android {
    namespace = "com.gabrielbmoro.moviedb.data"
}

dependencies {
    implementation(libs.preferences.ktx)

    implementation(libs.timber)

    implementation(libs.bundles.retrofit)

    ksp(libs.room.compiler)
    implementation(libs.bundles.room)

    implementation(libs.paging.compose)
}
