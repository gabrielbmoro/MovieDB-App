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
    ":composeApp",
    ":data",
    ":domain",
    ":designsystem",
    ":feature:wishlist",
    ":feature:search",
    ":feature:details",
    ":feature:movies",
    ":platform",
    ":util:media",
    ":util:logging",
)

rootProject.name = "MovieDBApp"

plugins {
    id("org.jetbrains.kotlinx.kover.aggregation") version "0.9.1"
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
