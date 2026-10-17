package com.sga.modelo;

/**
 *
 * @author gonca
 */
public abstract class Persona {
    protected String ci;
    protected String nombre;
    protected String apellidos;
    protected String mail;

    // Constructor completo
    public Persona(String ci, String nombre, String apellidos, String mail) {
        this.ci = ci;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.mail = mail;
    }

    // Constructor void
    public Persona() {}

    // Getters y Setters
    public String getCi() { return ci; }
    public void setCi(String ci) { this.ci = ci; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getMail() { return mail; }
    public void setMail(String mail) { this.mail = mail; }

    //Metodo
    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

    @Override
    public String toString() {
        return "CI: " + ci + " | " + getNombreCompleto() + " (" + mail + ")";
    }
}
