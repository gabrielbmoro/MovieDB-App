package tasks.checkarcviolation

internal sealed class ArcViolationRule(
    open val targetModule: String
) {
    data class NoRelationship(
        override val targetModule: String
    ) : ArcViolationRule(targetModule)

    data class JustWith(
        override val targetModule: String,
        val justWith: List<String>
    ) : ArcViolationRule(targetModule)
}
