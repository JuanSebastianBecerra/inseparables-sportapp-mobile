package com.example.inseparables_sportapp_mobile

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.inseparables_sportapp_mobile.activities.*
import com.example.inseparables_sportapp_mobile.entities.Evento
import com.example.inseparables_sportapp_mobile.entities.Ubicacion
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class AgendaActivityTest {

    @Test
    fun testActivityNotNull() {
        val activityScenario = ActivityScenario.launch(AgendaActivity::class.java)
        activityScenario.onActivity { activity ->
            Assert.assertNotNull(activity)
        }
    }


    @Test
    fun testCrearRecyclerEntrenamientos() {
        val activityScenario = ActivityScenario.launch(AgendaActivity::class.java)
        activityScenario.onActivity { activity ->
            Assert.assertNotNull(activity)
            activity.eventos = ArrayList()
            val ubicacion = Ubicacion("", "", "", "", "", "", "")
            activity.eventos.add(Evento("", "", "", "", "", "2024-05-10 22:03:19.882000", "2024-05-18 22:03:19.882000", ubicacion))
            activity.mostrarEventosEnCalendario()

        }
    }

}