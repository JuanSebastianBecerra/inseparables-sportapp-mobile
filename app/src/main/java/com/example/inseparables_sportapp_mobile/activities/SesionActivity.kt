package com.example.inseparables_sportapp_mobile.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.inseparables_sportapp_mobile.R
import com.example.inseparables_sportapp_mobile.comunes.Constants
import com.example.inseparables_sportapp_mobile.comunes.VolleyBroker
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
                    Handler(Looper.getMainLooper()).postDelayed({
                        this.finish()
                    }, 2000)
                },
                {
                    Log.d("TAG", it.toString())
                    Toast.makeText(this, "Error al iniciar la sesión",
                        Toast.LENGTH_LONG).show();
                },
                headersParams as HashMap<String, String>
            ))
    }

}