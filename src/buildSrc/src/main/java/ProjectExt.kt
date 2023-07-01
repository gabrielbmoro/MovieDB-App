import org.gradle.api.Project

fun Project.apiAuth(variableKey: String): String {
    val localAPIToken = findProperty(variableKey).toString()
    return localAPIToken.ifEmpty {
        System.getenv(variableKey)
    }
}