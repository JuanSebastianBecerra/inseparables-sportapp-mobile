package com.example.inseparables_sportapp_mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import comunes.VolleyBroker
import org.json.JSONObject


class AutenticacionActivity : AppCompatActivity() {
    lateinit var volleyBroker: VolleyBroker
    lateinit var botonIngresar: Button
    lateinit var txtUsuario: TextView
    lateinit var txtContra: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
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

            volleyBroker.instance.add(VolleyBroker.postRequest("personas/ingresar", JSONObject(postParams),
                { response ->
                    // Display the first 500 characters of the response string.
                    Toast.makeText(this, response.toString(),
                        Toast.LENGTH_LONG).show();
                    val intent = Intent(this, InicioActivity::class.java)
                    startActivity(intent)
                },
                {
                    Log.d("TAG", it.toString())
                    Toast.makeText(this, "No funciono",
                        Toast.LENGTH_LONG).show();
                }
            ))
        }

    }
}