@Suppress("DSL_SCOPE_VIOLATION",  "ForbiddenComment") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("feature-setup-plugin")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
    id("koin-plugin-setup")
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
            implementation(libs.lifecycle.viewmodel.compose)
            implementation(libs.kotlin.stdlib)
            implementation(libs.kotlinx.collections.immutable)
            implementation(libs.navigation.compose)

            implementation(projects.domain)
            implementation(projects.designsystem)
            implementation(projects.platform)
            implementation(projects.resources)
        }
    }
}
