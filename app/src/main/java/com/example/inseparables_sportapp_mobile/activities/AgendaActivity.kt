package com.example.inseparables_sportapp_mobile.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inseparables_sportapp_mobile.R
import com.example.inseparables_sportapp_mobile.comunes.Constants
import com.example.inseparables_sportapp_mobile.comunes.VolleyBroker
import com.example.inseparables_sportapp_mobile.entities.Evento
import com.google.gson.Gson
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class AgendaActivity : AppCompatActivity() {

    lateinit var volleyBroker: VolleyBroker
    lateinit var token: String;
    lateinit var eventos: ArrayList<Evento>
    lateinit var calendarView: MaterialCalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agenda)
        volleyBroker = VolleyBroker(this.applicationContext)
        loadToken()
        llamarServiciosEventos()
        inicarCalendario()
    }

    private fun loadToken() {
        val mPrefs = getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, MODE_PRIVATE)
        token = mPrefs.getString(Constants.TOKEN_KEY, null).toString()
    }

    fun inicarCalendario(){
        calendarView = findViewById(R.id.calendarView)
    }

    @SuppressLint("SimpleDateFormat")
    fun mostrarEventosEnCalendario(){
        for (evento in eventos) {
            try {
                val parser =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val formattedDate = parser.parse(evento.fecha_inicio.replace(" ", "T"))
                calendarView.setDateSelected(formattedDate, true)
            } catch (e : Exception){
                println(e)
            }
        }
    }

    private fun llamarServiciosEventos() {
        val headersParams: MutableMap<String, String> = HashMap()
        headersParams["Authorization"] = "Bearer $token"
        volleyBroker.instance.add(
            VolleyBroker.getRequest(
                "${Constants.BASE_URL_ADMINISTRACION}/eventos-deportista",
                { response ->
                    var jsonRespuestaServicios: JSONObject? = null;
                    try {
                        jsonRespuestaServicios = JSONObject(response)
                    } catch (e: Exception) {
                        Log.e("JSON", "Error: \"$e\"");
                    }
                    val gson = Gson()
                    eventos = ArrayList(
                        gson.fromJson(
                            jsonRespuestaServicios?.getString("respuesta"),
                            Array<Evento>::class.java
                        ).toList()
                    )
                    mostrarEventosEnCalendario()
                },
                {
                    Log.d("TAG", it.toString())
                    Toast.makeText(
                        this, "Error en la consulta",
                        Toast.LENGTH_LONG
                    ).show();
                },
                headersParams as HashMap<String, String>
            )
        )
    }
}

