plugins {
    id("kmp-library-plugin")
    id("koin-compiler-setup")
}

kotlin {
    sourceSets {
        commonTest.dependencies {
            implementation(libs.bundles.test.multiplatform)
        }
    }
}
