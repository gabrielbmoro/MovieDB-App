package config

import model.JavaConfiguration
import model.SdkConfiguration
import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

internal object ConfigurationKeys {

    const val APPLICATION_ID = "com.gabrielbmoro.moviedb"
    const val APP_NAME = "ComposeApp"

    val javaConfiguration = JavaConfiguration(
        javaVmTarget = "21",
        version = JavaVersion.VERSION_21,
        jvmTarget = JvmTarget.JVM_21,
    )

    val sdkConfiguration = SdkConfiguration(
        minSdk = 28,
        targetSdk = 36,
        compileSdk = 36,
    )
}
