package ext

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import config.EnvKeys.BITRISE_ANDROID_KEYSTORE_ALIAS
import config.EnvKeys.BITRISE_ANDROID_KEYSTORE_PASSWORD
import config.EnvKeys.BITRISE_ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD
import java.io.File

internal fun BaseAppModuleExtension.configureSigning() {
    signingConfigs {
        create("release") {
            keyAlias = System.getenv(BITRISE_ANDROID_KEYSTORE_ALIAS)
            keyPassword = System.getenv(BITRISE_ANDROID_KEYSTORE_PRIVATE_KEY_PASSWORD)

            val path = System.getenv("HOME").plus("/moviedb-keystore")
            storeFile = File(path)
            storePassword = System.getenv(BITRISE_ANDROID_KEYSTORE_PASSWORD)
        }
    }
}

internal fun BaseAppModuleExtension.configureBuildTypes() {
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