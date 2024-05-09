package com.example.inseparables_sportapp_mobile.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.inseparables_sportapp_mobile.R
import com.google.android.material.navigation.NavigationView


class InicioActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        initSideBar()
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
        navViewCollector.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.inicio -> Toast.makeText(this, "test click", Toast.LENGTH_SHORT).show()
                R.id.servicios -> startActivity(intentServicios)
                R.id.entrenamientos -> startActivity(intentEntrenamientos)
                R.id.eventos -> startActivity(intentEventos)
                R.id.sesion -> startActivity(intentSesion)
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
}