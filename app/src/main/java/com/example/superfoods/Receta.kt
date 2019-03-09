package com.example.superfoods

class Receta {
    var nombre:String?=null
    var categoria:String?=null
    var ingredientes:String?=null
    var proceso:String?= null

    constructor(){}

    constructor(nombre:String, categoria:String, ingredientes:String, proceso:String){
        this.nombre=nombre
        this.categoria=categoria
        this.ingredientes=ingredientes
        this.proceso=proceso
    }
}