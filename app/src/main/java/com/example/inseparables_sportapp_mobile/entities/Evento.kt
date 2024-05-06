package com.example.inseparables_sportapp_mobile.entities

import com.google.gson.annotations.SerializedName

class Evento (
    @SerializedName("id")
    var id: String, @SerializedName("id_deporte")
    var id_deporte: String, @SerializedName("id_socio")
    var id_socio: String, @SerializedName("detalle")
    var detalle: String, @SerializedName("nombre")
    var nombre: String, @SerializedName("fecha_inicio")
    var fecha_inicio: String, @SerializedName("fecha_fin")
    var fecha_fin: String, @SerializedName("ubicacion")
    var ubicacion: Ubicacion
){}