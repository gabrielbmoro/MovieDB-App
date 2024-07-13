plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.moko.plugin)
}

android {
    namespace = "resources"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.moko.core)
        }
        androidMain.get().dependsOn(commonMain.get())
        iosMain.get().dependsOn(commonMain.get())
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.gabrielbmoro.moviedb" // required
    multiplatformResourcesClassName = "SharedRes" // optional, default MR
}
