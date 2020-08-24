package com.khiemle.wmovies.presentation


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.khiemle.wmovies.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(HomeActivity::class.java)

    @Before
    fun prepare() {
        mActivityTestRule.launchActivity(null)
    }

    @Test
    fun homeActivityTest() {
        val constraintLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.rvMovies),
                                childAtPosition(
                                        withId(R.id.swipeContainer),
                                        0)),
                        0),
                        isDisplayed()))

        constraintLayout.perform(click())

        val frameLayout = onView(
                allOf(withId(android.R.id.content),
                        childAtPosition(
                                allOf(withId(R.id.decor_content_parent),
                                        childAtPosition(
                                                IsInstanceOf.instanceOf(android.widget.FrameLayout::class.java),
                                                0)),
                                1),
                        isDisplayed()))
        frameLayout.check(matches(isDisplayed()))
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
