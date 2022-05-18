package com.example.itextpdfkotlin

class Usuario {

    lateinit var usuario: String
    lateinit var nombre: String
    lateinit var email: String

    constructor(usuario: String, nombre: String, email: String) {
        this.usuario = usuario
        this.nombre = nombre
        this.email = email
    }
}