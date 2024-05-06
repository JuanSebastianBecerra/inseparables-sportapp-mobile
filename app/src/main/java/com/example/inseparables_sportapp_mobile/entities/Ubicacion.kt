package com.example.inseparables_sportapp_mobile.entities

import com.google.gson.annotations.SerializedName

class Ubicacion (
    @SerializedName("id")
    var id: String, @SerializedName("id_evento")
    var id_evento: String, @SerializedName("id_ubicacion")
    var id_ubicacion: String, @SerializedName("direccion")
    var direccion: String, @SerializedName("nombre")
    var nombre: String, @SerializedName("ubicacion_latitud")
    var ubicacion_latitud: String, @SerializedName("ubicacion_longitud")
    var ubicacion_longitud: String
){}