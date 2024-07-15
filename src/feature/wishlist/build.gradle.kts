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
            implementation(compose.animation)
            implementation(compose.uiUtil)

            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.bundles.moko)

            implementation(libs.kamel)

            implementation(libs.bundles.voyager)

            implementation(libs.koin.core)

            implementation(projects.domain)
            implementation(projects.designsystem)
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
