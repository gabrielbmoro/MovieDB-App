import config.ConfigurationKeys
import config.EnvKeys.BITRISE_ANDROID_KEYSTORE_ALIAS
import config.EnvKeys.BITRISE_ANDROID_KEYSTORE_PASSWORD
import config.EnvKeys.BITRISE_ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD
import config.Versioning

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("io.kotzilla.kotzilla-plugin")
    id("kotlin-parcelize")
    kotlin("android")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = ConfigurationKeys.APPLICATION_ID
    compileSdk = ConfigurationKeys.sdkConfiguration.compileSdk

    defaultConfig {
        applicationId = ConfigurationKeys.APPLICATION_ID

        minSdk = ConfigurationKeys.sdkConfiguration.minSdk
        targetSdk = ConfigurationKeys.sdkConfiguration.targetSdk
        versionCode = Versioning.versionCode()
        versionName = Versioning.versionName()
    }

    compileOptions {
        sourceCompatibility = ConfigurationKeys.javaConfiguration.version
        targetCompatibility = ConfigurationKeys.javaConfiguration.version
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    signingConfigs {
        create("release") {
            keyAlias = System.getenv(BITRISE_ANDROID_KEYSTORE_ALIAS)
            keyPassword = System.getenv(BITRISE_ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD)

            val path = System.getenv("HOME").plus("/moviedb-keystore")
            storeFile = File(path)
            storePassword = System.getenv(BITRISE_ANDROID_KEYSTORE_PASSWORD)
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

}

dependencies {
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.androidx.activity.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.coroutines)
    implementation(libs.kotzilla.sdk.compose)
    implementation(libs.navigation.compose)
    implementation(libs.rinku.core)
    implementation(libs.rinku.compose.ext)
    implementation(libs.material3)
    implementation(libs.core.ktx)
    implementation(libs.bundles.compose.multiplatform)

    implementation(projects.composeApp)
    implementation(projects.designsystem)
}