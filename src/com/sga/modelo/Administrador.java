package com.sga.modelo;

/**
 * Representa al usuario Administrador del sistema.
 * Aplica el principio de Herencia en POO heredando de la clase Persona.
 * 
 * @author gonca
 */
public class Administrador extends Persona {

    // Constructor completo que invoca al constructor de la clase padre (Persona)
    public Administrador(String ci, String nombre, String apellidos, String mail) {
        super(ci, nombre, apellidos, mail);
    }

    // Constructor vacío
    public Administrador() {
        super();
    }
}
