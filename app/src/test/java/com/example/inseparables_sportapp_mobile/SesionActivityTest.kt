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
}