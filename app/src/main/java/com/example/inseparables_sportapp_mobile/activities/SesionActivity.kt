package com.example.inseparables_sportapp_mobile.activities

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.inseparables_sportapp_mobile.R
import com.example.inseparables_sportapp_mobile.comunes.Constants
import com.example.inseparables_sportapp_mobile.comunes.VolleyBroker
import com.example.inseparables_sportapp_mobile.entities.Evento
import com.example.inseparables_sportapp_mobile.entities.RutinaAlimenticia
import com.example.inseparables_sportapp_mobile.entities.Socio
import com.google.gson.Gson
import org.json.JSONObject

class SesionActivity : AppCompatActivity() {

    lateinit var volleyBroker: VolleyBroker
    lateinit var token: String

    lateinit var textTiempoCronometro: TextView
    lateinit var editPotencia: EditText
    lateinit var editRitmoMinimo: EditText
    lateinit var editRitmoMaximo: EditText
    lateinit var botonIniciarTerminar: Button
    lateinit var cronometro: Chronometer
    lateinit var rutinaAlimenticia: RutinaAlimenticia
    var idSesion: String = ""
    var ejecutandose = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sesion)
        volleyBroker = VolleyBroker(this.applicationContext)
        loadToken()
        iniciarComponentes()
        habilitarCronometro()
    }

    private fun loadToken() {
        val mPrefs = getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, MODE_PRIVATE)
        token = mPrefs.getString(Constants.TOKEN_KEY, null).toString()
    }

    fun iniciarComponentes() {
        textTiempoCronometro = findViewById(R.id.textChronometer)
        editPotencia = findViewById(R.id.editPotencia)
        editRitmoMinimo = findViewById(R.id.editRitmoMinimo)
        editRitmoMaximo = findViewById(R.id.editRitmoMaximo)
        botonIniciarTerminar = findViewById(R.id.botonIniciarTerminar)
        cronometro = findViewById(R.id.cronometro)
    }

    fun habilitarCronometro() {
        botonIniciarTerminar.setOnClickListener {
            if (ejecutandose) {
                cronometro.base = SystemClock.elapsedRealtime()
                cronometro.stop()
                ejecutandose = false
                botonIniciarTerminar.text = getString(R.string.iniciar)
                if (idSesion != "") {
                    servicioTerminarSesion()
                }
            } else {
                cronometro.start()
                ejecutandose = true
                botonIniciarTerminar.text = getString(R.string.finalizar)
                servicioIniciarSesion()
                mostrarDialog(getString(R.string.sugerencia_alimenticia_1))
                cronometro.setOnChronometerTickListener {
                    when (it.text) {
                        "00:30" -> mostrarDialog(getString(R.string.sugerencia_alimenticia_2))
                        "01:00" -> mostrarDialog(getString(R.string.sugerencia_alimenticia_3))
                        "01:30" -> mostrarDialog(getString(R.string.sugerencia_alimenticia_4))
                    }
                }
            }
        }
    }

    fun servicioIniciarSesion(){
            val headersParams: MutableMap<String, String> = HashMap()
            headersParams["Authorization"] = "Bearer $token"
            val postParams = mapOf<String, Any>()
            volleyBroker.instance.add(
                VolleyBroker.postRequestWithHeaders(
                    "${Constants.BASE_URL_DEPORTE}/sesion-entrenamiento/iniciar ",
                    JSONObject(postParams),
                    {response ->
                        idSesion = response.getString("id_sesion_entrenamiento")
                        Toast.makeText(this, response.getString("respuesta"),
                            Toast.LENGTH_LONG).show();
                    },
                    {
                        Log.d("TAG", it.toString())
                        Toast.makeText(this, "Error al iniciar la sesión",
                            Toast.LENGTH_LONG).show();
                    },
                    headersParams as HashMap<String, String>
                ))
    }

    fun servicioTerminarSesion(){
        val headersParams: MutableMap<String, String> = HashMap()
        headersParams["Authorization"] = "Bearer $token"
        val postParams = mapOf<String, Any>(
            "id_sesion_entrenamiento" to idSesion,
            "potencia" to editPotencia.text.toString(),
            "min_ritmo" to editRitmoMinimo.text.toString(),
            "max_ritmo" to editRitmoMaximo.text.toString(),
        )
        volleyBroker.instance.add(
            VolleyBroker.postRequestWithHeaders(
                "${Constants.BASE_URL_DEPORTE}/sesion-entrenamiento/finalizar ",
                JSONObject(postParams),
                {response ->
                    Toast.makeText(this, response.getString("respuesta"),
                        Toast.LENGTH_LONG).show();
                    obtenerRutinaAlimenticia()
                },
                {
                    Log.d("TAG", it.toString())
                    Toast.makeText(this, "Error al finalizar la sesión",
                        Toast.LENGTH_LONG).show();
                },
                headersParams as HashMap<String, String>
            ))
    }

    fun obtenerRutinaAlimenticia(){
        val headersParams: MutableMap<String, String> = HashMap()
        headersParams["Authorization"] = "Bearer $token"
        val postParams = mapOf<String, Any>(
            "potencia" to editPotencia.text.toString(),
            "min_ritmo" to editRitmoMinimo.text.toString(),
            "max_ritmo" to editRitmoMaximo.text.toString(),
        )
        volleyBroker.instance.add(
            VolleyBroker.postRequestWithHeaders(
                "${Constants.BASE_URL_DEPORTE}/sesion-entrenamiento/rutina-alimenticia ",
                JSONObject(postParams),
                {response ->
                    val gson = Gson()
                    println(response.toString())
                    rutinaAlimenticia = gson.fromJson(response.getString("respuesta"), RutinaAlimenticia::class.java)
                    if (rutinaAlimenticia.id.isNotEmpty()){
                        val intentRutinaAlimenticia = Intent(this@SesionActivity, RutinaAlimenticiaActivity::class.java)
                        this.finish()
                        intentRutinaAlimenticia.putExtra(Constants.RUTINA_ALIMENTICIA_KEY, rutinaAlimenticia)
                        startActivity(intentRutinaAlimenticia)
                    }
                },
                {
                    Toast.makeText(this, "Error al obtener la rutina alimenticia",
                        Toast.LENGTH_LONG).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        this.finish()
                    }, 2000)
                },
                headersParams as HashMap<String, String>
            ))
    }


    private fun mostrarDialog(textoAMostrar: String = ""){
        val view: View = LayoutInflater.from(this@SesionActivity).inflate(R.layout.alimentacion_dialog, null)
        val botonAceptar: Button = view.findViewById(R.id.botonAceptarConsejo)
        val textSugerencia: TextView = view.findViewById(R.id.textConsejoAlimenticio)
        textSugerencia.text = textoAMostrar
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@SesionActivity)
        builder.setView(view)
        val alertDialog: AlertDialog = builder.create()
        botonAceptar.findViewById<Button>(R.id.botonAceptarConsejo).setOnClickListener {
            alertDialog.dismiss()
        }
        if (alertDialog.window != null){
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        alertDialog.show()
    }

}