package ext

import com.android.build.gradle.BaseExtension
import config.Config

fun BaseExtension.configureCompileOptions() {
    compileOptions {
        sourceCompatibility = Config.javaCompatibilityVersion
        targetCompatibility = Config.javaCompatibilityVersion
    }
}

fun BaseExtension.configureTestOptions() {
    testOptions {
        unitTests.isReturnDefaultValues = Config.HAS_UNIT_TESTS_DEFAULT_VALUES
    }
}

fun BaseExtension.configureDefaultConfig() {
    defaultConfig {
        minSdk = Config.MIN_SDK
        targetSdk = Config.TARGET_SDK
        versionCode = Config.versionCode()
        versionName = Config.versionName()
    }
}