package com.gabrielbmoro.programmingchallenge.presentation.components

import timber.log.Timber
import java.util.concurrent.locks.ReentrantLock

class PaginationController {

    private lateinit var serverRequest: ((Int) -> Unit)

    var pagNumber: Int = 1
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

    companion object Builder {
        fun build(serverCallback: ((Int) -> Unit)): PaginationController {
            return PaginationController().apply {
                setup(serverCallback)
            }
        }
    }
}