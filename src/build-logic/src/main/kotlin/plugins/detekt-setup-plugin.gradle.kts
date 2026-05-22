import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("io.gitlab.arturbosch.detekt")
}

val libs = the<LibrariesForLibs>()

detekt {
    buildUponDefaultConfig = true

    config.setFrom(
        "$rootDir/config/detekt/detekt.yml"
    )
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    exclude("**/build/**")
    exclude("**/generated/**")

    setSource(
        files(
            "src/commonMain/kotlin",
            "src/androidMain/kotlin",
            "src/iosMain/kotlin"
        )
    )
}

dependencies {
    detektPlugins(libs.detekt.formatting)
}
