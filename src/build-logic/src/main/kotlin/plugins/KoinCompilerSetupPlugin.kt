package plugins

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.the
import org.koin.compiler.plugin.KoinGradleExtension

// Ref: https://github.com/InsertKoinIO/koin-compiler-plugin/blob/main/docs/CASE_STUDY_NOW_IN_ANDROID.md#the-convention-plugin
class KoinCompilerSetupPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply Koin Compiler Plugin (replaces KSP)
            pluginManager.apply("io.insert-koin.compiler.plugin")

            // Configure logging
            extensions.configure<KoinGradleExtension> {
                userLogs.set(true)
                compileSafety.set(true)
            }

            val libs = the<LibrariesForLibs>()

            dependencies {
                add("commonMainImplementation",libs.koin.core)
                add("commonMainImplementation",libs.koin.annotations)
            }
        }
    }
}
