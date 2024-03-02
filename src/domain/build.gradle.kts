@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("android-library-plugin")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kover)
}

android {
    namespace = "com.gabrielbmoro.moviedb.domain"
}

dependencies {
    implementation(libs.paging.compose)

    // Test
    testImplementation(libs.bundles.test)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin.impl)
}