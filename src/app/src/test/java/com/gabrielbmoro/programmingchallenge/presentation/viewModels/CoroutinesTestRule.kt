package com.gabrielbmoro.programmingchallenge.presentation.viewModels

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description


/**
 * To solve this problem: Module with the Main dispatcher had failed to initialize.
 * For tests Dispatchers.setMain from kotlinx-coroutines-test module can be used
 * Just add: @get:Rule var coroutinesTestRule = CoroutinesTestRule()
 *
 * - Reference: https://medium.com/androiddevelopers/easy-coroutines-in-android-viewmodelscope-25bffb605471
 */
@ExperimentalCoroutinesApi
class CoroutinesTestRule constructor(
        private val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher() {;

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}