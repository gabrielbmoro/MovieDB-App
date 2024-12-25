@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.buildkonfig.plugin) apply false
}

buildscript {
    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.google.services)
        classpath(libs.firebase.crashlytics.gradle)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
