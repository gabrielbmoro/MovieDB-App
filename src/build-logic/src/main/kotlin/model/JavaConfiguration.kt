package model

import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

data class JavaConfiguration(
    val version: JavaVersion,
    val javaVmTarget: String,
    val jvmTarget: JvmTarget,
)
