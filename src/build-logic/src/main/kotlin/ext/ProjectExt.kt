package ext

import config.ConfigurationKeys
import org.gradle.api.Project

private fun Project.isEligibleForCoverage(): Boolean {
    return ConfigurationKeys.ELIGIBLE_MODULES_FOR_COVERAGE.contains(name)
}

fun Project.configureKover(kover: (Project) -> Unit) {
    rootProject.subprojects.forEach { module ->
        when {
            module.subprojects.isEmpty() -> {
                if (module.isEligibleForCoverage()) {
                    kover(module)
                }
            }

            else -> {
                module.subprojects.filter { subModule ->
                    subModule.isEligibleForCoverage()
                }.forEach(kover)
            }
        }
    }
}