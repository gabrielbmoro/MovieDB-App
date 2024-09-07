package tasks.checkarcviolation

internal class ArcViolationChecker(
    private val rules: List<ArcViolationRule>
) {
    fun check(targetModuleName: String, internalProjectDependencies: List<String>) {
        rules.firstOrNull { rule -> rule.targetModule == targetModuleName }?.let { targetRule ->
            when (targetRule) {
                is ArcViolationRule.JustWith -> {
                    if (internalProjectDependencies != targetRule.justWith) {
                        throw IllegalStateException("This module has extra dependencies...")
                    }
                }

                is ArcViolationRule.NoRelationship -> {
                    if (internalProjectDependencies.isNotEmpty()) {
                        throw IllegalStateException("This module should not have dependencies...")
                    }
                }
            }
        }
    }
}