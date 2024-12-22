plugins {
    id("util-setup-plugin")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "images"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(libs.kamel)
        }

        androidMain.dependencies {
            implementation(libs.android.youtube.player)
        }
    }
}
