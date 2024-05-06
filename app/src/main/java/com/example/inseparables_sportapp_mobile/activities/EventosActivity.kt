package com.example.inseparables_sportapp_mobile.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inseparables_sportapp_mobile.R
import com.example.inseparables_sportapp_mobile.comunes.Constants
import com.example.inseparables_sportapp_mobile.comunes.VolleyBroker
import com.example.inseparables_sportapp_mobile.entities.Evento
import com.example.inseparables_sportapp_mobile.ui.adapters.EventoAdapter
import com.google.gson.Gson
import org.json.JSONObject

class EventosActivity : AppCompatActivity() {

    lateinit var volleyBroker: VolleyBroker
    lateinit var token: String;
    lateinit var eventos: ArrayList<Evento>;
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)
        volleyBroker = VolleyBroker(this.applicationContext)
        loadToken()
        llamarServiciosEventos()
    }

    private fun loadToken() {
        val mPrefs = getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, MODE_PRIVATE)
        token = mPrefs.getString(Constants.TOKEN_KEY, null).toString()
    }

    private fun llamarServiciosEventos() {
        val headersParams: MutableMap<String, String> = HashMap()
        headersParams["Authorization"] = "Bearer $token"
        volleyBroker.instance.add(
            VolleyBroker.getRequest(
                "${Constants.BASE_URL_ADMINISTRACION}/eventos",
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
                    createRecycler()
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

    fun createRecycler(){
        recyclerView = findViewById(R.id.recycler_eventos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EventoAdapter(eventos)
    }

}