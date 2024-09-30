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
    implementation(libs.kover.gradle.plugin)
    implementation(libs.com.google.devtools.ksp.gradle.plugin)
    implementation(libs.detekt.gradle.plugin)
    implementation("io.github.gabrielbmoro.popcorngp:io.github.gabrielbmoro.popcorngp.gradle.plugin:1.0.8")

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test)
}

tasks.test {
    testLogging {
        events("passed", "skipped", "failed")
    }
}
