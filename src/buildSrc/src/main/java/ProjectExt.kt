import org.gradle.api.Project

private const val TOKEN_KEY = "MOVIE_DB_API_TOKEN_DEBUG"
fun Project.debugAPIAuth(): String {
    val localAPIToken = findProperty(TOKEN_KEY)?.toString()
    return localAPIToken ?: System.getenv(TOKEN_KEY)
}