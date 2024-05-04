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
    ":core",
    ":feature_movies",
    ":feature_details",
    ":feature_wishlist",
    ":feature_search",
    ":domain",
    ":designsystem",
    ":feature:wishlist",
    ":feature:search",
    ":feature:details",
    ":feature:movies",
    ":platform",
    ":resources"
)

rootProject.name = "MovieDBApp"
