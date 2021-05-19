package com.gabrielbmoro.programmingchallenge.presentation

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.viewpager.widget.ViewPager
import com.gabrielbmoro.programmingchallenge.R
import com.google.common.truth.Truth.assertThat

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @Test
    fun whenIsTheFirstTabTryToGoToThePreviousTab() {
        // given
        val viewPager = withId(R.id.activity_main_pager_component)

        // when
        val viewInteraction = onView(viewPager).perform(ViewActions.swipeRight())

        // then
        viewInteraction.check { view, _ ->
            (view as? ViewPager)?.let {
                assertThat(it.currentItem).isEqualTo(FIRST_TAB)
            }
        }
    }

    @Test
    fun whenIsTheFirstTabTryToGoToTheNextTab() {
        // given
        val viewPager = withId(R.id.activity_main_pager_component)

        // when
        val viewInteraction = onView(viewPager).perform(ViewActions.swipeLeft())

        // then
        viewInteraction.check { view, _ ->
            (view as? ViewPager)?.let {
                assertThat(it.currentItem).isEqualTo(SECOND_TAB)
            }
        }
    }

    @Test
    fun whenIsTheSecondTabTryToGoToThePreviousTab() {
        // given
        val viewPager = withId(R.id.activity_main_pager_component)

        // when
        val viewInteraction = onView(viewPager)
                .perform(ViewActions.swipeLeft())
                .perform(ViewActions.swipeRight())

        // then
        viewInteraction.check { view, _ ->
            (view as? ViewPager)?.let {
                assertThat(it.currentItem).isEqualTo(FIRST_TAB)
            }
        }
    }

    @Test
    fun whenIsTheSecondTabTryToGoToTheNextTab() {
        // given
        val viewPager = withId(R.id.activity_main_pager_component)

        // when
        val viewInteraction = onView(viewPager)
                .perform(ViewActions.swipeLeft())
                .perform(ViewActions.swipeLeft())


        // then
        viewInteraction.check { view, _ ->
            (view as? ViewPager)?.let {
                assertThat(it.currentItem).isEqualTo(THIRD_TAB)
            }
        }
    }

    @Test
    fun whenIsTheThirdTabTryToGoToThePreviousTab() {
        // given
        val viewPager = withId(R.id.activity_main_pager_component)

        // when
        val viewInteraction = onView(viewPager)
                .perform(ViewActions.swipeLeft())
                .perform(ViewActions.swipeLeft())
                .perform(ViewActions.swipeRight())

        // then
        viewInteraction.check { view, _ ->
            (view as? ViewPager)?.let {
                assertThat(it.currentItem).isEqualTo(SECOND_TAB)
            }
        }
    }

    @Test
    fun whenIsTheThirdTabTryToGoToTheNextTab() {
        // given
        val viewPager = withId(R.id.activity_main_pager_component)

        // when
        val viewInteraction = onView(viewPager)
                .perform(ViewActions.swipeLeft())
                .perform(ViewActions.swipeLeft())
                .perform(ViewActions.swipeLeft())

        // then
        viewInteraction.check { view, _ ->
            (view as? ViewPager)?.let {
                assertThat(it.currentItem).isEqualTo(THIRD_TAB)
            }
        }
    }

    @Test
    fun whenIsTheThirdTabTryToGoToTheFirstTab() {
        // given
        val viewPager = withId(R.id.activity_main_pager_component)

        // when
        val viewInteraction = onView(viewPager)
                .perform(ViewActions.swipeLeft())
                .perform(ViewActions.swipeLeft())
                .perform(ViewActions.swipeLeft())
                .perform(ViewActions.swipeRight())
                .perform(ViewActions.swipeRight())

        // then
        viewInteraction.check { view, _ ->
            (view as? ViewPager)?.let {
                assertThat(it.currentItem).isEqualTo(FIRST_TAB)
            }
        }
    }

    @Test
    fun whenASettingsIsClickedTryToGoToSettings() {
        // given
        openActionBarOverflowOrOptionsMenu(
                InstrumentationRegistry.getInstrumentation().targetContext
        )

        // when
        onView(withText("Settings")).perform(ViewActions.click())
        val viewInteraction2 = onView(withText("Settings"))

        // then
        viewInteraction2.check(matches(isDisplayed()))
    }

    companion object {
        const val FIRST_TAB = 0
        const val SECOND_TAB = 1
        const val THIRD_TAB = 2
    }

}