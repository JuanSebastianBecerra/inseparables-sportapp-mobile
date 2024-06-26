package com.example.inseparables_sportapp_mobile.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class RutinaAlimenticia(
    @SerializedName("id")
    var id: String, @SerializedName("nombre")
    var nombre: String, @SerializedName("descripcion")
    var descripcion: String, @SerializedName("productos")
    var productos: ArrayList<Producto>
): Serializable {

}