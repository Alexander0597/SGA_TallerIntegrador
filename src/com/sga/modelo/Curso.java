package com.sga.modelo;

/**
 * Representa la asignación académica de un Curso en el sistema.
 * Un curso vincula una Asignatura con un Grupo de estudiantes y un Docente.
 * 
 * @author gonca
 */
public class Curso {
    private int codigo;
    private Asignatura asignatura;
    private Docente docente;
    private Grupo grupo;

    // Constructor completo
    public Curso(int codigo, Asignatura asignatura, Docente docente, Grupo grupo) {
        this.codigo = codigo;
        this.asignatura = asignatura;
        this.docente = docente;
        this.grupo = grupo;
    }

    // Constructor vacío
    public Curso() {
    }

    // Getters y Setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Asignatura getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        String nombreAsignatura = (asignatura != null) ? asignatura.getNombre() : "Sin Asignatura";
        String nombreGrupo = (grupo != null) ? grupo.getNombre() : "Sin Grupo";
        String nombreDocente = (docente != null) ? docente.getNombreCompleto() : "Sin Docente";
        
        return "Curso [" + codigo + "] " + nombreAsignatura + " - " + nombreGrupo + " (Prof: " + nombreDocente + ")";
    }
}