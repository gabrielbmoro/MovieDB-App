@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.android.library")
    kotlin("android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
}

android {
    namespace = "${ConfigData.APPLICATION_ID}.repository"
}

dependencies {
    implementation(libs.preferences.ktx)

    implementation(libs.timber)

    implementation(libs.bundles.retrofit)

    ksp(libs.room.compiler)
    implementation(libs.bundles.room)

    implementation(libs.paging.compose)

    // Koin
    implementation(libs.bundles.koin)

    // Test
    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.test)
    androidTestImplementation(libs.ui.compose.test)
}