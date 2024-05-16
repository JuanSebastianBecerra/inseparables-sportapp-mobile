package com.example.inseparables_sportapp_mobile

import android.content.ClipData.Item
import android.view.Gravity
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.DrawerMatchers.isClosed
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.inseparables_sportapp_mobile.activities.*
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
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
            val navTest = activity.findViewById<View>(R.id.inicio)
            navTest.performClick()
        }
    }

    @Test
    fun testClickServicios() {
        val activityScenario = ActivityScenario.launch(InicioActivity::class.java)
        activityScenario.onActivity { activity ->
            assertNotNull(activity)
            val navTest = activity.findViewById<View>(R.id.servicios)
            navTest.performClick()
            assertNotNull(ServiciosActivity::class.java)
        }
    }

    @Test
    fun testClickEntrenamientos() {
        val activityScenario = ActivityScenario.launch(InicioActivity::class.java)
        activityScenario.onActivity { activity ->
            assertNotNull(activity)
            val navTest = activity.findViewById<View>(R.id.entrenamientos)
            navTest.performClick()
            assertNotNull(EntrenamientosActivity::class.java)
        }
    }

    @Test
    fun testClickEventos() {
        val activityScenario = ActivityScenario.launch(InicioActivity::class.java)
        activityScenario.onActivity { activity ->
            assertNotNull(activity)
            val navTest = activity.findViewById<View>(R.id.eventos)
            navTest.performClick()
            assertNotNull(EventosActivity::class.java)
        }
    }

    @Test
    fun testClickSesion() {
        val activityScenario = ActivityScenario.launch(InicioActivity::class.java)
        activityScenario.onActivity { activity ->
            assertNotNull(activity)
            val navTest = activity.findViewById<View>(R.id.sesion)
            navTest.performClick()
            assertNotNull(SesionActivity::class.java)
        }
    }

    @Test
    fun testClickCalendario() {
        val activityScenario = ActivityScenario.launch(InicioActivity::class.java)
        activityScenario.onActivity { activity ->
            assertNotNull(activity)
            val navTest = activity.findViewById<View>(R.id.calendario)
            navTest.performClick()
            assertNotNull(AgendaActivity::class.java)
        }
    }

    @Test
    fun testClickCerrarSesion() {
        val activityScenario = ActivityScenario.launch(InicioActivity::class.java)
        activityScenario.onActivity { activity ->
            assertNotNull(activity)
            activity.findViewById<View>(R.id.cerrar_sesion)
        }
    }


}