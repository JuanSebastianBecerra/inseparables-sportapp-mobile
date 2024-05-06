package com.example.inseparables_sportapp_mobile.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.inseparables_sportapp_mobile.R
import com.example.inseparables_sportapp_mobile.comunes.Constants
import com.example.inseparables_sportapp_mobile.comunes.VolleyBroker
import com.example.inseparables_sportapp_mobile.entities.Evento
import com.example.inseparables_sportapp_mobile.entities.Servicio
import com.google.gson.Gson
import org.json.JSONObject

class DetalleEventoActivity : AppCompatActivity() {

    lateinit var idEvento: String
    lateinit var volleyBroker: VolleyBroker
    lateinit var token: String
    lateinit var evento: Evento
    lateinit var servicios: ArrayList<Servicio>
    lateinit var textTitulo: TextView
    lateinit var textHoraInicio: TextView
    lateinit var textHoraFin: TextView
    lateinit var textOrganizador: TextView
    lateinit var textDescripcion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_evento)
        volleyBroker = VolleyBroker(this.applicationContext)
        idEvento = intent.getStringExtra(Constants.EVENTO_SELECCIONADO_KEY).toString()
        loadToken()
        llamarDetalleEvento()
        llamarServiciosPorEvento()
    }

    fun loadToken() {
        val mPrefs = getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, MODE_PRIVATE) //add key
        token = mPrefs.getString(Constants.TOKEN_KEY, null).toString()
    }

    fun llamarDetalleEvento() {
        val headersParams: MutableMap<String, String> = HashMap()
        headersParams["Authorization"] = "Bearer $token"
        volleyBroker.instance.add(
            VolleyBroker.getRequest(
                "${Constants.BASE_URL_ADMINISTRACION}/eventos/${idEvento}",
                { response ->
                    val gson = Gson()
                    var jsonRespuestaServicios: JSONObject? = null;
                    try {
                        jsonRespuestaServicios = JSONObject(response)
                    } catch (e: Exception) {
                        Log.e("JSON", "Error: \"$e\"");
                    }
                    evento = gson.fromJson(jsonRespuestaServicios?.getString("respuesta"), Evento::class.java)
                    cargarInformacionEvento()
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

    private fun llamarServiciosPorEvento() {
        val headersParams: MutableMap<String, String> = HashMap()
        headersParams["Authorization"] = "Bearer $token"
        volleyBroker.instance.add(
            VolleyBroker.getRequest(
                "${Constants.BASE_URL_ADMINISTRACION}/evento/${idEvento}/servicios",
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
    fun cargarInformacionEvento(){
        textTitulo = findViewById(R.id.textTituloEvento)
        textHoraInicio = findViewById(R.id.textInicioValor)
        textHoraFin = findViewById(R.id.textFinValor)
        textOrganizador = findViewById(R.id.textOrganizadorValor)
        textDescripcion = findViewById(R.id.textDetalleEvento)
        textTitulo.text = evento.nombre
        textHoraInicio.text = evento.fecha_inicio
        textHoraFin.text = evento.fecha_fin
        textDescripcion.text = evento.detalle
        textOrganizador.text = evento.id_socio

    }
}