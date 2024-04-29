package com.example.inseparables_sportapp_mobile.entities

import com.google.gson.annotations.SerializedName

class Servicio(
    @SerializedName("id")
    var id: String, @SerializedName("nombre")
    var nombre: String, @SerializedName("valor")
    var valor: String, @SerializedName("detalle")
    var detalle: String, @SerializedName("descripcion")
    var descripcion: String, @SerializedName("id_deporte")
    var idDeporte: String, @SerializedName("id_socio")
    var idSocio: String
) {

}