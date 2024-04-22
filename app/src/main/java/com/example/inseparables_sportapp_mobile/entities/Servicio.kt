package com.example.inseparables_sportapp_mobile.entities

import com.google.gson.annotations.SerializedName

class Servicio {
    @SerializedName("id")
    lateinit var id: String

    @SerializedName("nombre")
    lateinit var nombre: String

    @SerializedName("valor")
    lateinit var valor: String

    @SerializedName("detalle")
    lateinit var detalle: String

    @SerializedName("descripcion")
    lateinit var descripcion: String

    @SerializedName("id_deporte")
    lateinit var idDeporte: String

    @SerializedName("id_socio")
    lateinit var idSocio: String
}