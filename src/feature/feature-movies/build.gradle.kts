@Suppress("ForbiddenComment") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.koin)

            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.animation)
            implementation(compose.uiUtil)
            implementation(compose.components.resources)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.kotlinx.collections.immutable)
            implementation(libs.navigation.compose)

            implementation(projects.domain)
            implementation(projects.designsystem)
            implementation(projects.platform)
            implementation(projects.util.logging)

            commonTest.dependencies {
                implementation(libs.bundles.test.multiplatform)
                implementation(libs.kotlinx.coroutines.core)
            }
        }
    }
}
