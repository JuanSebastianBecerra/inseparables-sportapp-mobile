package com.example.inseparables_sportapp_mobile.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.inseparables_sportapp_mobile.R
import com.google.android.material.navigation.NavigationView
import java.util.Locale


class InicioActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var spinnerIdiomas: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        initSideBar()
        iniciarIdiomas()
    }

    private fun initSideBar(){
        val drawerLayoutCollector = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        val navViewCollector = findViewById<View>(R.id.navSidebar) as NavigationView

        toggle = ActionBarDrawerToggle(this, drawerLayoutCollector, R.string.abrir, R.string.cerrar)
        drawerLayoutCollector.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val intentServicios = Intent(this, ServiciosActivity::class.java)
        val intentEntrenamientos = Intent(this, EntrenamientosActivity::class.java)
        val intentEventos = Intent(this, EventosActivity::class.java)
        val intentSesion = Intent(this, SesionActivity::class.java)
        val intentCalendario = Intent(this, AgendaActivity::class.java)
        navViewCollector.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.inicio -> Toast.makeText(this, "test click", Toast.LENGTH_SHORT).show()
                R.id.servicios -> startActivity(intentServicios)
                R.id.entrenamientos -> startActivity(intentEntrenamientos)
                R.id.eventos -> startActivity(intentEventos)
                R.id.sesion -> startActivity(intentSesion)
                R.id.calendario -> startActivity(intentCalendario)
                R.id.cerrar_sesion -> this.finish()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun iniciarIdiomas(){
        spinnerIdiomas = findViewById(R.id.spinnerIdiomas)
        ArrayAdapter.createFromResource(
            this,
            R.array.idiomas,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerIdiomas.adapter = adapter
        }
        spinnerIdiomas.setSelection(0)
        spinnerIdiomas.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val lenguajeSeleccionado = parent?.getItemAtPosition(position)
                println(lenguajeSeleccionado)
                if (lenguajeSeleccionado != null) {
                    if (lenguajeSeleccionado == "Spanish" || lenguajeSeleccionado == "Español"){
                        setLocale(this@InicioActivity, "es")
                        finish()
                        startActivity(intent)
                    }else if (lenguajeSeleccionado == "English" || lenguajeSeleccionado == "Inglés"){
                        setLocale(this@InicioActivity, "en")
                        finish()
                        startActivity(intent)
                    }
                }
            }

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