@file:Suppress("UnstableApiUsage")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(
    ":androidApp",
    ":composeApp",
    ":data",
    ":domain",
    ":designsystem",
    ":feature-wishlist",
    ":feature-search",
    ":feature-movies",
    ":feature-tvshows",
    ":platform"
)

rootProject.name = "MovieDBApp"

plugins {
    id("org.jetbrains.kotlinx.kover.aggregation") version "0.9.8"
}

/**
 * To run kover aggregated plugin you need:
 * ./gradlew test -Pkover koverHtmlReport
 */
kover {
    enableCoverage()

    reports {
        excludedClasses.addAll(
            listOf(
                "*.BuildConfig",
                "*_Factory*",
                "*Activity",
                "*ScreenKt*",
                "*.generated.resources*",
            ),
        )
    }
}
