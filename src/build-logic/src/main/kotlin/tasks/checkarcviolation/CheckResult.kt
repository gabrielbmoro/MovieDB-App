package tasks.checkarcviolation

sealed class CheckResult {
    object Success : CheckResult()

    data class Failure(val errorMessage: String) : CheckResult()
}