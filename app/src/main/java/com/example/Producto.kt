package com.example.superfoods

import java.util.HashMap

class Producto {
    var nombre:String?=null
    var descripcion:String?=null
    var precio:String?=null
    var contacto:String?=null

    constructor(nombrep:String, descripcion:String, precio:String, contacto:String){
        this.nombre=nombrep
        this.descripcion=descripcion
        this.precio=precio
        this.contacto=contacto

    }
    constructor(){}
    fun toMap():Map<String, Any>{
        val result= HashMap<String, Any>()
        result.put("nombre", nombre!!)
        result.put("descripcion", descripcion!!)
        result.put("precio", precio!!)
        result.put("contacto", contacto!!)
        return result
    }

}