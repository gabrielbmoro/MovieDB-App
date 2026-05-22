plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.ui)
            implementation(libs.compose.foundation)
            implementation(libs.material3)
            implementation(libs.bundles.koin)
            implementation(libs.kermit)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.bundles.compose.multiplatform)
            implementation(libs.navigation.compose)
        }

        androidMain.dependencies {
            implementation(libs.material)
        }

        commonTest.dependencies {
            implementation(libs.bundles.test.multiplatform)
        }
    }
}
