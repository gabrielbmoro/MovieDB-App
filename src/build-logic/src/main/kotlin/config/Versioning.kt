package config

internal object Versioning {
    private const val LOCAL_VERSION_CODE = 10
    private const val LOCAL_VERSION_NAME = "1.0.0"
    private const val MAJOR_VERSION = "1.8"

    internal fun versionCode(): Int {
        val versionCode =
            try {
                System.getenv(EnvKeys.BITRISE_BUILD_NUMBER).toIntOrNull()
            } catch (nullPointerException: NullPointerException) {
                null
            }
        return versionCode ?: LOCAL_VERSION_CODE
    }

    internal fun versionName(): String {
        val versionName = try {
            "$MAJOR_VERSION.${System.getenv(EnvKeys.BITRISE_BUILD_NUMBER)}"
        } catch (nullPointerException: NullPointerException) {
            null
        }

        return versionName?.ifEmpty { LOCAL_VERSION_NAME } ?: LOCAL_VERSION_NAME
    }
}