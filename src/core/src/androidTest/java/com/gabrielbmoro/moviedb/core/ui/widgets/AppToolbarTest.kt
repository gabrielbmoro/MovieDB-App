package com.gabrielbmoro.moviedb.core.ui.widgets

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import org.junit.Rule
import org.junit.Test

class AppToolbarTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showAppBarTitle() {
        // arrange
        val title = "Jumanji movie"

        // act
        composeTestRule.setContent {
            AppToolbar(
                title = title
            )
        }

        // assert
        composeTestRule
            .onAllNodesWithText(title)
            .assertCountEquals(1)
    }

    @Test
    fun showAppBarNavigationUpIcon() {
        // arrange
        val backEvent = {

        }

        // act
        composeTestRule.setContent {
            AppToolbar(
                title = "any title",
                backEvent = backEvent
            )
        }

        // assert
        composeTestRule
            .onNodeWithContentDescription("Back")
            .assertExists()
    }

    @Test
    fun hideAppBarNavigationUpIcon() {
        // arrange
        val backEvent: (() -> Unit)? = null

        // act
        composeTestRule.setContent {
            AppToolbar(
                title = "any title",
                backEvent = backEvent
            )
        }

        // assert
        composeTestRule
            .onNodeWithContentDescription("Back")
            .assertDoesNotExist()
    }
}