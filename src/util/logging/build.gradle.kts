plugins {
    id("kmp-library-plugin")
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
