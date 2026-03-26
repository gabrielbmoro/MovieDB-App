plugins {
    id("kmp-library-plugin")
    id("koin-annotations-setup-plugin")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.data)
        }
        commonTest.dependencies {
            implementation(libs.bundles.test.multiplatform)
        }
    }
}
