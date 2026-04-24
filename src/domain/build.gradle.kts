plugins {
    id("kmp-library-plugin")
    id("koin-annotations-setup-plugin")
}

kotlin {
    sourceSets {
        commonTest.dependencies {
            implementation(libs.bundles.test.multiplatform)
        }
    }
}
