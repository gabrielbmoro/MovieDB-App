package ext

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

private fun Project.libs() =
    project
        .extensions
        .getByType<VersionCatalogsExtension>()
        .named("libs")

fun Project.getVersionFromCatalogs(alias: String): String {
    return libs()
        .findVersion(alias).get().requiredVersion
}
