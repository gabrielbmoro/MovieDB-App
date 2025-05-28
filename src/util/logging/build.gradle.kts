plugins {
    id("kmp-library-plugin")
}

android {
    namespace = "logging"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kermit)
            implementation(libs.koin.core)
        }
    }
}
