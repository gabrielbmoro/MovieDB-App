package model

import org.gradle.api.JavaVersion

internal data class JavaConfiguration(
    val version: JavaVersion,
    val javaVmTarget: String,
)