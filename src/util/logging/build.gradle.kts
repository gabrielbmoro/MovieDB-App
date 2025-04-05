plugins {
    id("util-setup-plugin")
    id("koin-plugin-setup")
}

android {
    namespace = "logging"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kermit)
        }
    }
}
