@Suppress("DSL_SCOPE_VIOLATION", "ForbiddenComment") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("kmp-library-plugin")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.data)
            implementation(libs.koin.core)
        }
        commonTest.dependencies {
            implementation(libs.bundles.test.multiplatform)
        }
    }
}
