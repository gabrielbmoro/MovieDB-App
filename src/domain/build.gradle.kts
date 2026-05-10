plugins {
    id("kmp-library-plugin")
    id("koin-compiler-setup")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.util.logging)
        }
        commonTest.dependencies {
            implementation(libs.bundles.test.multiplatform)
        }
    }
}
