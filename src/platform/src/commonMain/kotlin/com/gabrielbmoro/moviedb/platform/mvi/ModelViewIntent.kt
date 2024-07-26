public interface ModelViewIntent<in UserIntent : Any, ScreenState : Any> {
    suspend fun setup(): ScreenState? {
        return null
    }

    @Suppress("TooGenericExceptionThrown")
    suspend fun execute(intent: UserIntent) {
        throw Throwable("Method not implemented")
    }

    fun defaultEmptyState(): ScreenState
}
