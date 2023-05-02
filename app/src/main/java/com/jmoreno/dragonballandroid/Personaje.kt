package com.jmoreno.dragonballandroid

data class Personaje (
    var favorite: Boolean ,
    var name: String,
    var id: String,
    var photo: String,
    var description:String,
    var vidaMaxima: Int = 100,
    var vidaActual: Int = 100
        )
