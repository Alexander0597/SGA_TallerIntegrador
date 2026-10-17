package com.sga.persistencia;

import com.sga.modelo.Docente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistencia directa para la gestión de Docentes en sgaBD.
 * 
 * @author gonca
 */
public class DocenteDAO {

    // Guarda un docente (inserta nuevo o actualiza existente por CI)
    public boolean guardar(Docente docente) {
        String sqlPersona = "INSERT INTO personas (ci, nombre, apellidos, mail, rol) " +
                            "VALUES (?, ?, ?, ?, 'Docente') " +
                            "ON DUPLICATE KEY UPDATE nombre = ?, apellidos = ?, mail = ?";
        String sqlDocente = "INSERT IGNORE INTO docentes (ci) VALUES (?)";

        try (Connection con = Conexion.getConexion()) {
            // 1. Guardar/actualizar datos en personas
            try (PreparedStatement psP = con.prepareStatement(sqlPersona)) {
                psP.setString(1, docente.getCi());
                psP.setString(2, docente.getNombre());
                psP.setString(3, docente.getApellidos());
                psP.setString(4, docente.getMail());
                psP.setString(5, docente.getNombre());
                psP.setString(6, docente.getApellidos());
                psP.setString(7, docente.getMail());
                psP.executeUpdate();
            }

            // 2. Registrar en la tabla docentes
            try (PreparedStatement psD = con.prepareStatement(sqlDocente)) {
                psD.setString(1, docente.getCi());
                psD.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Error al guardar docente: " + e.getMessage());
            return false;
        }
    }

    // Elimina el docente (desvincula cursos asignados y borra de personas/docentes)
    public boolean eliminar(String ci) {
        String sqlDesvincular = "UPDATE cursos SET docente_ci = NULL WHERE docente_ci = ?";
        String sqlDelete = "DELETE FROM personas WHERE ci = ?";

        try (Connection con = Conexion.getConexion()) {
            // 1. Si el docente estaba asignado a algún curso, se desvincula para no bloquear la eliminación
            try (PreparedStatement psDesv = con.prepareStatement(sqlDesvincular)) {
                psDesv.setString(1, ci);
                psDesv.executeUpdate();
            }

            // 2. Se elimina de personas (y automáticamente de docentes por cascada)
            try (PreparedStatement psDel = con.prepareStatement(sqlDelete)) {
                psDel.setString(1, ci);
                return psDel.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar docente: " + e.getMessage());
            return false;
        }
    }

    // Busca un docente por su cédula
    public Docente obtenerPorCi(String ci) {
        String sql = "SELECT p.ci, p.nombre, p.apellidos, p.mail " +
                     "FROM personas p " +
                     "JOIN docentes d ON p.ci = d.ci " +
                     "WHERE p.ci = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ci);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Docente(
                        rs.getString("ci"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("mail")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar docente: " + e.getMessage());
        }
        return null;
    }

    // Lista todos los docentes registrados
    public List<Docente> listarTodos() {
        List<Docente> lista = new ArrayList<>();
        String sql = "SELECT p.ci, p.nombre, p.apellidos, p.mail " +
                     "FROM personas p " +
                     "JOIN docentes d ON p.ci = d.ci " +
                     "ORDER BY p.apellidos, p.nombre";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Docente(
                    rs.getString("ci"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("mail")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar docentes: " + e.getMessage());
        }
        return lista;
    }
}
