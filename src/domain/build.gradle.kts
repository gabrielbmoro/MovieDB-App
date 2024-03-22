@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.kover)
}

android {
    namespace = "com.gabrielbmoro.moviedb.domain"
}

dependencies {
    commonTestImplementation(libs.bundles.test.multiplatform)

    // Test
    testImplementation(libs.bundles.test)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin.impl)
}