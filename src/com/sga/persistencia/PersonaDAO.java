package com.sga.persistencia;

import com.sga.modelo.Administrador;
import com.sga.modelo.Persona;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Persistencia para autenticación del Administrador en sgaBD.
 * 
 * @author gonca
 */
public class PersonaDAO {

    // Autentica al Administrador buscando por cédula o correo
    public Persona autenticar(String usuario, String contrasenia) {
        String sql = "SELECT ci, nombre, apellidos, mail, contrasenia, rol " +
                     "FROM personas " +
                     "WHERE (ci = ? OR mail = ?) AND rol = 'Administrador'";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, usuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String claveBD = rs.getString("contrasenia");
                    // Valida la clave almacenada o contraseñas maestras
                    if (contrasenia.equals(claveBD) || contrasenia.equals("admin123") || contrasenia.equals("1234")) {
                        return new Administrador(
                            rs.getString("ci"),
                            rs.getString("nombre"),
                            rs.getString("apellidos"),
                            rs.getString("mail")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al autenticar: " + e.getMessage());
        }
        return null;
    }
}