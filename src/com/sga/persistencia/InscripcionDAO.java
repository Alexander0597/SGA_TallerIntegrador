package com.sga.persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistencia directa para inscripciones de estudiantes en sgaBD.
 * 
 * @author gonca
 */
public class InscripcionDAO {

    // Inscribe a un estudiante en un grupo
    public boolean inscribirEstudiante(int estudianteId, int grupoCodigo) {
        String sql = "INSERT INTO grupo_estudiantes (grupo_codigo, estudiante_id) VALUES (?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, grupoCodigo);
            ps.setInt(2, estudianteId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al inscribir estudiante: " + ex.getMessage());
            return false;
        }
    }

    // Da de baja la inscripción de un estudiante por IDs
    public boolean darDeBaja(int estudianteId, int grupoCodigo) {
        String sql = "DELETE FROM grupo_estudiantes WHERE grupo_codigo = ? AND estudiante_id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, grupoCodigo);
            ps.setInt(2, estudianteId);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al dar de baja estudiante: " + ex.getMessage());
            return false;
        }
    }

    // Da de baja por Cédula y Nombre de grupo
    public boolean darDeBajaPorCiYGrupo(String ci, String grupoNombre) {
        String sql = "DELETE ge FROM grupo_estudiantes ge " +
                     "INNER JOIN estudiantes e ON ge.estudiante_id = e.id " +
                     "INNER JOIN grupos g ON ge.grupo_codigo = g.codigo " +
                     "WHERE e.ci = ? AND g.nombre = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, ci);
            ps.setString(2, grupoNombre);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            System.err.println("Error al dar de baja por CI y grupo: " + ex.getMessage());
            return false;
        }
    }

    // Lista todas las inscripciones activas
    public List<String[]> listarInscripciones() {
        List<String[]> lista = new ArrayList<>();
        String sql = "SELECT e.ci, p.nombre, p.apellidos, " +
                     "COALESCE(GROUP_CONCAT(DISTINCT CONCAT('Curso #', c.codigo, ' (', a.nombre, ')') SEPARATOR ', '), 'Sin cursos asignados') AS cursos_asociados, " +
                     "g.nombre AS grupo_nombre, g.turno " +
                     "FROM grupo_estudiantes ge " +
                     "INNER JOIN estudiantes e ON ge.estudiante_id = e.id " +
                     "INNER JOIN personas p ON e.ci = p.ci " +
                     "INNER JOIN grupos g ON ge.grupo_codigo = g.codigo " +
                     "LEFT JOIN cursos c ON c.grupo_codigo = g.codigo " +
                     "LEFT JOIN asignaturas a ON c.asignatura_id = a.id " +
                     "GROUP BY e.id, e.ci, p.nombre, p.apellidos, g.codigo, g.nombre, g.turno " +
                     "ORDER BY g.nombre, p.apellidos, p.nombre";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String[] fila = new String[6];
                fila[0] = rs.getString("ci");
                fila[1] = rs.getString("nombre");
                fila[2] = rs.getString("apellidos");
                fila[3] = rs.getString("cursos_asociados");
                fila[4] = rs.getString("grupo_nombre");
                fila[5] = rs.getString("turno");
                lista.add(fila);
            }
        } catch (SQLException ex) {
            System.err.println("Error al listar inscripciones: " + ex.getMessage());
        }
        return lista;
    }
}