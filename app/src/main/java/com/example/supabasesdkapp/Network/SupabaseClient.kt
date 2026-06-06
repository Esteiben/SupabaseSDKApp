package com.example.supabasesdkapp.Network

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json
import com.example.supabasesdkapp.Models.Alumno
import com.example.supabasesdkapp.Models.Materia

object SupabaseClient {
    private const val SUPABASE_URL = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
    private const val SUPABASE_ANON_KEY = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    val client = createSupabaseClient(
        supabaseUrl = SUPABASE_URL,
        supabaseKey = SUPABASE_ANON_KEY
    ) {
        defaultSerializer = KotlinXSerializer(json)

        install(Postgrest)
    }

    suspend fun obtenerAlumnos(): List<Alumno> {
        return client.postgrest["alumnos"]
            .select()
            .decodeList()
    }

    suspend fun obtenerMaterias(): List<Materia> {
        return client.postgrest["materias"]
            .select()
            .decodeList()
    }
}