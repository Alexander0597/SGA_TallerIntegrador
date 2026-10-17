package com.sga.modelo;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author gonca
 */


public class Grupo {
    private int codigo;
    private String nombre;
    private String turno; //"Matutino"-"Vespertino"-"Nocturno"
    private Date anio;
    private ArrayList<Estudiante> estudiantes; 

    // Constructor completo
    public Grupo(int codigo, String nombre, String turno, Date anio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.turno = turno;
        this.anio = anio;
        this.estudiantes = new ArrayList<>(); // Inicialización obligatoria de la lista
    }

    // Constructor void
    public Grupo() {
        this.estudiantes = new ArrayList<>();
    }

    // Getters y Setters
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Date getAnio() {
        return anio;
    }

    public void setAnio(Date anio) {
        this.anio = anio;
    }

    public ArrayList<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(ArrayList<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    // Método: Inscribir estudiante 
    public boolean inscribirEstudiante(Estudiante e) {
        if (e != null && !estudiantes.contains(e)) {
            estudiantes.add(e);
            return true;
        }
        return false;
    }

    // Método: Remover un estudiante
    public boolean removerEstudiante(String ci) {
        if (ci == null) {
            return false;
        }
        for (int i = 0; i < estudiantes.size(); i++) {
            if (estudiantes.get(i).getCi().equals(ci)) {
                estudiantes.remove(i);
                return true; // Removido con éxito
            }
        }
        return false; // No se encontró ningún estudiante con esa CI en este grupo
    }

    @Override
    public String toString() {
        return "Grupo: " + nombre + " (Código: " + codigo + " | Turno: " + turno + ")";
    }
}
