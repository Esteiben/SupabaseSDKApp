# 📱 Supabase Student Manager

Aplicación Android nativa desarrollada en **Kotlin** que consume datos desde **Supabase** (PostgreSQL) y los muestra en formato de tarjeta con imágenes. Incluye filtros por semestre y materia, utilizando `ListView`, `Glide` y el SDK oficial de Supabase.

## 🎯 Propósito del proyecto

Práctica académica para la asignatura **Aplicaciones Móviles** (Sexto Semestre).  
Objetivos:

- Consumir datos desde Supabase usando su SDK Kotlin.
- Mostrar información de estudiantes en un `ListView` con diseño de tarjeta personalizado.
- Cargar imágenes desde **Supabase Storage** usando **Glide**.
- Implementar filtros combinados por **semestre (nivel)** y **materia**.
- Aplicar principios modernos de desarrollo Android (corutinas, Material Design, serialización JSON).

## 🧱 Tecnologías utilizadas

| Área               | Herramientas / Librerías                                                                 |
|--------------------|-------------------------------------------------------------------------------------------|
| **Lenguaje**       | Kotlin                                                                                    |
| **UI**             | ConstraintLayout, LinearLayout, ListView, CardView (diseño de tarjeta), Material Design   |
| **Networking**     | Supabase SDK (Postgrest), Ktor Client                                                     |
| **Serialización**  | kotlinx.serialization                                                                     |
| **Imágenes**       | Glide                                                                                     |
| **Concurrencia**   | Kotlin Coroutines                                                                         |
| **Base de datos**  | Supabase (PostgreSQL) – tablas `alumnos` y `materias`                                     |

## 📱 Características principales

- **Lista de estudiantes** en formato tarjeta con foto, nombre, correo y teléfono.
- **Filtro por semestre** (Spinner/ExposedDropdownMenu) → muestra las materias de ese nivel.
- **Filtro por materia** → muestra solo los alumnos cursando esa materia (asignación simulada en ausencia de tabla intermedia real).
- **Carga asíncrona** de datos con corutinas.
- **Manejo de errores** y retroalimentación con `Toast`.
- **Diseño limpio** y adaptable.
