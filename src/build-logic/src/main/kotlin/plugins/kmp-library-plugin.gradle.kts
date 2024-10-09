@file:Suppress("UnstableApiUsage")

import com.gabrielbmoro.popcorn.domain.input.PopcornConfiguration
import com.gabrielbmoro.popcorn.domain.input.PopcornProject
import com.gabrielbmoro.popcorn.domain.input.ProjectType
import com.gabrielbmoro.popcorn.domain.rules.NoDependencyRule
import config.ConfigurationKeys
import ext.configureCompileOptions
import ext.configureDefaultConfig
import ext.configurePlatformTargets
import ext.configureTestOptions

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("org.jetbrains.kotlinx.kover")
    id("detekt-plugin-setup")
    id("io.github.gabrielbmoro.popcorngp")
}

android {
    compileSdk = ConfigurationKeys.sdkConfiguration.compileSdk

    configureDefaultConfig()
    configureCompileOptions()
    configureTestOptions()

    namespace = ConfigurationKeys.APPLICATION_ID.plus(name)
}

kotlin {
    configurePlatformTargets()
}

popcornGuineapigConfig {
    configuration = PopcornConfiguration(
        project = PopcornProject(
            type = ProjectType.KMP
        ),
        rules = listOf(
            NoDependencyRule()
        )
    )
}