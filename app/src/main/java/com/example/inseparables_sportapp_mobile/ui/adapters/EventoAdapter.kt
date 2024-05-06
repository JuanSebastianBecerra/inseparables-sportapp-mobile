package com.example.inseparables_sportapp_mobile.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inseparables_sportapp_mobile.R
import com.example.inseparables_sportapp_mobile.entities.Entrenamiento
import com.example.inseparables_sportapp_mobile.entities.Evento
import com.example.inseparables_sportapp_mobile.ui.viewholders.EntrenamientoViewHolder
import com.example.inseparables_sportapp_mobile.ui.viewholders.EventoViewHolder

class EventoAdapter(private val eventos: ArrayList<Evento>) : RecyclerView.Adapter<EventoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        return EventoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view_evento, parent, false))
    }

    override fun getItemCount(): Int {
        return eventos.size
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        holder.textNombre.text = eventos[position].nombre
        holder.textDetalle.text = eventos[position].detalle
    }
}