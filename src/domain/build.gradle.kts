@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.kover)
}

android {
    namespace = "com.gabrielbmoro.moviedb.domain"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.bundles.koin.impl)
        }

        commonTest.dependencies {
            implementation(libs.bundles.test.multiplatform)
        }
    }
}
