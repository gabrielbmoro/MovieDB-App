package tasks.checkarcviolation

sealed class CheckResult {
    object Success : CheckResult()

    object Failure : CheckResult()
}