@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("kmp-library-plugin")
    id("koin-annotations-plugin-setup")
}

kotlin {
    sourceSets {
        commonTest.dependencies {
            implementation(libs.bundles.test.multiplatform)
        }
    }
}
