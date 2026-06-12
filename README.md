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


## ⚙️ Configuración y ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/tuusuario/supabase-student-manager.git
cd supabase-student-manager
```

### 2. Abrir en Android Studio

- Abre Android Studio → `Open an Existing Project` → selecciona la carpeta del proyecto.
- Espera a que Gradle sincronice las dependencias.

### 3. Configurar Supabase

- Crea un proyecto en [Supabase](https://supabase.com/).
- Ejecuta los scripts SQL proporcionados (`materias` y `alumnos`) para crear las tablas e insertar los datos.
- En el bucket de Storage, crea una carpeta `media` (o el que uses) y asegúrate de que sea **pública** para que las fotos sean accesibles.

### 4. Agregar credenciales

En el archivo `network/SupabaseClient.kt`, reemplaza las constantes:

```kotlin
private const val SUPABASE_URL = "https://tu-proyecto.supabase.co"
private const val SUPABASE_ANON_KEY = "tu-clave-anon-publica"
```

### 5. Ejecutar la app

- Conecta un dispositivo físico o emulador.
- Haz clic en **Run ▶️**.

> **Nota**: Si las fotos no cargan, verifica las rutas en la tabla `alumnos.foto` y que el bucket de Storage sea público o se use el token adecuado.

## 🧪 Filtro de materias (simulación)

Como la base de datos original **no tiene una tabla de inscripción** (alumno ↔ materia), se implementó una asignación **determinística** basada en el `id` del alumno:  
`idMateria = listaMaterias[idAlumno % totalMaterias]`.  
Esto permite probar el filtro combinado semestre + materia sin modificar el esquema real. Si en el futuro se agrega la tabla de relación, basta con reemplazar esa función.

Asignatura: **Aplicaciones Móviles** – Sexto Semestre  
Universidad Técnica Estatal de Quevedo (UTEQ)

<img width="1080" height="2400" alt="image" src="https://github.com/user-attachments/assets/3f53d08e-94cd-451e-9119-6b7f8735ec04" />

<img width="1080" height="2400" alt="image" src="https://github.com/user-attachments/assets/4a099fa4-75c8-431e-ae34-b8d3e65f8be8" />


