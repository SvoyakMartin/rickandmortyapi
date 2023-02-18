package ru.svoyakmartin.featureLocation.ui.fragment


import androidx.fragment.app.testing.launchFragmentInContainer
import ru.svoyakmartin.featureLocation.R
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocationListFragmentTest {

    @Before
    fun setUp() {
        launchFragmentInContainer<LocationListFragment>()
    }


    @Test
    fun testScroll() {
        onView(withId(R.id.locations_recycler_view))
            .check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10)
            )
    }
}