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

    // Koin
    commonMainImplementation(platform(libs.koin.bom))
    commonMainImplementation(libs.bundles.koin.impl)
}