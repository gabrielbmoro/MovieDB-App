@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.kover)
    alias(libs.plugins.ksp)
    alias(libs.plugins.jetbrains.compose)
}

android {
    namespace = "com.gabrielbmoro.moviedb.navigation"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.material3)
            implementation(compose.components.resources)

            implementation(libs.bundles.voyager)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.bundles.koin.impl)
            
            implementation(projects.domain)
        }
    }
}