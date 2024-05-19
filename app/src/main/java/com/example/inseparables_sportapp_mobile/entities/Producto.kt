package com.example.inseparables_sportapp_mobile.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Producto(
    @SerializedName("id")
    var id: String, @SerializedName("nombre")
    var nombre: String, @SerializedName("valor")
    var valor: String
) : Serializable {

}