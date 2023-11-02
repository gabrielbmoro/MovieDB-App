enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":app",
    ":data",
    ":core",
    ":feature_movies",
    ":feature_details",
    ":feature_wishlist",
    ":feature_search"
)

rootProject.name = "MovieDB-Android"
