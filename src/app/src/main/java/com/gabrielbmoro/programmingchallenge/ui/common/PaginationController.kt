package com.gabrielbmoro.programmingchallenge.ui.common

import timber.log.Timber
import java.util.concurrent.locks.ReentrantLock

class PaginationController {

    private lateinit var serverRequest: ((Int) -> Unit)

    private var hasNextPage: Boolean = true

    private var pagNumber: Int = FIRST_PAGE_TO_REQUEST

    private val isLock = ReentrantLock(false)

    fun setup(serverCallback: ((Int) -> Unit)) {
        this.serverRequest = serverCallback
    }

    fun requestMore() {
        if (hasNextPage && !isLock.isLocked) {
            isLock.lock()

            serverRequest(pagNumber)
            pagNumber++
        }
    }

    fun resultReceived(hasNextPage: Boolean) {
        try {
            isLock.unlock()
            this.hasNextPage = hasNextPage
        } catch (illegalMonitorStateException: IllegalMonitorStateException) {
            Timber.e(illegalMonitorStateException)
        }
    }

    fun reset() {
        pagNumber = 1
        hasNextPage = true
        if (isLock.isLocked) {
            isLock.unlock()
        }
    }

    companion object Builder {

        private const val FIRST_PAGE_TO_REQUEST = 1

        fun build(serverCallback: ((Int) -> Unit)): PaginationController {
            return PaginationController().apply {
                setup(serverCallback)
            }
        }
    }
}