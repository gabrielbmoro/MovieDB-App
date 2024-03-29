
import config.Config
import ext.debugAPIAuth
import ext.releaseAPIAuth

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.multiplatform")
}

android {
    compileSdk = Config.COMPILE_SDK

    defaultConfig {
        minSdk = Config.MIN_SDK
        targetSdk = Config.TARGET_SDK
        versionCode = Config.versionCode()
        versionName = Config.versionName()

        applicationId = Config.APPLICATION_ID
    }

    compileOptions {
        sourceCompatibility = Config.javaCompatibilityVersion
        targetCompatibility = Config.javaCompatibilityVersion
    }

    testOptions {
        unitTests.isReturnDefaultValues = Config.HAS_UNIT_TESTS_DEFAULT_VALUES
    }

    namespace = Config.APPLICATION_ID
}

android {
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
                "proguard-rules.pro",
            )
            buildConfigField("String", "API_TOKEN", "\"${releaseAPIAuth()}\"")
            signingConfig = signingConfigs.getByName("release")
        }
    }
}

kotlin {
    iosSimulatorArm64()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = Config.JAVA_VM_TARGET
            }
        }
    }
}
