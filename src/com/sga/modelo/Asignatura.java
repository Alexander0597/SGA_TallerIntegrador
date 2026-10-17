package com.sga.modelo;

/**
 *
 * @author gonca
 */

public class Asignatura {
    private int id;
    private String nombre;
    private int cantCreditos;
    private int cupoMaximo;
    private String estado; //Tenemos estados :Activa-Finalizada-Suspendida

    // Constructor para registros
    public Asignatura(String nombre, int cantCreditos, int cupoMaximo) {
        this.nombre = nombre;
        this.cantCreditos = cantCreditos;
        this.cupoMaximo = cupoMaximo;
        this.estado = "Activa";
    }

    // Constructor completo
    public Asignatura(int id, String nombre, int cantCreditos, int cupoMaximo, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.cantCreditos = cantCreditos;
        this.cupoMaximo = cupoMaximo;
        this.estado = estado;
    }

    // Getters y Setters
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getCantCreditos() { return cantCreditos; }
    public void setCantCreditos(int cantCreditos) { this.cantCreditos = cantCreditos; }

    public int getCupoMaximo() { return cupoMaximo; }
    public void setCupoMaximo(int cupoMaximo) { this.cupoMaximo = cupoMaximo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}