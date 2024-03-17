@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("android-compose-library-plugin")
    alias(libs.plugins.kover)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.gabrielbmoro.moviedb.feature.movies"
}

dependencies {
    implementation(projects.domain)
    implementation(projects.core)

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.timber)

    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.bundles.koin.impl)

    implementation(libs.bundles.lifecycle)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose.impl)
    debugImplementation(libs.bundles.compose.debug.impl)
    implementation(libs.bundles.compose.extras)

    // Test
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.test)
    androidTestImplementation(libs.ui.compose.test)

    implementation(libs.bundles.voyager)
}