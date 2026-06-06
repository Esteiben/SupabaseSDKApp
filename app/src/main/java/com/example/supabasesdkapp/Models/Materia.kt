package com.example.supabasesdkapp.Models

import kotlinx.serialization.Serializable

@Serializable
data class Materia(
    val id: Int,
    val nombre: String,
    val nivel: Int
)