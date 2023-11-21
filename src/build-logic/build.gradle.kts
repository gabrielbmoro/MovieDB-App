plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
    libs.plugins.kover
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
}