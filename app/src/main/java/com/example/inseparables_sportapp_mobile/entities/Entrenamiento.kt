package com.example.inseparables_sportapp_mobile.entities

import com.google.gson.annotations.SerializedName

class Entrenamiento {
    @SerializedName("id")
    lateinit var id: String

    @SerializedName("nombre")
    lateinit var nombre: String

    @SerializedName("hora_inicio")
    lateinit var horaInicio: String

    @SerializedName("hora_fin")
    lateinit var horaFin: String

    @SerializedName("lugar")
    lateinit var lugar: String

    @SerializedName("frecuencia")
    lateinit var frecuencia: String

    @SerializedName("detalle")
    lateinit var detalle: String

    @SerializedName("deporte")
    lateinit var deporte: String
}