package ext

import org.gradle.api.Project
import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.gradle.plugin.use.PluginDependency
import org.gradle.plugin.use.PluginDependencySpec

private fun Project.libs() = project
    .extensions
    .getByType<VersionCatalogsExtension>()
    .named("libs")

fun Project.getLibraryFromCatalogs(alias: String): MinimalExternalModuleDependency = libs()
    .findLibrary(alias)
    .get().get()

fun Project.getVersionFromCatalogs(alias: String): String {
    return libs()
        .findVersion(alias).get().requiredVersion
}

fun Project.getLibrariesFromCatalogs(alias: String): List<MinimalExternalModuleDependency> =
    libs().findBundle(alias).get().get().toList()