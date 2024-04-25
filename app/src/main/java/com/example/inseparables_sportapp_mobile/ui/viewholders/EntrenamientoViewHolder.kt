package com.example.inseparables_sportapp_mobile.ui.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inseparables_sportapp_mobile.R

class EntrenamientoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textNombre: TextView = itemView.findViewById(R.id.item_entrenamiento_nombre)
    val textLugar: TextView = itemView.findViewById(R.id.item_entrenamiento_lugar)
}