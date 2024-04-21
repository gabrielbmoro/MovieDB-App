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
        iosMain.get().dependsOn(commonMain.get())
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.gabrielbmoro.moviedb" // required
    multiplatformResourcesClassName = "SharedRes" // optional, default MR
}