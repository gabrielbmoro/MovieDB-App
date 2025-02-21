import com.github.codandotv.popcorn.domain.input.PopcornConfiguration
import com.github.codandotv.popcorn.domain.input.PopcornProject
import com.github.codandotv.popcorn.domain.input.ProjectType
import com.github.codandotv.popcorn.domain.rules.NoDependencyRule

plugins {
    id("kmp-library-plugin")
}

popcornGuineapigConfig {
    configuration = PopcornConfiguration(
        project = PopcornProject(
            type = ProjectType.KMP,
        ),
        rules = listOf(
            NoDependencyRule(),
        ),
    )
}
