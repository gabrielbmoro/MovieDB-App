@Suppress("DSL_SCOPE_VIOLATION", "ForbiddenComment") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
    alias(libs.plugins.room.plugin)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.bundles.ktor)
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
        }
        commonTest.dependencies {
            implementation(libs.bundles.test.multiplatform)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
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
    add("kspAndroid", libs.room.compiler)

    if (hasProperty("kmp.enableIos")) {
        add("kspIosSimulatorArm64", libs.room.compiler)
        add("kspIosX64", libs.room.compiler)
        add("kspIosArm64", libs.room.compiler)
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}
