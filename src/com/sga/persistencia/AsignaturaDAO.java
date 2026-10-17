package com.sga.persistencia;

import com.sga.modelo.Asignatura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistencia directa para la gestión de Asignaturas en sgaBD.
 * 
 * @author gonca
 */
public class AsignaturaDAO {

    // Guarda una asignatura (inserta si es nueva o actualiza si ya tiene ID)
    public boolean guardar(Asignatura asig) {
        if (asig.getId() == 0) {
            String sql = "INSERT INTO asignaturas (nombre, cant_creditos, cupo_maximo, estado) VALUES (?, ?, ?, ?)";
            try (Connection con = Conexion.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, asig.getNombre());
                ps.setInt(2, asig.getCantCreditos());
                ps.setInt(3, asig.getCupoMaximo());
                ps.setString(4, asig.getEstado());
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                System.err.println("Error al insertar asignatura: " + e.getMessage());
                return false;
            }
        } else {
            String sql = "UPDATE asignaturas SET nombre = ?, cant_creditos = ?, cupo_maximo = ?, estado = ? WHERE id = ?";
            try (Connection con = Conexion.getConexion();
                 PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, asig.getNombre());
                ps.setInt(2, asig.getCantCreditos());
                ps.setInt(3, asig.getCupoMaximo());
                ps.setString(4, asig.getEstado());
                ps.setInt(5, asig.getId());
                return ps.executeUpdate() > 0;
            } catch (SQLException e) {
                System.err.println("Error al actualizar asignatura: " + e.getMessage());
                return false;
            }
        }
    }

    // Elimina una asignatura por su ID (elimina cursos asociados y borra la asignatura)
    public boolean eliminar(int id) {
        String sqlCursos = "DELETE FROM cursos WHERE asignatura_id = ?";
        String sqlAsig = "DELETE FROM asignaturas WHERE id = ?";

        try (Connection con = Conexion.getConexion()) {
            // 1. Si estaba asociada a algún curso, se eliminan esos cursos para no bloquear
            try (PreparedStatement psCursos = con.prepareStatement(sqlCursos)) {
                psCursos.setInt(1, id);
                psCursos.executeUpdate();
            }

            // 2. Se elimina la asignatura
            try (PreparedStatement psAsig = con.prepareStatement(sqlAsig)) {
                psAsig.setInt(1, id);
                return psAsig.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar asignatura: " + e.getMessage());
            return false;
        }
    }

    // Busca una asignatura por ID
    public Asignatura buscarPorId(int id) {
        String sql = "SELECT id, nombre, cant_creditos, cupo_maximo, estado FROM asignaturas WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Asignatura(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getInt("cant_creditos"),
                        rs.getInt("cupo_maximo"),
                        rs.getString("estado")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar asignatura: " + e.getMessage());
        }
        return null;
    }

    // Lista todas las asignaturas
    public List<Asignatura> listarTodas() {
        List<Asignatura> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, cant_creditos, cupo_maximo, estado FROM asignaturas ORDER BY nombre";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Asignatura(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getInt("cant_creditos"),
                    rs.getInt("cupo_maximo"),
                    rs.getString("estado")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar asignaturas: " + e.getMessage());
        }
        return lista;
    }
}
