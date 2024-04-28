package com.example.inseparables_sportapp_mobile

import android.view.Gravity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.inseparables_sportapp_mobile.activities.InicioActivity
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class InicioActivityTest {

    @get:Rule
    var activityAutenticacionScenarioTestRule = ActivityScenarioRule(
        InicioActivity::class.java
    )

    @Test
    fun testActivityNotNull() {
        val activityScenario = ActivityScenario.launch(InicioActivity::class.java)
        activityScenario.onActivity { activity ->
            assertNotNull(activity)
        }
    }


}