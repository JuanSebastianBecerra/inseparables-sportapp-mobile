package com.example.inseparables_sportapp_mobile.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inseparables_sportapp_mobile.R
import com.example.inseparables_sportapp_mobile.entities.Servicio
import com.example.inseparables_sportapp_mobile.ui.viewholders.ServicioViewHolder

class ServicioAdapter(private val servicios: ArrayList<Servicio>) : RecyclerView.Adapter<ServicioViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicioViewHolder {
        return ServicioViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view_servicio, parent, false))
    }

    override fun getItemCount(): Int {
        return servicios.size
    }

    override fun onBindViewHolder(holder: ServicioViewHolder, position: Int) {
        println(servicios[position].nombre)
        println(servicios[position].descripcion)
        holder.textNombre.text = servicios[position].nombre
        holder.textDescripcion.text = servicios[position].descripcion
    }
}