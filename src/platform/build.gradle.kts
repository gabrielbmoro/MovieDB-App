plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
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
