import com.github.codandotv.popcorn.domain.input.PopcornChildConfiguration
import com.github.codandotv.popcorn.domain.input.ProjectType
import com.github.codandotv.popcorn.domain.rules.DoNotWithRule
import com.github.codandotv.popcorn.domain.rules.JustWithRule
import com.github.codandotv.popcorn.domain.rules.NoDependencyRule

plugins {
    id("io.github.codandotv.popcorngpparent")
}

popcornGuineapigParentConfig {
    type = ProjectType.KMP

    children = listOf(
        PopcornChildConfiguration(
            moduleNameRegex = ":composeApp",
            rules = listOf(
                DoNotWithRule(
                    notWith = listOf(
                        "data",
                        "domain",
                    ),
                ),
            ),
        ),
        PopcornChildConfiguration(
            moduleNameRegex = ":util:[a-z]+",
            rules = listOf(
                NoDependencyRule(),
            ),
        ),
        PopcornChildConfiguration(
            moduleNameRegex = ":feature:feature-[a-z]+",
            rules = listOf(
                DoNotWithRule(
                    notWith = listOf("data"),
                ),
            ),
        ),
        PopcornChildConfiguration(
            moduleNameRegex = ":domain",
            rules = listOf(
                JustWithRule(
                    justWith = listOf(
                        "data",
                    ),
                ),
            ),
        ),
        PopcornChildConfiguration(
            moduleNameRegex = ":data",
            rules = listOf(
                NoDependencyRule(),
            ),
        ),
        PopcornChildConfiguration(
            moduleNameRegex = ":designsystem",
            rules = listOf(
                JustWithRule(
                    justWith = listOf("media"),
                ),
            ),
        ),
    )
}
