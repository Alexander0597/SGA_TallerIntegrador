-- ==============================================================
-- SISTEMA DE GESTIÓN ACADÉMICA (SGA)
-- Base de Datos: sgaBD
-- Módulo del Administrador
-- ==============================================================

DROP DATABASE IF EXISTS sgaBD;
CREATE DATABASE sgaBD CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE sgaBD;

-- 1. Tabla PERSONAS
-- Contiene los datos comunes de todos los usuarios del sistema.
CREATE TABLE personas (
    ci VARCHAR(20) NOT NULL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(50) NOT NULL,
    mail VARCHAR(100) NOT NULL UNIQUE,
    contrasenia VARCHAR(100) NOT NULL DEFAULT '1234',
    rol VARCHAR(20) NOT NULL CHECK (rol IN ('Administrador', 'Docente', 'Estudiante'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 2. Tabla ADMINISTRADORES
-- Especialización de personas para usuarios administradores
CREATE TABLE administradores (
    ci VARCHAR(20) NOT NULL PRIMARY KEY,
    FOREIGN KEY (ci) REFERENCES personas(ci) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. Tabla DOCENTES
-- Especialización de personas para docentes registrados por el administrador
CREATE TABLE docentes (
    ci VARCHAR(20) NOT NULL PRIMARY KEY,
    FOREIGN KEY (ci) REFERENCES personas(ci) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. Tabla ESTUDIANTES
-- Especialización de personas para estudiantes registrados por el administrador
CREATE TABLE estudiantes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ci VARCHAR(20) NOT NULL UNIQUE,
    FOREIGN KEY (ci) REFERENCES personas(ci) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. Tabla ASIGNATURAS
-- Materias que se imparten en la institución
CREATE TABLE asignaturas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    cant_creditos INT NOT NULL CHECK (cant_creditos > 0),
    cupo_maximo INT NOT NULL CHECK (cupo_maximo > 0),
    estado VARCHAR(20) NOT NULL DEFAULT 'Activa' CHECK (estado IN ('Activa', 'Finalizada', 'Suspendida'))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 6. Tabla GRUPOS
-- Clases o grupos organizados por turno y año
CREATE TABLE grupos (
    codigo INT NOT NULL PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    turno VARCHAR(20) NOT NULL CHECK (turno IN ('Matutino', 'Vespertino', 'Nocturno')),
    anio DATE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 7. Tabla CURSOS
-- Relación de un grupo cursando una asignatura con un docente asignado
CREATE TABLE cursos (
    codigo INT NOT NULL PRIMARY KEY,
    asignatura_id INT NOT NULL,
    docente_ci VARCHAR(20) NULL,
    grupo_codigo INT NOT NULL,
    FOREIGN KEY (asignatura_id) REFERENCES asignaturas(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (docente_ci) REFERENCES docentes(ci) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (grupo_codigo) REFERENCES grupos(codigo) ON DELETE CASCADE,
    UNIQUE KEY uq_curso_grupo_asig (asignatura_id, grupo_codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 8. Tabla GRUPO_ESTUDIANTES
-- Inscripción de estudiantes a grupos
CREATE TABLE grupo_estudiantes (
    grupo_codigo INT NOT NULL,
    estudiante_id INT NOT NULL,
    PRIMARY KEY (grupo_codigo, estudiante_id),
    FOREIGN KEY (grupo_codigo) REFERENCES grupos(codigo) ON DELETE CASCADE,
    FOREIGN KEY (estudiante_id) REFERENCES estudiantes(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ==============================================================
-- DATOS INICIALES DE PRUEBA
-- ==============================================================

-- 1. Administrador por defecto
-- Cédula: 11111111 | Correo: admin@sga.edu | Contraseña: admin o 1234
INSERT INTO personas (ci, nombre, apellidos, mail, contrasenia, rol) VALUES
('11111111', 'Administrador', 'General', 'admin@sga.edu', 'admin123', 'Administrador');
INSERT INTO administradores (ci) VALUES ('11111111');

-- 2. Docentes de prueba
INSERT INTO personas (ci, nombre, apellidos, mail, contrasenia, rol) VALUES
('22222222', 'Carlos', 'Rodríguez', 'carlos.rodriguez@sga.edu', '1234', 'Docente'),
('33333333', 'María', 'Fernández', 'maria.fernandez@sga.edu', '1234', 'Docente');
INSERT INTO docentes (ci) VALUES ('22222222'), ('33333333');

-- 3. Estudiantes de prueba
INSERT INTO personas (ci, nombre, apellidos, mail, contrasenia, rol) VALUES
('44444444', 'Juan', 'Pérez', 'juan.perez@estudiante.edu', '1234', 'Estudiante'),
('55555555', 'Ana', 'Gómez', 'ana.gomez@estudiante.edu', '1234', 'Estudiante');
INSERT INTO estudiantes (ci) VALUES ('44444444'), ('55555555');

-- 4. Asignaturas de prueba
INSERT INTO asignaturas (nombre, cant_creditos, cupo_maximo, estado) VALUES
('Programación I', 10, 30, 'Activa'),
('Bases de Datos I', 8, 30, 'Activa'),
('Sistemas Operativos', 6, 25, 'Activa');

-- 5. Grupos de prueba
INSERT INTO grupos (codigo, nombre, turno, anio) VALUES
(101, '3ro Matutino', 'Matutino', '2026-03-01'),
(102, '3ro Nocturno', 'Nocturno', '2026-03-01');

-- 6. Cursos asignados
INSERT INTO cursos (codigo, asignatura_id, docente_ci, grupo_codigo) VALUES
(1, 1, '22222222', 101),
(2, 2, '33333333', 101);

-- 7. Inscripciones de prueba
INSERT INTO grupo_estudiantes (grupo_codigo, estudiante_id) VALUES
(101, 1);
