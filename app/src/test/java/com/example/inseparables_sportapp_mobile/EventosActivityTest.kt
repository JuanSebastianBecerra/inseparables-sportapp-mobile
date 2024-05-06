package com.example.inseparables_sportapp_mobile

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.inseparables_sportapp_mobile.activities.AutenticacionActivity
import com.example.inseparables_sportapp_mobile.activities.EntrenamientosActivity
import com.example.inseparables_sportapp_mobile.activities.EventosActivity
import com.example.inseparables_sportapp_mobile.entities.Entrenamiento
import com.example.inseparables_sportapp_mobile.entities.Evento
import com.example.inseparables_sportapp_mobile.entities.Ubicacion
import com.google.gson.Gson
import org.json.JSONObject
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class EventosActivityTest {

    @get:Rule
    var activityAutenticationScenarioTestRule = ActivityScenarioRule(
        AutenticacionActivity::class.java
    )

    @BeforeEach
    fun ingresar() {
        onView(ViewMatchers.withId(R.id.editTextUsusario))
            .perform(ViewActions.replaceText("sebastian@correo.com"));
        onView(ViewMatchers.withId(R.id.editTextContrasenia))
            .perform(ViewActions.replaceText("123456"));
        onView(ViewMatchers.withId(R.id.buttonIngresar))
            .perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testActivityNotNull() {
        val activityScenario = ActivityScenario.launch(EventosActivity::class.java)
        activityScenario.onActivity { activity ->
            Assert.assertNotNull(activity)
            activity.eventos = ArrayList()
            activity.createRecycler()

        }
    }


    @Test
    fun testCrearRecyclerEntrenamientos() {
        val activityScenario = ActivityScenario.launch(EventosActivity::class.java)
        activityScenario.onActivity { activity ->
            Assert.assertNotNull(activity)
            activity.eventos = ArrayList()
            val ubicacion = Ubicacion("", "", "", "", "", "", "")
            activity.eventos.add(Evento("", "", "", "", "", "", "", ubicacion))
            activity.createRecycler()

        }
    }

}