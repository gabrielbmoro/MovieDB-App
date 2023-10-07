@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.gabrielbmoro.moviedb.feature.search"
}

dependencies {
    api(project(":core"))
    api(project(":data"))
}