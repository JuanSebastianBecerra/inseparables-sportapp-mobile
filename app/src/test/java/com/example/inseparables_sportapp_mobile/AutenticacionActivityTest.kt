package com.example.inseparables_sportapp_mobile

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.*
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class AutenticacionActivityTest {


    @get:Rule
    var activityTestRule = ActivityScenarioRule(
        AutenticacionActivity::class.java
    )

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertEquals("com.example.inseparables_sportapp_mobile", appContext.packageName)
    }

    @Test
    fun ingresarAplicativo() {
        val targetContext: Context = ApplicationProvider.getApplicationContext()
        onView(withText(targetContext.resources.getString(R.string.ingresar))).perform(click());
    }

}