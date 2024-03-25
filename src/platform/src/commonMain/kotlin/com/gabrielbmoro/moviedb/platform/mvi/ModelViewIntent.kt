internal interface ModelViewIntent<in UserIntent : Any, ScreenState : Any> {
    suspend fun setup(): ScreenState? {
        return null
    }

    suspend fun execute(intent: UserIntent): ScreenState {
        throw Throwable("Method not implemented")
    }

    fun defaultEmptyState(): ScreenState
}