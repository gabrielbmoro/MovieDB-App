@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.jetbrains.compose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)

            implementation(libs.bundles.moko)

            implementation(compose.animation)
            implementation(compose.uiUtil)

            implementation(libs.kamel)

            implementation(libs.koin.core)

            implementation(projects.domain)
            implementation(projects.resources)
        }
    }
}
