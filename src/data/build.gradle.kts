@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.ksp)
    alias(libs.plugins.kover)
    alias(libs.plugins.serialization)
    alias(libs.plugins.ktlint)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.domain)
            implementation(libs.bundles.ktor)
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.bundles.koin.impl)
        }

        commonTest.dependencies {
            implementation(libs.bundles.test.multiplatform)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.bundles.room)
            implementation(libs.koin.android)
        }

        androidUnitTest.dependencies {
            implementation(libs.bundles.test)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

dependencies {
    // Room for Android
    kspAndroid(libs.room.compiler)
}
