package com.gabrielbmoro.programmingchallenge.presentation.components

import timber.log.Timber
import java.util.concurrent.locks.ReentrantLock

class PaginationController {

    private lateinit var serverRequest: ((Int) -> Unit)

    var pagNumber: Int = FIRST_PAGE_TO_REQUEST
        private set

    private val isLock = ReentrantLock(false)

    fun setup(serverCallback: ((Int) -> Unit)) {
        this.serverRequest = serverCallback
    }

    fun requestMore() {
        if (!isLock.isLocked) {
            isLock.lock()

            serverRequest(pagNumber)
            pagNumber++
        }
    }

    fun resultReceived() {
        try {
            isLock.unlock()
        } catch (illegalMonitorStateException: IllegalMonitorStateException) {
            Timber.e(illegalMonitorStateException)
        }
    }

    fun reset() {
        pagNumber = 1
        if (isLock.isLocked) {
            isLock.unlock()
        }
    }

    companion object Builder {

        private const val FIRST_PAGE_TO_REQUEST = 2

        fun build(serverCallback: ((Int) -> Unit)): PaginationController {
            return PaginationController().apply {
                setup(serverCallback)
            }
        }
    }
}