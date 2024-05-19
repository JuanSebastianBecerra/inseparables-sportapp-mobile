package com.example.inseparables_sportapp_mobile.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.inseparables_sportapp_mobile.R
import com.example.inseparables_sportapp_mobile.comunes.Constants
import com.example.inseparables_sportapp_mobile.comunes.Constants.SERVICIO_SELECCIONADO_KEY
import com.example.inseparables_sportapp_mobile.comunes.VolleyBroker
import com.example.inseparables_sportapp_mobile.entities.Servicio
import com.google.gson.Gson
import org.json.JSONObject

class DetalleServicioActivity : AppCompatActivity() {

    lateinit var idServicio: String
    lateinit var volleyBroker: VolleyBroker
    lateinit var token: String
    lateinit var servicio: Servicio
    lateinit var textTitulo: TextView
    lateinit var textPrecio: TextView
    lateinit var textDescripcion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_servicio)
        volleyBroker = VolleyBroker(this.applicationContext)
        idServicio = intent.getStringExtra(SERVICIO_SELECCIONADO_KEY).toString()
        loadToken()
        llamarDetalleServicioProducto()
    }

    fun loadToken() {
        val mPrefs = getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, MODE_PRIVATE) //add key
        token = mPrefs.getString(Constants.TOKEN_KEY, null).toString()
    }

    fun llamarDetalleServicioProducto() {
        val headersParams: MutableMap<String, String> = HashMap()
        headersParams["Authorization"] = "Bearer $token"
        volleyBroker.instance.add(
            VolleyBroker.getRequest(
                "${Constants.BASE_URL_ADMINISTRACION}/producto_servicio/${idServicio}",
                { response ->
                    val gson = Gson()
                    var jsonRespuestaServicios: JSONObject? = null;
                    try {
                        jsonRespuestaServicios = JSONObject(response)
                    } catch (e: Exception) {
                        Log.e("JSON", "Error: \"$e\"");
                    }
                    servicio = gson.fromJson(jsonRespuestaServicios?.getString("respuesta"), Servicio::class.java)
                    cargarInformacionServicio()
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

    @SuppressLint("SetTextI18n")
    fun cargarInformacionServicio(){
        textTitulo = findViewById(R.id.textTituloDetalle)
        textPrecio = findViewById(R.id.textPrecioDetalle)
        textDescripcion = findViewById(R.id.textDescripcionDetalle)
        textTitulo.text = servicio.nombre
        textPrecio.text = "$ ${servicio.valor}"
        textDescripcion.text = servicio.descripcion
    }
}