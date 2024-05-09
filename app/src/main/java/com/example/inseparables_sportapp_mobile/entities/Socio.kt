package com.example.inseparables_sportapp_mobile.entities

import com.google.gson.annotations.SerializedName

class Socio(
    @SerializedName("id")
    var id: String, @SerializedName("nombre")
    var nombre: String, @SerializedName("apellido")
    var apellido: String, @SerializedName("email")
    var email: String, @SerializedName("username")
    var username: String, @SerializedName("tipo_identificacion")
    var tipoIdentificacion: String, @SerializedName("numero_identificacion")
    var numeroIdentificacion: String, @SerializedName("detalle")
    var detalle: String
) {

}