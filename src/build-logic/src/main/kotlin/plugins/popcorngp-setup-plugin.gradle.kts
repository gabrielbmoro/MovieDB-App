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
            moduleNameRegex = ":util:[a-z]+",
            skippedRules = null,
            rules = listOf(NoDependencyRule())
        ),
        PopcornChildConfiguration(
            moduleNameRegex = ":feature:[a-z]+",
            skippedRules = null,
            rules = listOf(
                DoNotWithRule(
                    notWith = listOf("data"),
                ),
            )
        ),
        PopcornChildConfiguration(
            moduleNameRegex = ":domain",
            skippedRules = null,
            rules = listOf(NoDependencyRule())
        ),
        PopcornChildConfiguration(
            moduleNameRegex = ":data",
            skippedRules = null,
            rules = listOf(
                JustWithRule(
                    justWith = listOf("domain"),
                ),
            )
        ),
        PopcornChildConfiguration(
            moduleNameRegex = ":designsystem",
            skippedRules = null,
            rules = listOf(
                JustWithRule(
                    justWith = listOf("media"),
                ),
            )
        )
    )
}