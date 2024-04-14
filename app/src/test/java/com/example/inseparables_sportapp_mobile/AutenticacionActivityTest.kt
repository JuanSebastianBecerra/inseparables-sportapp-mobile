package com.example.inseparables_sportapp_mobile

import android.content.Intent
import android.os.StrictMode
import android.widget.Button
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(manifest = Config.NONE)
class AutenticacionActivityTest {

    @Test
    fun clickingButton_opensInicioActivity() {
        // Se inicia la actividad bajo prueba
        val scenario = ActivityScenario.launch(AutenticacionActivity::class.java)

        // Esperar a que la actividad se complete
        scenario.onActivity { activity ->
            // Simular el clic en el botón
            activity.findViewById<Button>(R.id.buttonIngresar).performClick()

            // Verificar que se inició la actividad InicioActivity
            val expectedIntent = Intent(activity, InicioActivity::class.java)
            val actualIntent = shadowOf(activity).nextStartedActivity
            assertThat(actualIntent.toString()).isEqualTo(expectedIntent.toString())
        }
    }

}