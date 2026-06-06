package com.example.supabasesdkapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.supabasesdkapp.R
import com.example.supabasesdkapp.Models.Alumno
import com.example.supabasesdkapp.Network.SupabaseClient

class StudentAdapter(private var students: List<Alumno>) : BaseAdapter() {

    fun updateList(newList: List<Alumno>) {
        students = newList
        notifyDataSetChanged()
    }

    override fun getCount(): Int = students.size
    override fun getItem(position: Int): Any = students[position]
    override fun getItemId(position: Int): Long = students[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)

        val student = students[position]

        val imgAlumno = view.findViewById<ImageView>(R.id.imgAlumno)
        val txtNombre = view.findViewById<TextView>(R.id.txtNombre)
        val txtCorreo = view.findViewById<TextView>(R.id.txtCorreo)
        val txtTelefono = view.findViewById<TextView>(R.id.txtTelefono)

        txtNombre.text = student.nombres
        txtCorreo.text = student.correo
        txtTelefono.text = student.telefono ?: "Sin teléfono"

        // Construir URL completa de la foto
        val fotoUrl = if (!student.foto.isNullOrBlank()) {
            // Asumiendo que el campo 'foto' contiene una ruta como "/media/fotos/.../foto.jpg"
            // y que existe un bucket público llamado "media" en Supabase Storage
            "https://sga.uteq.edu.ec${student.foto}"
        } else null

        Glide.with(view.context)
            .load(fotoUrl)
            .placeholder(R.drawable.outline_account_circle) // crea un icono por defecto
            .error(R.drawable.outline_account_circle)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .circleCrop() // Redondea la imagen para que se vea como círculo
            .into(imgAlumno)

        return view
    }
}