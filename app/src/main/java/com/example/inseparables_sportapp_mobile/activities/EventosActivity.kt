package com.example.inseparables_sportapp_mobile.activities

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    lateinit var eventosProximidad: ArrayList<Evento>;
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerViewProximidad: RecyclerView
    lateinit var botonProximidad: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eventos)
        volleyBroker = VolleyBroker(this.applicationContext)
        loadToken()
        llamarServiciosEventos()
        activarEventosProximidad()
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

    private fun llamarServiciosEventosProximidad(view: View) {
        val headersParams: MutableMap<String, String> = HashMap()
        headersParams["Authorization"] = "Bearer $token"
        volleyBroker.instance.add(
            VolleyBroker.getRequest(
                "${Constants.BASE_URL_PERSONAS}/persona/direccion",
                { response ->
                    var jsonRespuestaDireccion: JSONObject? = null;
                    try {
                        jsonRespuestaDireccion = JSONObject(response)
                    } catch (e: Exception) {
                        Log.e("JSON", "Error: \"$e\"");
                    }
                    val gson = Gson()
                    println("${Constants.BASE_URL_ADMINISTRACION}/eventos-cercanos?latitud=${jsonRespuestaDireccion?.getString("ubicacion_latitud")}&longitud=${jsonRespuestaDireccion?.getString("ubicacion_longitud")}")
                    volleyBroker.instance.add(
                        VolleyBroker.getRequest(
                            "${Constants.BASE_URL_ADMINISTRACION}/eventos-cercanos?latitud=${jsonRespuestaDireccion?.getString("ubicacion_latitud")}&longitud=${jsonRespuestaDireccion?.getString("ubicacion_longitud")}",
                            { responseProximidad ->
                                var jsonRespuestaServicios: JSONObject? = null;
                                try {
                                    jsonRespuestaServicios = JSONObject(responseProximidad)
                                } catch (e: Exception) {
                                    Log.e("JSON", "Error: \"$e\"");
                                }
                                eventosProximidad = ArrayList(
                                    gson.fromJson(
                                        jsonRespuestaServicios?.getString("respuesta"),
                                        Array<Evento>::class.java
                                    ).toList()
                                )
                                createRecyclerProximidad(view)
                            },
                            {
                                Log.d("TAG", it.toString())
                                Toast.makeText(
                                    this, "Error en la consulta de eventos por proximidad",
                                    Toast.LENGTH_LONG
                                ).show();
                            },
                            headersParams as HashMap<String, String>
                        )
                    )
                },
                {
                    Log.d("TAG", it.toString())
                    Toast.makeText(
                        this, "Error en la consulta de direccion",
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

    fun createRecyclerProximidad(view: View){
        recyclerViewProximidad = view.findViewById(R.id.recycler_eventos_proximidad)
        recyclerViewProximidad.layoutManager = LinearLayoutManager(view.context)
        recyclerViewProximidad.adapter = EventoAdapter(eventosProximidad)
    }

    fun activarEventosProximidad(){
        botonProximidad = findViewById(R.id.botonProximidad)
        botonProximidad.setOnClickListener {
            mostrarDialog()
        }
    }

    private fun mostrarDialog(){
        val view: View = LayoutInflater.from(this@EventosActivity).inflate(R.layout.proximidad_dialog, null)
        val botonAceptar: Button = view.findViewById(R.id.botonProximidadDialogOk)
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@EventosActivity)
        builder.setView(view)
        val alertDialog: AlertDialog = builder.create()
        botonAceptar.findViewById<Button>(R.id.botonProximidadDialogOk).setOnClickListener {
            alertDialog.dismiss()
        }
        if (alertDialog.window != null){
            alertDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        }
        llamarServiciosEventosProximidad(view)
        alertDialog.show()
    }

}