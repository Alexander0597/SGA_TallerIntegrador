package com.sga.persistencia;

import com.sga.modelo.Estudiante;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistencia directa para la gestión de Estudiantes en sgaBD.
 * 
 * @author gonca
 */
public class EstudianteDAO {

    // Guarda un estudiante (crea nuevo o actualiza existente por CI)
    public boolean guardar(Estudiante est) {
        String sqlPersona = "INSERT INTO personas (ci, nombre, apellidos, mail, rol) " +
                            "VALUES (?, ?, ?, ?, 'Estudiante') " +
                            "ON DUPLICATE KEY UPDATE nombre = ?, apellidos = ?, mail = ?";
        String sqlEstudiante = "INSERT IGNORE INTO estudiantes (ci) VALUES (?)";

        try (Connection con = Conexion.getConexion()) {
            // 1. Guardar o actualizar en la tabla personas
            try (PreparedStatement psP = con.prepareStatement(sqlPersona)) {
                psP.setString(1, est.getCi());
                psP.setString(2, est.getNombre());
                psP.setString(3, est.getApellidos());
                psP.setString(4, est.getMail());
                psP.setString(5, est.getNombre());
                psP.setString(6, est.getApellidos());
                psP.setString(7, est.getMail());
                psP.executeUpdate();
            }

            // 2. Asegurar el registro en la tabla estudiantes
            try (PreparedStatement psE = con.prepareStatement(sqlEstudiante)) {
                psE.setString(1, est.getCi());
                psE.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error al guardar estudiante: " + e.getMessage());
            return false;
        }
    }

    // Elimina de personas (por borrado en cascada, MySQL elimina automáticamente de estudiantes)
    public boolean eliminar(String ci) {
        String sql = "DELETE FROM personas WHERE ci = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ci);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar estudiante: " + e.getMessage());
            return false;
        }
    }

    // Busca un estudiante por su cédula
    public Estudiante buscarPorCi(String ci) {
        String sql = "SELECT p.ci, p.nombre, p.apellidos, p.mail, e.id " +
                     "FROM personas p " +
                     "JOIN estudiantes e ON p.ci = e.ci " +
                     "WHERE p.ci = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ci);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Estudiante(
                        rs.getString("ci"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("mail"),
                        rs.getInt("id")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar estudiante: " + e.getMessage());
        }
        return null;
    }

    // Lista todos los estudiantes
    public List<Estudiante> listarTodos() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT p.ci, p.nombre, p.apellidos, p.mail, e.id " +
                     "FROM personas p " +
                     "JOIN estudiantes e ON p.ci = e.ci " +
                     "ORDER BY p.apellidos, p.nombre";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Estudiante(
                    rs.getString("ci"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("mail"),
                    rs.getInt("id")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar estudiantes: " + e.getMessage());
        }
        return lista;
    }
}