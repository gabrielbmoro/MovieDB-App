@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.buildkonfig.plugin) apply false
}

private val detektConfiguration by configurations.creating

dependencies {
    detektConfiguration(libs.detekt.cli)
    detektConfiguration(libs.detekt.formatting)
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

tasks.register<JavaExec>("linter") {
    mainClass.set("io.gitlab.arturbosch.detekt.cli.Main")
    classpath = detektConfiguration

    val input = projectDir
    val config = "$projectDir/config/detekt/detekt.yml"
    val exclude = "**/build/**,."
    val params = listOf("-i", input, "-c", config, "-ex", exclude)

    args(params)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}
