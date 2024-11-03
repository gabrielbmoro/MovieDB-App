import com.github.codandotv.popcorn.domain.input.PopcornConfiguration
import com.github.codandotv.popcorn.domain.input.PopcornProject
import com.github.codandotv.popcorn.domain.input.ProjectType
import com.github.codandotv.popcorn.domain.rules.NoDependencyRule
import gradle.kotlin.dsl.accessors._57fa814fe7c4b1263b4498fcf3d078af.popcornGuineapigConfig

plugins {
    id("kmp-library-plugin")
}

popcornGuineapigConfig {
    configuration = PopcornConfiguration(
        project = PopcornProject(
            type = ProjectType.KMP
        ),
        rules = listOf(
            NoDependencyRule()
        )
    )
}