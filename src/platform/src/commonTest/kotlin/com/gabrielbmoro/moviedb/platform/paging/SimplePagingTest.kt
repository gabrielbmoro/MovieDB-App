package com.gabrielbmoro.moviedb.platform.paging

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class SimplePagingTest {

    @Test
    fun `should be able to reset when a paging process is happening`() = runTest {
        // arrange
        val simplePaging = SimplePaging()
        simplePaging.requestNextPage()

        // act
        simplePaging.resetPaging()

        // assert
        assertEquals(0, simplePaging.currentPage.first())
    }

    @Test
    fun `should be able to request next page of elements when request more is called`() = runTest {
        // arrange
        val simplePaging = SimplePaging()

        // act
        simplePaging.requestNextPage()

        // assert
        assertEquals(1, simplePaging.currentPage.first())
    }
}
