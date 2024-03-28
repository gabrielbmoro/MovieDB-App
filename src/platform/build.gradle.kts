@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.kover)
    alias(libs.plugins.jetbrains.compose)
}

android {
    namespace = "com.gabrielbmoro.moviedb.platform"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.voyager)
            implementation(libs.kotlinx.coroutines.core)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
        }

        androidMain.dependencies {
            implementation(libs.material)
        }
    }
}