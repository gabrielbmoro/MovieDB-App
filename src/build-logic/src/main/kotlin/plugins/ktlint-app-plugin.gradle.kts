plugins {
    id("org.jlleitschuh.gradle.ktlint")
}

ktlint {
    filter {
        exclude { entry ->
            entry.file.toString().contains("BuildKonfig")
        }
    }
}

tasks.register("lintCheck") {
    dependsOn("ktlintCheck")
}
