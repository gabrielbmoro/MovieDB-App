package com.gabrielbmoro.moviedb.details.ui.widgets

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import org.junit.Rule
import org.junit.Test

class FavoriteTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun showFavoriteIcon() {
        // arrange
        val isFavorite = true

        // act
        composeTestRule.setContent {
            Favorite(isFavorite = isFavorite) {
            }
        }

        // assert
        composeTestRule
            .onNodeWithContentDescription("It is Favorite")
            .assertIsDisplayed()
    }

    @Test
    fun showUnFavoriteIcon() {
        // arrange
        val isFavorite = false

        // act
        composeTestRule.setContent {
            Favorite(isFavorite = isFavorite) {
            }
        }

        // assert
        composeTestRule
            .onNodeWithContentDescription("It is not favorite")
            .assertIsDisplayed()
    }
}
