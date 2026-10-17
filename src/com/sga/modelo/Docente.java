package com.sga.modelo;
import java.util.ArrayList;
/**
 *
 * @author gonca
 */

public class Docente extends Persona {
    //Lista de cursos del docente
    private ArrayList<Curso> cursos;

    // Constructor completo 
    public Docente(String ci, String nombre, String apellidos, String mail) {
        super(ci, nombre, apellidos, mail);
        this.cursos = new ArrayList<>();
    }

    // Constructor void persistencia*
    public Docente() {
        super();
        this.cursos = new ArrayList<>();
    }

    // Getter Alumnos
    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    // Setter Cursos
    public void setCursos(ArrayList<Curso> cursos) {
        this.cursos = cursos;
    }

    // Método: Asignar curso al docente
    public boolean asignarCurso(Curso curso) {
        if (curso != null && !cursos.contains(curso)) {
            cursos.add(curso);
            return true;
        }
        return false;
    }

    // Método : Quitar curso
    public boolean desasignarCurso(Curso curso) {
        return cursos.remove(curso);
    }

    // Método: Cantidad de cursos
    public int getCantidadCursos() {
        return cursos.size();
    }

    @Override
    public String toString() {
        return "[Docente] " + super.getNombreCompleto() + " | Cursos a cargo: " + getCantidadCursos();
    }
}
