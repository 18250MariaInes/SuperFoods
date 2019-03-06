package com.example.superfoods
import java.util.HashMap
class User {
    var id:String?=null
    var Nombre:String?=null
    var Correo:String?=null

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
        return result
    }
}