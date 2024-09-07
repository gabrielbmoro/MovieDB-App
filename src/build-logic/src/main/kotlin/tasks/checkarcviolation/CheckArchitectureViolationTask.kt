package tasks.checkarcviolation

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

open class CheckArchitectureViolationTask : DefaultTask() {

    private val checker = ArcViolationChecker()

    @TaskAction
    fun process() {
        val internalProjectDependencies = getInternalProjectDependencies()

        val targetModule = TargetModule(
            moduleName = project.name,
            isFeatureModule = isFeatureModule(),
            internalDependencies = internalProjectDependencies
        )

        val result = checker.check(targetModule = targetModule)

        if (result is CheckResult.Failure) {
            throw IllegalStateException("Something went wrong")
        }

        println("Check: $targetModule")
    }

    private fun isFeatureModule() = project.parent?.name == FEATURE_PARENT_MODULE_NAME

    private fun getInternalProjectDependencies(): List<String> {
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
        return internalProjectDependencies
    }

    companion object {
        private const val TARGET_GROUP_NAME = "MovieDBApp"
        private const val TARGET_CONFIGURATION_NAME = "commonMainImplementation"
        private const val FEATURE_PARENT_MODULE_NAME = "feature"
    }
}