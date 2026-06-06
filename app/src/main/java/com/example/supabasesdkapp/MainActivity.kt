package com.example.supabasesdkapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.supabasesdkapp.Adapters.StudentAdapter
import com.example.supabasesdkapp.Models.Alumno
import com.example.supabasesdkapp.Models.Materia
import com.example.supabasesdkapp.Network.SupabaseClient
import kotlinx.coroutines.launch
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {

    private lateinit var actvNiveles: AutoCompleteTextView
    private lateinit var actvMaterias: AutoCompleteTextView
    private lateinit var btFiltrar: Button
    private lateinit var listView: ListView
    private lateinit var adapter: StudentAdapter

    private var listaAlumnosOriginal = listOf<Alumno>()
    private var listaMaterias = listOf<Materia>()
    private val mapaAlumnoMateria = mutableMapOf<Int, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actvNiveles = findViewById(R.id.actvListaNiveles)
        actvMaterias = findViewById(R.id.actvListaMaterias)
        btFiltrar = findViewById(R.id.btFiltrar)
        listView = findViewById(R.id.litAlumnos)

        adapter = StudentAdapter(emptyList())
        listView.adapter = adapter

        cargarDatos()
    }

    private fun cargarDatos() {
        lifecycleScope.launch {
            try {
                val alumnosDeferred = async { SupabaseClient.obtenerAlumnos() }
                val materiasDeferred = async { SupabaseClient.obtenerMaterias() }
                listaAlumnosOriginal = alumnosDeferred.await()
                listaMaterias = materiasDeferred.await()

                asignarMateriasSimuladas()

                adapter.updateList(listaAlumnosOriginal)

                configurarAutoCompleteNiveles()
                configurarAutoCompleteMateriasInicial()

                btFiltrar.setOnClickListener {
                    aplicarFiltro()
                }

            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Error al cargar: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun asignarMateriasSimuladas() {
        if (listaMaterias.isEmpty()) return
        val idsMaterias = listaMaterias.map { it.id }
        for (alumno in listaAlumnosOriginal) {
            val materiaIndex = (alumno.id % idsMaterias.size)
            val materiaId = idsMaterias[materiaIndex]
            mapaAlumnoMateria[alumno.id] = materiaId
        }
    }

    private fun configurarAutoCompleteNiveles() {
        val niveles = listaMaterias.map { it.nivel }.distinct().sorted()
        val adapterNiveles = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, niveles)
        actvNiveles.setAdapter(adapterNiveles)

        actvNiveles.setOnItemClickListener { _, _, position, _ ->
            val nivelSeleccionado = niveles[position]
            actualizarMateriasPorNivel(nivelSeleccionado)
        }
    }

    private fun configurarAutoCompleteMateriasInicial() {
        val nombresMaterias = listaMaterias.map { "${it.nombre} (Nivel ${it.nivel})" }
        val adapterMaterias = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, nombresMaterias)
        actvMaterias.setAdapter(adapterMaterias)
        actvMaterias.tag = listaMaterias
    }

    private fun actualizarMateriasPorNivel(nivel: Int) {
        val materiasFiltradas = listaMaterias.filter { it.nivel == nivel }
        val nombresMaterias = materiasFiltradas.map { "${it.nombre} (Nivel ${it.nivel})" }
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, nombresMaterias)
        actvMaterias.setAdapter(adapter)
        actvMaterias.setText("")
        actvMaterias.tag = materiasFiltradas
    }

    private fun aplicarFiltro() {
        val materiaSeleccionadaStr = actvMaterias.text.toString()
        if (materiaSeleccionadaStr.isBlank()) {
            adapter.updateList(listaAlumnosOriginal)
            return
        }

        val materiasDisponibles = actvMaterias.tag as? List<Materia> ?: emptyList()
        val materiaSeleccionada = materiasDisponibles.find {
            "${it.nombre} (Nivel ${it.nivel})" == materiaSeleccionadaStr
        }

        if (materiaSeleccionada == null) {
            Toast.makeText(this, "Selecciona una materia válida", Toast.LENGTH_SHORT).show()
            return
        }

        val alumnosFiltrados = listaAlumnosOriginal.filter { alumno ->
            mapaAlumnoMateria[alumno.id] == materiaSeleccionada.id
        }

        adapter.updateList(alumnosFiltrados)
        if (alumnosFiltrados.isEmpty()) {
            Toast.makeText(this, "No hay alumnos asignados a esta materia", Toast.LENGTH_SHORT).show()
        }
    }
}