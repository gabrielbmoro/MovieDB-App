import com.codingfeline.buildkonfig.compiler.FieldSpec
import java.util.Properties

plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)
    alias(libs.plugins.room.plugin)
    alias(libs.plugins.buildkonfig.plugin)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.koin.core)
            implementation(libs.bundles.ktor)
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)
            implementation(projects.domain)
        }
        commonTest.dependencies {
            implementation(libs.bundles.test.multiplatform)
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.android)
        }
        androidUnitTest.dependencies {
            implementation(libs.bundles.test)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

dependencies {
    add("kspAndroid", libs.room.compiler)

    if (hasProperty("kmp.enableIos")) {
        add("kspIosSimulatorArm64", libs.room.compiler)
        add("kspIosX64", libs.room.compiler)
        add("kspIosArm64", libs.room.compiler)
    }
}

room {
    schemaDirectory("$projectDir/schemas")
}

buildkonfig {
    packageName = "com.gabrielbmoro.moviedb.data"

    val properties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        properties.load(localPropertiesFile.inputStream())
    }

    defaultConfigs {
        val apiToken = properties.getProperty("MOVIE_DB_API_TOKEN")
            ?: findProperty("MOVIE_DB_API_TOKEN") as? String
            ?: System.getenv("MOVIE_DB_API_TOKEN")
        buildConfigField(FieldSpec.Type.STRING, "API_TOKEN", apiToken)
    }
}
