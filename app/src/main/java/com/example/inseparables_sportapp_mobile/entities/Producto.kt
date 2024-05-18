package com.example.inseparables_sportapp_mobile.entities

import com.google.gson.annotations.SerializedName

class Producto(
    @SerializedName("id")
    var id: String, @SerializedName("nombre")
    var nombre: String, @SerializedName("valor")
    var valor: String
) {

}