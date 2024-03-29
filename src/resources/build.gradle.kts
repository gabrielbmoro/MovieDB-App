@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.kover)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.moko.plugin)
}

android {
    namespace = "resources"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.bundles.moko)
        }

        androidMain.get().dependsOn(commonMain.get())
    }
}
