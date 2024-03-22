@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kover)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.gabrielbmoro.moviedb.data"
}

dependencies {

    commonMainImplementation(projects.domain)

    androidMainImplementation(libs.preferences.ktx)
    androidMainImplementation(libs.bundles.ktor)
    androidMainImplementation(libs.kotlinx.serialization.json)

    // Room
    ksp(libs.room.compiler)
    androidMainImplementation(libs.bundles.room)

    // Test
    commonTestImplementation(libs.bundles.test.multiplatform)
    androidTestImplementation(libs.bundles.test)
    androidMainImplementation(libs.timber)

    // Koin
    commonMainImplementation(platform(libs.koin.bom))
    commonMainImplementation(libs.bundles.koin.impl)
    androidMainImplementation(libs.koin.android)
}
