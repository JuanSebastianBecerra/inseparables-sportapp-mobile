package com.example.inseparables_sportapp_mobile

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.inseparables_sportapp_mobile.activities.ServiciosActivity
import com.example.inseparables_sportapp_mobile.activities.SesionActivity
import com.example.inseparables_sportapp_mobile.entities.Servicio
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class SesionActivityTest {

    @Test
    fun testActivityNotNull() {
        val activityScenario = ActivityScenario.launch(SesionActivity::class.java)
        activityScenario.onActivity { activity ->
            Assert.assertNotNull(activity)
        }
    }

    @Test
    fun crearRecyckerServicios() {
        val activityScenario = ActivityScenario.launch(SesionActivity::class.java)
        activityScenario.onActivity { activity ->
            Assert.assertNotNull(activity)
            activity.servicioIniciarSesion()
            activity.servicioTerminarSesion()
        }
    }

    @Test
    fun iniciarCronometro() {
        val activityScenario = ActivityScenario.launch(SesionActivity::class.java)
        activityScenario.onActivity { activity ->
            Assert.assertNotNull(activity)
            activity.botonIniciarTerminar.performClick()
            Assert.assertEquals(activity.ejecutandose, true)
        }
    }

    @Test
    fun terminarCronometro() {
        val activityScenario = ActivityScenario.launch(SesionActivity::class.java)
        activityScenario.onActivity { activity ->
            Assert.assertNotNull(activity)
            activity.ejecutandose = true
            activity.botonIniciarTerminar.performClick()
            Assert.assertEquals(activity.ejecutandose, false)
        }
    }

    @Test
    fun servicioTerminar() {
        val activityScenario = ActivityScenario.launch(SesionActivity::class.java)
        activityScenario.onActivity { activity ->
            Assert.assertNotNull(activity)
            activity.ejecutandose = true
            activity.idSesion = "3d5f00c8-7649-47db-b2e9-2f3b6543909c"
            activity.botonIniciarTerminar.performClick()
            Assert.assertEquals(activity.ejecutandose, false)
        }
    }
}