package com.gabrielbmoro.programmingchallenge

import android.content.Context
import com.gabrielbmoro.programmingchallenge.core.usecaseModule
import com.gabrielbmoro.programmingchallenge.presentation.viewModels.CoroutinesTestRule
import com.gabrielbmoro.programmingchallenge.repository.api.ApiRepositoryImpl
import com.gabrielbmoro.programmingchallenge.repository.dataBase.DataBaseRepositoryImpl
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

open class KoinUnitTest : KoinTest {

    private val fakeRepositoryModule = module {
        single {
            ApiRepositoryImpl(mockk())
        }
        single {
            DataBaseRepositoryImpl(mockk())
        }
    }

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidLogger(Level.ERROR)
        val context = mockk<Context>()
        androidContext(context)
        modules(fakeRepositoryModule, usecaseModule)
    }

}