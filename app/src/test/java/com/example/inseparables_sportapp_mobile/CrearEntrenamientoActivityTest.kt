package com.example.inseparables_sportapp_mobile

import android.widget.DatePicker
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.inseparables_sportapp_mobile.activities.CrearEntrenamientoActivity
import com.example.inseparables_sportapp_mobile.activities.DetalleEventoActivity
import com.example.inseparables_sportapp_mobile.entities.Evento
import com.example.inseparables_sportapp_mobile.entities.Servicio
import com.example.inseparables_sportapp_mobile.entities.Socio
import com.example.inseparables_sportapp_mobile.entities.Ubicacion
import com.google.android.material.timepicker.MaterialTimePicker
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config
import java.util.*


@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class CrearEntrenamientoActivityTest {

    @get:Rule
    var activityCreacionEntrenamientoScenarioTestRule = ActivityScenarioRule(
        CrearEntrenamientoActivity::class.java
    )

    @Test
    fun testActivityNotNull() {
        val activityScenario = ActivityScenario.launch(CrearEntrenamientoActivity::class.java)
        activityScenario.onActivity { activity ->
            Assert.assertNotNull(activity)
        }
    }

    @Test
    fun abrirTimePickerHoraInicio(){
        onView(ViewMatchers.withId(R.id.pick_time_hora_inicio))
            .perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun abrirTimePickerHoraFin(){
        onView(ViewMatchers.withId(R.id.pick_time_hora_fin))
            .perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun seleccionarCancelar(){
        onView(ViewMatchers.withId(R.id.cancelar_button_entrenamiento))
            .perform(ViewActions.click()).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun hacerClickAgendar(){
        val activityScenario = ActivityScenario.launch(CrearEntrenamientoActivity::class.java)
        activityScenario.onActivity { activity ->
            activity.frecuenciaSeleccionada = "DIARIO"
            activity.botonGuardar.performClick()
            Assert.assertNotNull(activity)
        }
    }

}