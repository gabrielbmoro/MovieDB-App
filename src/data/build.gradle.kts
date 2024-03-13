@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("android-library-plugin")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kover)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.gabrielbmoro.moviedb.data"
}

dependencies {

    implementation(projects.domain)

    implementation(libs.preferences.ktx)
    implementation(libs.timber)
    implementation(libs.bundles.ktor)

    implementation(libs.kotlinx.serialization.json)

    // Room
    ksp(libs.room.compiler)
    implementation(libs.bundles.room)

    // Test
    testImplementation(libs.bundles.test)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin.impl)
}
