package ext

import com.android.build.gradle.BaseExtension
import config.ConfigurationKeys
import config.Versioning

internal fun BaseExtension.configureCompileOptions() {
    compileOptions {
        sourceCompatibility = ConfigurationKeys.Java.javaCompatibilityVersion
        targetCompatibility = ConfigurationKeys.Java.javaCompatibilityVersion
    }
}

internal fun BaseExtension.configureTestOptions() {
    testOptions {
        unitTests.isReturnDefaultValues = ConfigurationKeys.HAS_UNIT_TESTS_DEFAULT_VALUES
    }
}

internal fun BaseExtension.configureDefaultConfig() {
    defaultConfig {
        minSdk = ConfigurationKeys.Sdk.MIN_SDK
        targetSdk = ConfigurationKeys.Sdk.TARGET_SDK
        versionCode = Versioning.versionCode()
        versionName = Versioning.versionName()
    }
}