package com.example.superfoods

import java.util.HashMap

class Producto {
    var nombre:String?=null
    var descripcion:String?=null
    var precio:String?=null
    var contacto:String?=null
    var img:String?=null

    constructor(nombrep:String, descripcion:String, precio:String, contacto:String, img:String){
        this.nombre=nombrep
        this.descripcion=descripcion
        this.precio=precio
        this.contacto=contacto
        this.img=img

    }
    constructor(){}
    fun toMap():Map<String, Any>{
        val result= HashMap<String, Any>()
        result.put("nombre", nombre!!)
        result.put("descripcion", descripcion!!)
        result.put("precio", precio!!)
        result.put("contacto", contacto!!)
        result.put ("img", img!!)
        return result
    }

}