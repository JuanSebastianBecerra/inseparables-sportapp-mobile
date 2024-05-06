package com.example.inseparables_sportapp_mobile.ui.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inseparables_sportapp_mobile.R

class EventoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textNombre: TextView = itemView.findViewById(R.id.item_evento_nombre)
    val textDetalle: TextView = itemView.findViewById(R.id.item_evento_detalle)
}