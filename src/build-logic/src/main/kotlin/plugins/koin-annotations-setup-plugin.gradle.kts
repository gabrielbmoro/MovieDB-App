plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.google.devtools.ksp")
}

val libs = project.extensions.getByType<VersionCatalogsExtension>()
    .named("libs")

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.findLibrary("koin_core").get())
            api(libs.findLibrary("koin-annotations").get())
        }
    }

    /**
     * KSP generated code for common code is usually not picked up automatically
     * in the IDE or during compilation of other targets in older KMP versions.
     * We manually add the KSP common metadata output to the commonMain source set.
     */
    sourceSets.named("commonMain").configure {
        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
    }
}

/**
 * Register the KSP compiler for common metadata processing.
 * This ensures KSP runs on the common code to generate Koin modules.
 */
dependencies {
    add("kspCommonMainMetadata", libs.findLibrary("koin-ksp-compiler").get())
}

/**
 * WORKAROUND: Explicit Dependency Declaration
 *
 * Because we manually added "build/generated/ksp/..." to the source sets, Gradle's
 * incremental build system needs to know that compilation tasks depend on the
 * KSP task that generates those files. Without this, you get "Implicit Dependency"
 * errors or "Symbol not found" during compilation.
 */
tasks.configureEach {
    val containsKotlinOrKsp = name.contains("Kotlin") ||
        name.contains("ksp", ignoreCase = true)
    // We force all compilation and KSP tasks to wait for the common KSP task.
    if (name != "kspCommonMainKotlinMetadata" && containsKotlinOrKsp) {
        if (name.startsWith("compile") || name.startsWith("ksp")) {
            runCatching {
                dependsOn("kspCommonMainKotlinMetadata")
            }
        }
    }
}

/**
 * Ensures that if a module generates a Sources JAR, it also includes
 * the generated KSP code by making the task depend on the generator.
 */
afterEvaluate {
    tasks.filter {
        it.name.contains("SourcesJar", true)
    }.forEach {
        println("SourceJarTask====>${it.name}")
        runCatching {
            it.dependsOn("kspCommonMainKotlinMetadata")
        }
    }
}

ksp {
    /**
     * Enables compile-time verification of the Koin graph.
     * KSP will check if all dependencies injected via annotations can be satisfied.
     */
    arg("KOIN_CONFIG_CHECK", "true")
}
