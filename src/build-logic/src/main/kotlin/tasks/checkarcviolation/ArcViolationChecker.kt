package tasks.checkarcviolation

internal class ArcViolationChecker {
    private val rules = listOf(
        ArcViolationRule.JustWith(
            targetModule = "data",
            justWith = listOf(
                "domain",
            ),
        ),
        ArcViolationRule.NoRelationship(
            targetModule = "domain"
        ),
        ArcViolationRule.NoRelationship(
            targetModule = "resources"
        ),
        ArcViolationRule.JustWith(
            targetModule = "designsystem",
            justWith = listOf("resources")
        ),
        ArcViolationRule.Feature(
            targetModule = "featureModule"
        ),
        ArcViolationRule.NoRelationship(
            targetModule = "platform",
        )
    )

    fun check(targetModule: TargetModule): CheckResult {
        val sortedInternalProjectDependencies = targetModule.internalDependencies.sorted()

        val targetRule = rules.firstOrNull { rule ->
            rule.targetModule == targetModule.moduleName
                    || targetModule.isFeatureModule && rule is ArcViolationRule.Feature
        } ?: return CheckResult.Success

        when (targetRule) {
            is ArcViolationRule.JustWith -> {
                if (sortedInternalProjectDependencies != targetRule.justWith) {
                    return CheckResult.Failure(
                        errorMessage = "${targetModule.moduleName} should only have deps " +
                                "with ${targetRule.justWith}"
                    )
                }
            }

            is ArcViolationRule.NoRelationship -> {
                if (sortedInternalProjectDependencies.isNotEmpty()) {
                    return CheckResult.Failure(
                        errorMessage = "${targetModule.moduleName} should not have deps"
                    )
                }
            }

            is ArcViolationRule.Feature -> {
                if (sortedInternalProjectDependencies.contains("data")) {
                    return CheckResult.Failure(
                        errorMessage = "${targetModule.moduleName} is a feature module and " +
                                "should not have dependency with data"
                    )
                }
            }
        }

        return CheckResult.Success
    }
}