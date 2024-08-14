package ext

import org.gradle.api.artifacts.VersionCatalog

internal fun VersionCatalog.koinCoreDependency() = findLibrary("koin_core").get()

internal fun VersionCatalog.koinAnnotationsDependency() = findLibrary("koin_annotations").get()

internal fun VersionCatalog.koinComposeViewModelDependency() = findLibrary("koin_compose_viewmodel").get()

internal fun VersionCatalog.koinComposeDependency() = findLibrary("koin_compose").get()

internal fun VersionCatalog.koinCoroutines() = findLibrary("koin_coroutines").get()

internal fun VersionCatalog.koinCompiler() = findLibrary("koin_compiler").get()