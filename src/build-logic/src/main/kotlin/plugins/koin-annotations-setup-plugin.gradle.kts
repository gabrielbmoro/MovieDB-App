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

    // KSP Common sourceSet
    sourceSets.named("commonMain").configure {
        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
    }
}

// KSP Tasks
dependencies {
    add("kspCommonMainMetadata", libs.findLibrary("koin-ksp-compiler").get())
}

// WORKAROUND: Ensure all relevant tasks depend on kspCommonMainKotlinMetadata
// as we've manually added its output directory to the source sets.
tasks.configureEach {
    val containsKotlinOrKsp = name.contains("Kotlin") ||
        name.contains("ksp", ignoreCase = true)
    if (name != "kspCommonMainKotlinMetadata" && containsKotlinOrKsp) {
        // We use try-catch or check to avoid issues if the task doesn't exist in a specific module
        if (name.startsWith("compile") || name.startsWith("ksp")) {
            runCatching {
                dependsOn("kspCommonMainKotlinMetadata")
            }
        }
    }
}

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
    arg("KOIN_CONFIG_CHECK", "true")
}
