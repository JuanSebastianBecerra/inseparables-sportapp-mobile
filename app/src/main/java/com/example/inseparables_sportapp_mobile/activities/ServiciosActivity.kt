package com.example.inseparables_sportapp_mobile.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inseparables_sportapp_mobile.R
import com.example.inseparables_sportapp_mobile.comunes.Constants
import com.example.inseparables_sportapp_mobile.comunes.Constants.SHARED_PREFERENCES_KEY
import com.example.inseparables_sportapp_mobile.comunes.Constants.TOKEN_KEY
import com.example.inseparables_sportapp_mobile.comunes.VolleyBroker
import com.example.inseparables_sportapp_mobile.entities.Servicio
import com.example.inseparables_sportapp_mobile.ui.adapters.ServicioAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject
import android.content.Intent


class ServiciosActivity : AppCompatActivity() {
    lateinit var volleyBroker: VolleyBroker
    lateinit var token: String;
    lateinit var servicios: ArrayList<Servicio>;
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        volleyBroker = VolleyBroker(this.applicationContext)
        setContentView(R.layout.activity_servicios)
        loadToken()
        llamarServiciosProductos()
    }

    private fun loadToken() {
        val mPrefs = getSharedPreferences(SHARED_PREFERENCES_KEY, MODE_PRIVATE) //add key
        token = mPrefs.getString(TOKEN_KEY, null).toString()
    }

    private fun llamarServiciosProductos() {
        val headersParams: MutableMap<String, String> = HashMap()
        headersParams["Authorization"] = "Bearer $token"
        volleyBroker.instance.add(
            VolleyBroker.getRequest(
                "${Constants.BASE_URL_ADMINISTRACION}/producto_servicio",
                { response ->
                    var jsonRespuestaServicios: JSONObject? = null;
                    try {
                        jsonRespuestaServicios = JSONObject(response)
                    } catch (e: Exception) {
                        Log.e("JSON", "Error: \"$e\"");
                    }
                    val gson = Gson()
                    servicios = ArrayList(
                        gson.fromJson(
                            jsonRespuestaServicios?.getString("respuesta"),
                            Array<Servicio>::class.java
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
        recyclerView = findViewById(R.id.recycler_servicios)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ServicioAdapter(servicios)
    }

}