import ext.configureKover
import org.gradle.kotlin.dsl.dependencies

plugins {
    id("org.jetbrains.kotlinx.kover")
}

dependencies {
    configureKover { targetModule -> kover(targetModule) }
}

kover {
    reports {
        filters {
            excludes {
                packages(
                    "*.di",
                    "*.datasources",
                    "*.providers",
                    "dagger.hilt.internal.aggregatedroot.codegen",
                    "hilt_aggregated_deps",
                    "*.dto",
                    "*.core.ui.*",
                    "*.widgets",
                    "*.navigation"
                )

                classes(
                    "*.BuildConfig",
                    "*.ComposableSingletons",
                    "*.Hilt_MainActivity",
                    "*_Factory*",
                    "*Activity",
                    "*ScreenKt*",
                    "*_HiltModules*"
                )
                annotatedBy("Generated")
            }
        }
    }
}

tasks.register("coverageReport") {
    dependsOn(":composeApp:koverHtmlReportDebug")
}
