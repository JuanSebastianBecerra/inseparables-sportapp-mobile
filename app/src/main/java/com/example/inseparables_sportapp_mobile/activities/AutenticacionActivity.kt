package com.example.inseparables_sportapp_mobile.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inseparables_sportapp_mobile.R
import com.example.inseparables_sportapp_mobile.comunes.Constants.BASE_URL_PERSONAS
import com.example.inseparables_sportapp_mobile.comunes.Constants.ROL_KEY
import com.example.inseparables_sportapp_mobile.comunes.Constants.SHARED_PREFERENCES_KEY
import com.example.inseparables_sportapp_mobile.comunes.Constants.TOKEN_KEY
import com.example.inseparables_sportapp_mobile.comunes.VolleyBroker
import org.json.JSONObject
import java.util.*

class AutenticacionActivity : AppCompatActivity() {
    lateinit var volleyBroker: VolleyBroker
    lateinit var botonIngresar: Button
    lateinit var txtUsuario: TextView
    lateinit var txtContra: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        setLocale(this@AutenticacionActivity, "es")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autenticacion)
        volleyBroker = VolleyBroker(this.applicationContext)
        ingresar()
    }

    private fun ingresar(){
        botonIngresar = findViewById(R.id.buttonIngresar)
        txtUsuario = findViewById(R.id.editTextUsusario)
        txtContra = findViewById(R.id.editTextContrasenia)
        botonIngresar.setOnClickListener {
            val postParams = mapOf<String, Any>(
                "email" to txtUsuario.text.toString(),
                "password" to txtContra.text.toString()
            )

            volleyBroker.instance.add(
                VolleyBroker.postRequest(
                "$BASE_URL_PERSONAS/ingresar", JSONObject(postParams),
                { response ->
                    // Display the first 500 characters of the response string.
                    Toast.makeText(this, "Bienvenido",
                        Toast.LENGTH_LONG).show();
                    val sharedPreferences = applicationContext.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE)
                    sharedPreferences.edit().putString(TOKEN_KEY, response.getString("token")).apply()
                    sharedPreferences.edit().putString(ROL_KEY, response.getString("rol")).apply()
                    val intent = Intent(this, InicioActivity::class.java)
                    startActivity(intent)
                },
                {
                    Log.d("TAG", it.toString())
                    Toast.makeText(this, "Credenciales incorrectas",
                        Toast.LENGTH_LONG).show();
                }
            ))
        }

    }

    private fun setLocale(activity: Activity, idioma: String){
        val local = Locale(idioma)
        Locale.setDefault(local)

        val resources = activity.resources

        val configuration = resources.configuration
        configuration.setLocale(local)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}