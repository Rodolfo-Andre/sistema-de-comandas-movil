package com.example.project_kotlin.service

import com.example.project_kotlin.entidades.dto.ComandaDTO
import com.example.project_kotlin.entidades.dto.EmpleadoDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServiceComanda {
    @POST("/configuracion/comanda/registrar")
    fun fetchGuardarComanda(@Body bean: ComandaDTO): Call<Void>
}