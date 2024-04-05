package com.example.inseparables_sportapp_mobile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class AutenticacionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autenticacion)
        abrirInicio()
    }

    private fun abrirInicio(){
        val botonIngresar: Button = findViewById<View>(R.id.buttonIngresar) as Button
        botonIngresar.setOnClickListener {
            val intent = Intent(this, InicioActivity::class.java)
            startActivity(intent)
        }

    }
}