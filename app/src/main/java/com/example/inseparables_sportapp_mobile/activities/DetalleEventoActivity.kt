package com.example.inseparables_sportapp_mobile.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.inseparables_sportapp_mobile.R
import com.example.inseparables_sportapp_mobile.comunes.Constants
import com.example.inseparables_sportapp_mobile.comunes.VolleyBroker
import com.example.inseparables_sportapp_mobile.entities.Evento
import com.example.inseparables_sportapp_mobile.entities.Servicio
import com.example.inseparables_sportapp_mobile.entities.Socio
import com.google.gson.Gson
import org.json.JSONObject

class DetalleEventoActivity : AppCompatActivity() {

    lateinit var idEvento: String
    lateinit var volleyBroker: VolleyBroker
    lateinit var token: String
    lateinit var evento: Evento
    lateinit var socio: Socio
    lateinit var servicios: ArrayList<Servicio>
    lateinit var textTitulo: TextView
    lateinit var textHoraInicio: TextView
    lateinit var textHoraFin: TextView
    lateinit var textOrganizador: TextView
    lateinit var textDescripcion: TextView
    lateinit var listServiciosEvento: ListView
    lateinit var botonAgendar: Button
    lateinit var eventosDeportista: ArrayList<Evento>
    var estaAsignado: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_evento)
        volleyBroker = VolleyBroker(this.applicationContext)
        idEvento = intent.getStringExtra(Constants.EVENTO_SELECCIONADO_KEY).toString()
        loadToken()
        llamarDetalleEvento()
        llamarServiciosPorEvento()
        asignarEliminarEventoAUsuario()
        llamarServiciosEventosDeportista()
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
                    var jsonRespuestaServicios: JSONObject? = null
                    try {
                        jsonRespuestaServicios = JSONObject(response)
                    } catch (e: Exception) {
                        Log.e("JSON", "Error: \"$e\"")
                    }
                    evento = gson.fromJson(jsonRespuestaServicios?.getString("respuesta"), Evento::class.java)
                    llamarServicioOrganizador()
                },
                {
                    Log.d("TAG", it.toString())
                    Toast.makeText(
                        this, "Error en la consulta",
                        Toast.LENGTH_LONG
                    ).show()
                },
                headersParams as HashMap<String, String>
            )
        )
    }

    private fun llamarServicioOrganizador() {
        val headersParams: MutableMap<String, String> = HashMap()
        headersParams["Authorization"] = "Bearer $token"
        volleyBroker.instance.add(
            VolleyBroker.getRequest(
                "${Constants.BASE_URL_ADMINISTRACION}/socios/${evento.id_socio}",
                { response ->
                    var jsonRespuestaServicios: JSONObject? = null
                    try {
                        jsonRespuestaServicios = JSONObject(response)
                    } catch (e: Exception) {
                        Log.e("JSON", "Error: \"$e\"")
                    }
                    val gson = Gson()
                    socio = gson.fromJson(jsonRespuestaServicios?.getString("respuesta"), Socio::class.java)
                    cargarInformacionEvento()
                },
                {
                    Log.d("TAG", it.toString())
                    Toast.makeText(
                        this, "Error en la consulta",
                        Toast.LENGTH_LONG
                    ).show()
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
                    var jsonRespuestaServicios: JSONObject? = null
                    try {
                        jsonRespuestaServicios = JSONObject(response)
                    } catch (e: Exception) {
                        Log.e("JSON", "Error: \"$e\"")
                    }
                    val gson = Gson()
                    servicios = ArrayList(
                        gson.fromJson(
                            jsonRespuestaServicios?.getString("respuesta"),
                            Array<Servicio>::class.java
                        ).toList()
                    )
                    crearAdapterServiciosEvento()
                },
                {
                    Log.d("TAG", it.toString())
                    Toast.makeText(
                        this, "Error en la consulta",
                        Toast.LENGTH_LONG
                    ).show()
                },
                headersParams as HashMap<String, String>
            )
        )
    }

    private fun asignarEliminarEventoAUsuario() {
        botonAgendar = findViewById(R.id.botonAgendarEvento)
        botonAgendar.setOnClickListener {
            val headersParams: MutableMap<String, String> = HashMap()
            headersParams["Authorization"] = "Bearer $token"
            val postParams = mapOf<String, Any>()
            volleyBroker.instance.add(
                if (!estaAsignado) VolleyBroker.postRequestWithHeaders(
                    "${Constants.BASE_URL_ADMINISTRACION}/deportista/evento/${idEvento}",
                    JSONObject(postParams),
                    {response ->
                        Toast.makeText(this, response.getString("respuesta"),
                            Toast.LENGTH_LONG).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            this.finish()
                        }, 2000)
                    },
                    {
                        Log.d("TAG", it.toString())
                        Toast.makeText(this, "Error al registrar el entrenamiento",
                            Toast.LENGTH_LONG).show()
                    },
                    headersParams as HashMap<String, String>
                ) else VolleyBroker.deleteRequestWithHeaders(
                    "${Constants.BASE_URL_ADMINISTRACION}/deportista/evento/${idEvento}",
                    JSONObject(postParams),
                    {response ->
                        Toast.makeText(this, "Evento eliminado correctamente de la agenda del deportista",
                            Toast.LENGTH_LONG).show()
                        Handler(Looper.getMainLooper()).postDelayed({
                            this.finish()
                        }, 2000)
                    },
                    {
                        Log.d("TAG", it.toString())
                        Toast.makeText(this, "Error al eliminar el entrenamiento",
                            Toast.LENGTH_LONG).show()
                    },
                    headersParams as HashMap<String, String>
                ) )
        }

    }

    fun llamarServiciosEventosDeportista() {
        val headersParams: MutableMap<String, String> = java.util.HashMap()
        headersParams["Authorization"] = "Bearer $token"
        volleyBroker.instance.add(
            VolleyBroker.getRequest(
                "${Constants.BASE_URL_ADMINISTRACION}/eventos-deportista",
                { response ->
                    var jsonRespuestaServicios: JSONObject? = null
                    try {
                        jsonRespuestaServicios = JSONObject(response)
                    } catch (e: Exception) {
                        Log.e("JSON", "Error: \"$e\"")
                    }
                    val gson = Gson()
                    eventosDeportista = java.util.ArrayList(
                        gson.fromJson(
                            jsonRespuestaServicios?.getString("respuesta"),
                            Array<Evento>::class.java
                        ).toList()
                    )
                    validarEventoAsignado()
                },
                {
                    Log.d("TAG", it.toString())
                    Toast.makeText(
                        this, "Error en la consulta",
                        Toast.LENGTH_LONG
                    ).show()
                },
                headersParams as java.util.HashMap<String, String>
            )
        )
    }

    fun validarEventoAsignado(){
        estaAsignado = eventosDeportista.any{ evento -> evento.id == idEvento }
        botonAgendar.text = if (estaAsignado) getString(R.string.eliminar_evento) else getString(R.string.agendar)
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
        textOrganizador.text = socio.nombre + " " + socio.apellido
    }

    fun crearAdapterServiciosEvento(){
        listServiciosEvento = findViewById(R.id.lista_servicios_evento)
        val serviciosAdapter = ArrayAdapter(applicationContext,android.R.layout.simple_list_item_1,servicios.map { it.nombre })
        listServiciosEvento.adapter = serviciosAdapter
    }
}