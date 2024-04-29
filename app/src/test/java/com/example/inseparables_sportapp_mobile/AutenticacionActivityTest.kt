package com.example.inseparables_sportapp_mobile

import android.os.Handler
import android.os.Looper
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents.*
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.inseparables_sportapp_mobile.activities.AutenticacionActivity
import org.hamcrest.Matchers.not
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config


@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class AutenticacionActivityTest {


    @get:Rule
    var activityAutenticationScenarioTestRule = ActivityScenarioRule(
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
        onView(withId(R.id.editTextUsusario)).perform(replaceText("sebastian@correo.com"));
        onView(withId(R.id.editTextContrasenia)).perform(replaceText("123456"));
        onView(withId(R.id.buttonIngresar)).perform(click()).check(matches(isDisplayed()))
    }

    @Test
    fun ingresarAplicativoError() {
        onView(withId(R.id.editTextUsusario)).perform(replaceText("sebastn@correo.com"));
        onView(withId(R.id.editTextContrasenia)).perform(replaceText(""))
        onView(withId(R.id.buttonIngresar)).perform(click())
        activityAutenticationScenarioTestRule.scenario.onActivity { activity ->
            Handler(Looper.getMainLooper()).postDelayed({
                onView(withText("Credenciales incorrectas")).inRoot(
                    withDecorView(
                        not(
                            activity.window.decorView
                        )
                    )
                ).check(
                    matches(
                        isDisplayed()
                    )
                )
            }, 5000)
        }
    }

}