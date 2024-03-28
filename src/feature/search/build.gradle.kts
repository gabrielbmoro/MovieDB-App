@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.kover)
    alias(libs.plugins.jetbrains.compose)
}

android {
    namespace = "com.gabrielbmoro.moviedb.feature.search"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.animation)
            implementation(compose.uiUtil)

            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.bundles.moko)

            implementation(libs.kamel)

            implementation(libs.bundles.voyager)

            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.bundles.koin.impl)

            implementation(projects.domain)
            implementation(projects.designSystem)
            implementation(projects.platform)
            implementation(projects.resources)
        }

        commonTest.dependencies {
            implementation(libs.bundles.test.multiplatform)

            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.kotlin.stdlib)
        }
    }
}
