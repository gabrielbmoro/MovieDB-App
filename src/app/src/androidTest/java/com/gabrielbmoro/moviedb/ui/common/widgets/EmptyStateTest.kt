package com.gabrielbmoro.moviedb.ui.common.widgets

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class EmptyStateTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showEmptyState() {
        // arrange + act
        composeTestRule.setContent {
            EmptyState()
        }

        // assert
        composeTestRule
            .onNodeWithText("No movie here")
            .assertIsDisplayed()
    }

    @Test
    fun showEmptyStateIcon() {
        // arrange + act
        composeTestRule.setContent {
            EmptyState()
        }

        // assert
        composeTestRule
            .onNodeWithContentDescription("Sad emoji")
            .assertExists()
    }
}