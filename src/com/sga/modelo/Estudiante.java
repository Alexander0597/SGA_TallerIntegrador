package com.sga.modelo;
import java.util.ArrayList;
/**
 *
 * @author gonca
 */

public class Estudiante extends Persona {
    private int id;
    private ArrayList<Curso> cursos; 

    // Constructor completo
    public Estudiante(String ci, String nombre, String apellidos, String mail, int id) {
        super(ci, nombre, apellidos, mail);
        this.id = id;
        this.cursos = new ArrayList<>();
    }

    // Constructor vacío
    public Estudiante() {
        super();
        this.cursos = new ArrayList<>();
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public ArrayList<Curso> getCursos() { return cursos; }
    public void setCursos(ArrayList<Curso> cursos) { this.cursos = cursos; }

    // Métodos 
    public boolean inscribirCurso(Curso curso) {
        if (curso != null && !cursos.contains(curso)) {
            cursos.add(curso);
            return true;
        }
        return false;
    }

    public boolean abandonarCurso(Curso curso) {
        return cursos.remove(curso);
    }

    public boolean tieneCurso(int codigo) {
        // Falta sincronizar con la BD **
        for (Curso c : cursos) {
            if (c.getCodigo() == codigo) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "[Estudiante ID: " + id + "] " + super.getNombreCompleto();
    }
}
