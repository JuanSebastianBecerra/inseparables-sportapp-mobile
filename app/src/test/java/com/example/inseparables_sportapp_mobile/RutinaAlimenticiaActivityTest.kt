package com.example.inseparables_sportapp_mobile

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.example.inseparables_sportapp_mobile.activities.*
import com.example.inseparables_sportapp_mobile.comunes.Constants
import com.example.inseparables_sportapp_mobile.entities.RutinaAlimenticia
import org.junit.After
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import kotlin.coroutines.coroutineContext


@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class RutinaAlimenticiaActivityTest {


    @Test
    fun testActivityNotNull() {
        val activityScenario = ActivityScenario.launch(RutinaAlimenticiaActivity::class.java)
        activityScenario.onActivity { activity ->
            Assert.assertNotNull(activity)
        }
    }


}