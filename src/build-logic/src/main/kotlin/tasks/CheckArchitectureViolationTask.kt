package tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

sealed class Rule(
    open val targetModule: String
) {
    data class NoRelationship(
        override val targetModule: String
    ) : Rule(targetModule)

    data class JustWith(
        override val targetModule: String,
        val justWith: List<String>
    ) : Rule(targetModule)
}

open class CheckArchitectureViolationTask : DefaultTask() {

    private val rules = listOf(
        Rule.JustWith(
            targetModule = "data",
            justWith = listOf(
                "domain",
            ),
        ),
        Rule.NoRelationship(
            targetModule = "domain"
        ),
        Rule.NoRelationship(
            targetModule = "resources"
        ),
        Rule.JustWith(
            targetModule = "designsystem",
            justWith = listOf("resources")
        )
    )

    @TaskAction
    fun process() {
        val internalProjectDependencies = mutableListOf<String>()

        project.configurations.onEach { conf ->
            if (conf.name == TARGET_CONFIGURATION_NAME) {
                conf.dependencies.map {
                    if (it.group == TARGET_GROUP_NAME) {
                        internalProjectDependencies.add(it.name)
                    }
                }
            }
        }

        rules.firstOrNull { rule ->
            rule.targetModule == project.name
        }?.let {
            when (it) {
                is Rule.JustWith -> {
                    if (internalProjectDependencies != it.justWith) {
                        throw IllegalStateException("This module has extra dependencies...")
                    }
                }

                is Rule.NoRelationship -> {
                    if (internalProjectDependencies.isNotEmpty()) {
                        throw IllegalStateException("This module should not have dependencies...")
                    }
                }
            }
        }

        val isFeatureModule = project.parent?.name == FEATURE_PARENT_MODULE_NAME

        println(
            "Check: ${project.name}, " +
                    "isFeatureModule $isFeatureModule, " +
                    "deps: ${internalProjectDependencies.toString()}"
        )
    }

    companion object {
        private const val TARGET_GROUP_NAME = "MovieDBApp"
        private const val TARGET_CONFIGURATION_NAME = "commonMainImplementation"
        private const val FEATURE_PARENT_MODULE_NAME = "feature"
    }
}