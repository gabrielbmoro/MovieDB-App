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
    ":resources",
    ":util:media"
)

rootProject.name = "MovieDBApp"
