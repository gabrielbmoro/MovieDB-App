plugins {
    id("kmp-library-plugin")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kermit)
            implementation(libs.koin.core)
        }
    }
}
