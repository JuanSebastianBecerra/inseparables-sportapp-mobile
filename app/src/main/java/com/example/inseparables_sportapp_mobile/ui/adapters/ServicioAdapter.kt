package com.example.inseparables_sportapp_mobile.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.inseparables_sportapp_mobile.R
import com.example.inseparables_sportapp_mobile.activities.DetalleServicioActivity
import com.example.inseparables_sportapp_mobile.comunes.Constants.SERVICIO_SELECCIONADO_KEY
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
        holder.textNombre.text = servicios[position].nombre
        holder.textDescripcion.text = servicios[position].descripcion
        holder.itemView.setOnClickListener{view ->
            val intent = Intent(view.context, DetalleServicioActivity::class.java)
            intent.putExtra(SERVICIO_SELECCIONADO_KEY, servicios[position].id)
            view.context.startActivity(intent)
        }
    }
}