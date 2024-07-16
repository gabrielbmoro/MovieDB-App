package ext

import org.gradle.api.artifacts.VersionCatalog

internal fun VersionCatalog.koinCoreDependency() = findLibrary("koin_core").get()

internal fun VersionCatalog.koinAnnotationsDependency() = findLibrary("koin_annotations").get()

internal fun VersionCatalog.koinCompiler() = findLibrary("koin_compiler").get()