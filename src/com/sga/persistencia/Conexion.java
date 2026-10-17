package com.sga.persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Conexión directa a la base de datos MySQL (sgaBD).
 * Método estático getConexion() para abrir y devolver una conexión.
 * 
 * @author gonca
 */
public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3309/sgaBD?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String CONTRASENIA = "";

    // Método principal para obtener la conexión
    public static Connection getConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, CONTRASENIA);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: Driver de MySQL no encontrado: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Error al conectar con sgaBD: " + e.getMessage());
        }
        return null;
    }
}
