plugins {
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    config.setFrom(file(project.rootDir.path.plus("/config/detekt/detekt.yml")))
    buildUponDefaultConfig = true

    source.from("src")

}