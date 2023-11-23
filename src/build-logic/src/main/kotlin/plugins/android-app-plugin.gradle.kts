import config.Config
import ext.debugAPIAuth
import ext.getVersionFromCatalogs
import ext.releaseAPIAuth

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode()
        versionName = Config.versionName()

        testInstrumentationRunner = Config.testInstrumentationRunner
        vectorDrawables.useSupportLibrary = true

        applicationId = Config.applicationId

        vectorDrawables.useSupportLibrary = true

        multiDexEnabled = true
    }

    testOptions {
        unitTests.isReturnDefaultValues = true
    }

    compileOptions {
        sourceCompatibility = Config.javaCompatibilityVersion
        targetCompatibility = Config.javaCompatibilityVersion
    }

    composeOptions {
        kotlinCompilerExtensionVersion = getVersionFromCatalogs("compose.compiler")
    }
    buildFeatures.compose = true

    namespace = Config.applicationId

    kotlinOptions {
        jvmTarget = Config.javaVMTarget
    }

    signingConfigs {
        create("release") {
            keyAlias = System.getenv("BITRISEIO_ANDROID_KEYSTORE_ALIAS")
            keyPassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD")

            storeFile = file(System.getenv("HOME").plus("/moviedb-keystore"))
            storePassword = System.getenv("BITRISEIO_ANDROID_KEYSTORE_PASSWORD")
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "API_TOKEN", "\"${debugAPIAuth()}\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_TOKEN", "\"${releaseAPIAuth()}\"")
            signingConfig = signingConfigs.getByName("release")
        }
    }
}