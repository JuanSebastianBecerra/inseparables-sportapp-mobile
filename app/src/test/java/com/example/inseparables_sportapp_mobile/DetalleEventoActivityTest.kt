package com.example.inseparables_sportapp_mobile

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.inseparables_sportapp_mobile.activities.DetalleEventoActivity
import com.example.inseparables_sportapp_mobile.activities.DetalleServicioActivity
import com.example.inseparables_sportapp_mobile.activities.ServiciosActivity
import com.example.inseparables_sportapp_mobile.entities.Evento
import com.example.inseparables_sportapp_mobile.entities.Servicio
import com.example.inseparables_sportapp_mobile.entities.Socio
import com.example.inseparables_sportapp_mobile.entities.Ubicacion
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class DetalleEventoActivityTest {

    @Test
    fun testActivityNotNull() {
        val activityScenario = ActivityScenario.launch(DetalleEventoActivity::class.java)
        activityScenario.onActivity { activity ->
            Assert.assertNotNull(activity)
        }
    }

    @Test
    fun crearRecyclerServicios() {
        val activityScenario = ActivityScenario.launch(DetalleEventoActivity::class.java)
        activityScenario.onActivity { activity ->
            Assert.assertNotNull(activity)
            val ubicacion = Ubicacion("", "", "", "", "", "", "")
            activity.evento = Evento("", "", "", "", "", "", "", ubicacion)
            activity.socio = Socio("", "", "", "", "", "", "", "")
            activity.servicios = ArrayList()
            activity.servicios.add(Servicio("","","","","","",""))
            activity.cargarInformacionEvento()
            activity.llamarDetalleEvento()
            activity.llamarServicioOrganizador()
            activity.llamarServiciosPorEvento()
        }
    }

    @Test
    fun hacerClickAgendar(){
        val activityScenario = ActivityScenario.launch(DetalleEventoActivity::class.java)
        activityScenario.onActivity { activity ->
            Assert.assertNotNull(activity)
            val ubicacion = Ubicacion("", "", "", "", "", "", "")
            activity.evento = Evento("", "", "", "", "", "", "", ubicacion)
            activity.socio = Socio("", "", "", "", "", "", "", "")
            activity.servicios = ArrayList()
            activity.servicios.add(Servicio("","","","","","",""))
            activity.botonAgendar.performClick()
        }
    }
}