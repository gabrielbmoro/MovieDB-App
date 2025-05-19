plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
    maven(url = "https://plugins.gradle.org/m2/")
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.gradle)
    implementation(libs.com.google.devtools.ksp.gradle.plugin)
    implementation(libs.popcorn.guineapig)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
}
