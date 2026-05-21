plugins {
    id("kmp-library-plugin")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // region VIOLATION
            implementation(projects.feature.featureMovies)
            // endregion
            implementation(libs.kermit)
            implementation(libs.koin.core)
        }
    }
}
