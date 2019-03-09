package com.example.superfoods
import com.google.common.base.Strings
import java.util.HashMap
class User {
    var id:String?=null
    var Nombre:String?=null
    var Correo:String?=null
    var recetas:ArrayList<Receta>?= arrayListOf()

    constructor(){}

    constructor(id:String, Nombre:String, Correo:String){
        this.id=id
        this.Nombre=Nombre
        this.Correo=Correo
    }

    fun toMap():Map<String, Any>{
        val result=HashMap<String, Any>()
        result.put("nombre", Nombre!!)
        result.put("correo", Correo!!)
        result.put("recetas", recetas!!)
        return result
    }
}