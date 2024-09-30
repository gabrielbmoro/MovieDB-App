@file:Suppress("UnstableApiUsage")

import com.gabrielbmoro.popcorn.domain.entity.PopcornConfiguration
import com.gabrielbmoro.popcorn.domain.entity.PopcornJustWithRule
import com.gabrielbmoro.popcorn.domain.entity.PopcornNoRelationShipRule
import com.gabrielbmoro.popcorn.domain.entity.PopcornProject
import com.gabrielbmoro.popcorn.domain.entity.PopcornRules
import com.gabrielbmoro.popcorn.domain.entity.ProjectType
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
        rules = PopcornRules(
            noRelationship = listOf(
                PopcornNoRelationShipRule("domain"),
                PopcornNoRelationShipRule("resources"),
                PopcornNoRelationShipRule("platform")
            ),
            justWith = listOf(
                PopcornJustWithRule(
                    target = "data",
                    with = listOf(
                        "domain"
                    )
                ),
                PopcornJustWithRule(
                    target = "designsystem",
                    with = listOf(
                        "resources"
                    )
                )
            ),
            doNotWith = emptyList()
        )
    )
}
