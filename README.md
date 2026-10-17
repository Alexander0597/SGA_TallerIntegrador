# SGA - Sistema de Gestión Académica (Taller Integrador)

Sistema de gestión académica desarrollado en Java (Swing) con base de datos relacional MySQL.

## 📋 Módulos y Roles
- **Administrador:** Gestión de docentes, estudiantes, cursos y configuraciones del sistema.
- **Docente:** Gestión de asignaturas, calificaciones y asistencia.
- **Estudiante:** Consulta de inscripciones y calificaciones.

## 🛠️ Tecnologías y Requisitos
- **Lenguaje:** Java SE (JDK 17 o superior / JDK 21+)
- **IDE recomendado:** Apache NetBeans / IntelliJ IDEA / VS Code
- **Base de Datos:** MySQL Server 8.0+
- **Driver:** MySQL Connector/J (incluido en `lib/`)

## 🚀 Puesta en marcha

### 1. Base de Datos
1. Abre tu gestor de MySQL preferido (MySQL Workbench, phpMyAdmin, DBeaver, o consola).
2. Ejecuta el script SQL [sgaBD.sql](sgaBD.sql) para crear la base de datos `sgaBD` y sus tablas correspondientes.

### 2. Configuración de Conexión
Verifica los datos de conexión (usuario y contraseña de MySQL) en la clase de conexión en `src/com/sga/persistencia/`.

### 3. Ejecución
1. Abre el proyecto en NetBeans (`Archivo -> Abrir Proyecto`).
2. Haz clic derecho en el proyecto y selecciona **Clean and Build** (Limpiar y Construir).
3. Ejecuta el proyecto (**Run** o `F6`).
