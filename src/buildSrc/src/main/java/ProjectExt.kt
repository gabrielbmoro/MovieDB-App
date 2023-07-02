import org.gradle.api.Project

private fun Project.apiAuth(variableKey: String): String {
    val localAPIToken = findProperty(variableKey)?.toString()
    return localAPIToken ?: System.getenv(variableKey)
}

fun Project.debugAPIAuth() = apiAuth("MOVIE_DB_API_TOKEN_DEBUG")

fun Project.releaseAPIAuth() = apiAuth("MOVIE_DB_API_TOKEN_RELEASE")