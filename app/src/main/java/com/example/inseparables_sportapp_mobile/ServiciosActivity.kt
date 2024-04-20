package com.example.inseparables_sportapp_mobile

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inseparables_sportapp_mobile.comunes.Constants
import com.example.inseparables_sportapp_mobile.comunes.Constants.SHARED_PREFERENCES_KEY
import com.example.inseparables_sportapp_mobile.comunes.Constants.TOKEN_KEY
import com.example.inseparables_sportapp_mobile.comunes.VolleyBroker
import com.example.inseparables_sportapp_mobile.entities.Servicio
import com.google.gson.Gson
import org.json.JSONObject


class ServiciosActivity : AppCompatActivity() {
    lateinit var volleyBroker: VolleyBroker
    lateinit var token: String;
    lateinit var servicios: ArrayList<Servicio>;
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
                    // Display the first 500 characters of the response string.
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