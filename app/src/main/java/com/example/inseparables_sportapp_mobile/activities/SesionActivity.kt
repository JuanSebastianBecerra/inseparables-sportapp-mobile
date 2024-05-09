package com.example.inseparables_sportapp_mobile.activities

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.inseparables_sportapp_mobile.R

class SesionActivity : AppCompatActivity() {

    lateinit var textTiempoCronometro: TextView
    lateinit var editPotencia: EditText
    lateinit var editRitmoMinimo: EditText
    lateinit var editRitmoMaximo: EditText
    lateinit var botonIniciar: Button
    lateinit var botonPausar: Button
    lateinit var botonReiniciar: Button
    lateinit var botonGuardar: Button
    lateinit var cronometro: Chronometer
    var ejecutandose = false
    var pausado = false
    var tiempoPausado: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sesion)
        iniciarComponentes()
        habilitarCronometro()
    }

    fun iniciarComponentes() {
        textTiempoCronometro = findViewById(R.id.textChronometer)
        editPotencia = findViewById(R.id.editPotencia)
        editRitmoMinimo = findViewById(R.id.editRitmoMinimo)
        editRitmoMaximo = findViewById(R.id.editRitmoMaximo)
        botonIniciar = findViewById(R.id.botonIniciar)
        botonPausar = findViewById(R.id.botonPausa)
        botonReiniciar = findViewById(R.id.botonReiniciar)
        botonGuardar = findViewById(R.id.botonGuardarSesion)
        cronometro = findViewById(R.id.cronometro)
    }

    fun habilitarCronometro() {
        botonIniciar.setOnClickListener {
            cronometro.base = SystemClock.elapsedRealtime() + tiempoPausado
            cronometro.start()
            ejecutandose = true
            pausado = false
        }
        botonPausar.setOnClickListener {
            tiempoPausado = cronometro.base - SystemClock.elapsedRealtime()
            cronometro.base = SystemClock.elapsedRealtime() + tiempoPausado
            cronometro.stop()
            ejecutandose = false
            pausado = true
        }
        botonReiniciar.setOnClickListener {
            if (ejecutandose || pausado) {
                cronometro.base = SystemClock.elapsedRealtime()
                cronometro.stop()
                tiempoPausado = 0
                ejecutandose = false
            }
        }
        botonGuardar.setOnClickListener {

        }
    }

}