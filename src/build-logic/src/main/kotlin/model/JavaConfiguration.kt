package model

import org.gradle.api.JavaVersion

data class JavaConfiguration(
    val version: JavaVersion,
    val javaVmTarget: String,
)