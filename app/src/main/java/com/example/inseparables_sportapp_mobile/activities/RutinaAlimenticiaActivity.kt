package com.example.inseparables_sportapp_mobile.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import com.example.inseparables_sportapp_mobile.R
import com.example.inseparables_sportapp_mobile.comunes.Constants
import com.example.inseparables_sportapp_mobile.comunes.VolleyBroker
import com.example.inseparables_sportapp_mobile.entities.RutinaAlimenticia
import com.google.gson.Gson
import org.json.JSONObject

class RutinaAlimenticiaActivity : AppCompatActivity() {

    lateinit var volleyBroker: VolleyBroker
    lateinit var token: String

    lateinit var textTitleRutina: TextView
    lateinit var listaProductos: ListView
    lateinit var botonGuardar: Button
    lateinit var botonCancelar: Button
    lateinit var rutinaAlimenticia: RutinaAlimenticia

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rutina_alimenticia)
        volleyBroker = VolleyBroker(this.applicationContext)
        loadToken()
        val jsonRutinaData = intent.getStringExtra(Constants.RUTINA_ALIMENTICIA_KEY)
        if (jsonRutinaData != null) {
            rutinaAlimenticia = Gson().fromJson(jsonRutinaData, RutinaAlimenticia::class.java)
        }
        iniciarComponentes()
    }

    private fun loadToken() {
        val mPrefs = getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, MODE_PRIVATE)
        token = mPrefs.getString(Constants.TOKEN_KEY, null).toString()
    }

    fun iniciarComponentes(){
        botonGuardar = findViewById(R.id.guardarRutinaAlimenticia)
        botonCancelar = findViewById(R.id.cancelarRutinaAlimenticia)
        textTitleRutina = findViewById(R.id.textTitleRutinaAlimenticia)
        if (this::rutinaAlimenticia.isInitialized) {
            textTitleRutina.text = rutinaAlimenticia.nombre
        }
        crearAdapterProductosRutina()
        botonGuardar.setOnClickListener {
            solicitarRutinaAlimanticia()
        }
        botonCancelar.setOnClickListener { this.finish() }
    }

    fun crearAdapterProductosRutina(){
        listaProductos = findViewById(R.id.listaAlimentosRutina)
        if (this::rutinaAlimenticia.isInitialized) {
            val serviciosAdapter =
                ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1,
                    rutinaAlimenticia.productos.map { it.nombre })
            listaProductos.adapter = serviciosAdapter
        }
    }

    fun solicitarRutinaAlimanticia(){
        val headersParams: MutableMap<String, String> = HashMap()
        headersParams["Authorization"] = "Bearer $token"
        val postParams = mapOf<String, Any>()
        volleyBroker.instance.add(
            VolleyBroker.postRequestWithHeaders(
                "${Constants.BASE_URL_DEPORTE}/rutina-alimenticia/${rutinaAlimenticia.id}/enviar ",
                JSONObject(postParams),
                {
                    Toast.makeText(this, "Solicitud de productos enviada exitosamente.",
                        Toast.LENGTH_LONG).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        this.finish()
                    }, 2000)
                },
                {
                    Log.d("TAG", it.toString())
                    Toast.makeText(this, "Error al solicitar los productos",
                        Toast.LENGTH_LONG).show();
                },
                headersParams as HashMap<String, String>
            ))
    }
}