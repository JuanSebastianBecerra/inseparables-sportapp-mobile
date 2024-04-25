package com.example.inseparables_sportapp_mobile.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.inseparables_sportapp_mobile.R
import com.example.inseparables_sportapp_mobile.entities.Entrenamiento
import com.example.inseparables_sportapp_mobile.ui.viewholders.EntrenamientoViewHolder

class EntrenamientoAdapter(private val entrenamientos: ArrayList<Entrenamiento>) : RecyclerView.Adapter<EntrenamientoViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntrenamientoViewHolder {
        return EntrenamientoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view_entrenamiento, parent, false))
    }

    override fun getItemCount(): Int {
        return entrenamientos.size
    }

    override fun onBindViewHolder(holder: EntrenamientoViewHolder, position: Int) {
        holder.textNombre.text = entrenamientos[position].nombre
        holder.textLugar.text = entrenamientos[position].lugar
    }
}