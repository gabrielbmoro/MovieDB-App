import com.gabrielbmoro.popcorn.domain.input.PopcornConfiguration
import com.gabrielbmoro.popcorn.domain.input.PopcornProject
import com.gabrielbmoro.popcorn.domain.input.ProjectType
import com.gabrielbmoro.popcorn.domain.rules.JustWithRule

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("kmp-library-plugin")
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.ui)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.animation)
            implementation(compose.uiUtil)

            implementation(libs.bundles.moko)
            implementation(libs.kamel)

            implementation(projects.resources)
        }
    }
}

popcornGuineapigConfig {
    configuration = PopcornConfiguration(
        project = PopcornProject(
            type = ProjectType.KMP
        ),
        rules = listOf(
            JustWithRule(
                justWith = listOf("resources")
            )
        )
    )
}