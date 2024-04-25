package com.example.inseparables_sportapp_mobile.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inseparables_sportapp_mobile.R
import com.example.inseparables_sportapp_mobile.comunes.Constants
import com.example.inseparables_sportapp_mobile.comunes.VolleyBroker
import com.example.inseparables_sportapp_mobile.entities.Entrenamiento
import com.example.inseparables_sportapp_mobile.ui.adapters.EntrenamientoAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import org.json.JSONObject

class EntrenamientosActivity : AppCompatActivity() {

    lateinit var volleyBroker: VolleyBroker
    lateinit var token: String;
    lateinit var entrenamientos: ArrayList<Entrenamiento>;
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entrenamientos)
        volleyBroker = VolleyBroker(this.applicationContext)
        loadToken()
        llamarServiciosEntrenamientos()
        activarBotonFlotante()
    }

    private fun loadToken() {
        val mPrefs = getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, MODE_PRIVATE)
        token = mPrefs.getString(Constants.TOKEN_KEY, null).toString()
    }

    private fun llamarServiciosEntrenamientos() {
        val headersParams: MutableMap<String, String> = HashMap()
        headersParams["Authorization"] = "Bearer $token"
        volleyBroker.instance.add(
            VolleyBroker.getRequest(
                "${Constants.BASE_URL_DEPORTE}/entrenamientos",
                { response ->
                    var jsonRespuestaServicios: JSONObject? = null;
                    try {
                        jsonRespuestaServicios = JSONObject(response)
                    } catch (e: Exception) {
                        Log.e("JSON", "Error: \"$e\"");
                    }
                    val gson = Gson()
                    entrenamientos = ArrayList(
                        gson.fromJson(
                            jsonRespuestaServicios?.getString("entrenamientos"),
                            Array<Entrenamiento>::class.java
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

    private fun createRecycler(){
        recyclerView = findViewById(R.id.recycler_entrenamientos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EntrenamientoAdapter(entrenamientos)
    }

    private fun activarBotonFlotante(){
        val intentCrearServicio = Intent(this, CrearEntrenamientoActivity::class.java)
        val fab = findViewById<View>(R.id.agregar_entrenamiento) as FloatingActionButton
        fab.setOnClickListener { startActivity(intentCrearServicio) }
    }

    override fun onResume() {
        super.onResume()
        llamarServiciosEntrenamientos()
    }
}