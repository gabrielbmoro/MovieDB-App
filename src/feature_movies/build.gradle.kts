@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    alias(libs.plugins.kover)
}

android {
    namespace = "com.gabrielbmoro.moviedb.feature.movies"
}

dependencies {

    implementation(projects.data)
    implementation(projects.core)

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose.impl)
    debugImplementation(libs.bundles.compose.debug.impl)
    implementation(libs.bundles.compose.extras)

    implementation(libs.bundles.lifecycle)

    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)

    // Test
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.test)
    androidTestImplementation(libs.ui.compose.test)
}
