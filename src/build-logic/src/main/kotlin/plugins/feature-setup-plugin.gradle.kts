import com.gabrielbmoro.popcorn.domain.input.PopcornConfiguration
import com.gabrielbmoro.popcorn.domain.input.PopcornProject
import com.gabrielbmoro.popcorn.domain.input.ProjectType
import com.gabrielbmoro.popcorn.domain.rules.DoNotWithRule

plugins {
    id("kmp-library-plugin")
}

popcornGuineapigConfig {
    configuration = PopcornConfiguration(
        project = PopcornProject(
            type = ProjectType.KMP
        ),
        rules = listOf(
            DoNotWithRule(
                notWith = listOf("data")
            )
        )
    )
}