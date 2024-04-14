package com.example.inseparables_sportapp_mobile

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class InicioActivityTest {

    @Test
    fun testActivityNotNull() {
        val activityScenario = ActivityScenario.launch(InicioActivity::class.java)
        activityScenario.onActivity { activity ->
            assertNotNull(activity)
        }
    }

    @Test
    fun testDrawerToggleNotNull() {
        val activityScenario = ActivityScenario.launch(InicioActivity::class.java)
        activityScenario.onActivity { activity ->
            assertNotNull(activity.toggle)
        }
    }

}