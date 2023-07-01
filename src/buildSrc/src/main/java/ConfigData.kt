object ConfigData {
    const val APPLICATION_ID = "com.gabrielbmoro.programmingchallenge"
    const val MIN_SDK = 22
    const val TARGET_SDK = 33
    private const val LOCAL_VERSION_CODE = 10
    private const val LOCAL_VERSION_NAME = "1.0.0"

    fun versionCode(): Int {
        val versionCode = try {
            System.getenv("BITRISE_BUILD_NUMBER").toIntOrNull()
        } catch (nullPointerException: NullPointerException) {
            null
        }
        return versionCode ?: LOCAL_VERSION_CODE
    }

    fun versionName(): String {
        val versionName = try {
            "1.6.${System.getenv("BITRISE_BUILD_NUMBER")}"
        } catch (nullPointerException: NullPointerException) {
            null
        }

        return versionName?.ifEmpty { LOCAL_VERSION_NAME } ?: LOCAL_VERSION_NAME
    }
}