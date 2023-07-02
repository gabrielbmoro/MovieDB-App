import org.gradle.api.Project


fun Project.debugAPIAuth() = findProperty("MOVIE_DB_API_TOKEN_DEBUG")
    ?: System.getenv("MOVIE_DB_API_TOKEN_DEBUG")

fun Project.releaseAPIAuth() = findProperty("MOVIE_DB_API_TOKEN_RELEASE")
    ?: System.getenv("MOVIE_DB_API_TOKEN_RELEASE")