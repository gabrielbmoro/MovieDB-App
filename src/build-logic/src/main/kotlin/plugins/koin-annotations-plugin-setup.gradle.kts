import ext.koinAnnotationsDependency
import ext.koinCompiler
import ext.koinCoreDependency
import ext.libs
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile

plugins {
    id("com.google.devtools.ksp")
    id("kotlin-multiplatform")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")

            dependencies {
                api(libs.koinCoreDependency())
                api(libs.koinAnnotationsDependency())
            }
        }
    }
}

dependencies {
    add("kspCommonMainMetadata", libs.koinCompiler())
}

// WORKAROUND: ADD this dependsOn("kspCommonMainKotlinMetadata") instead of above dependencies
tasks.withType<KotlinCompile<*>>().configureEach {
    if (name != "kspCommonMainKotlinMetadata") {
        dependsOn("kspCommonMainKotlinMetadata")
    }
}

afterEvaluate {
    tasks.filter {
        it.name.contains("SourcesJar", true)
    }.forEach {
        println("SourceJarTask====>${it.name}")
        it.dependsOn("kspCommonMainKotlinMetadata")
    }
}

ksp {
    arg("KOIN_CONFIG_CHECK", "true")
}