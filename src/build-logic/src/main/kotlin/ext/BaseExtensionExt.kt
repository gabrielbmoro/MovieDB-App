package ext

import com.android.build.gradle.BaseExtension
import config.ConfigurationKeys
import config.Versioning

internal fun BaseExtension.configureCompileOptions() {
    compileOptions {
        sourceCompatibility = ConfigurationKeys.javaConfiguration.version
        targetCompatibility = ConfigurationKeys.javaConfiguration.version
    }
}

internal fun BaseExtension.configureTestOptions() {
    testOptions {
        unitTests.isReturnDefaultValues = ConfigurationKeys.HAS_UNIT_TESTS_DEFAULT_VALUES
    }
}

internal fun BaseExtension.configureDefaultConfig() {
    defaultConfig {
        minSdk = ConfigurationKeys.sdkConfiguration.minSdk
        targetSdk = ConfigurationKeys.sdkConfiguration.targetSdk
        versionCode = Versioning.versionCode()
        versionName = Versioning.versionName()
    }
}