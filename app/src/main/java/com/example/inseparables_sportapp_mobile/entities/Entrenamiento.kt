package com.example.inseparables_sportapp_mobile.entities

import com.google.gson.annotations.SerializedName

class Entrenamiento(
    @SerializedName("id")
    var id: String, @SerializedName("nombre")
    var nombre: String, @SerializedName("hora_inicio")
    var horaInicio: String, @SerializedName("hora_fin")
    var horaFin: String, @SerializedName("lugar")
    var lugar: String, @SerializedName("frecuencia")
    var frecuencia: String, @SerializedName("detalle")
    var detalle: String, @SerializedName("deporte")
    var deporte: String
) {

}