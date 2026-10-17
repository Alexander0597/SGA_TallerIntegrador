package com.sga.presentacion;
import com.sga.persistencia.InscripcionDAO;
import com.sga.persistencia.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gonca
 */
public class JF_Inscripciones extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JF_Inscripciones.class.getName());
    private InscripcionDAO inscripcionDAO = new InscripcionDAO();
    private Map<String, Integer> mapaEstudiantes = new HashMap<>();
    private Map<String, Integer> mapaGrupos = new HashMap<>();
    private Map<Integer, String> mapaCodigoTextoGrupo = new HashMap<>();
   
    public JF_Inscripciones() {
        initComponents();
        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); // Pantalla completa
        cargarCombos();
        cargarTabla();
    }

    public JF_Inscripciones(int grupoCodigoSeleccionado) {
        this();
        seleccionarGrupo(grupoCodigoSeleccionado);
    }

    public void seleccionarGrupo(int grupoCodigo) {
        String texto = mapaCodigoTextoGrupo.get(grupoCodigo);
        if (texto != null) {
            cbGrupos.setSelectedItem(texto);
        }
    }

    private void cargarCombos() {
        cbEstudiantes.removeAllItems();
        cbGrupos.removeAllItems();

        cbEstudiantes.addItem("-- Seleccione un Estudiante --");
        cbGrupos.addItem("-- Seleccione un Curso / Grupo --");

        mapaEstudiantes.clear();
        mapaGrupos.clear();
        mapaCodigoTextoGrupo.clear();

        // 1. Cargar Estudiantes (ID, CI, Nombre y Apellido)
        String sqlEst = "SELECT e.id, e.ci, p.nombre, p.apellidos " +
                        "FROM estudiantes e " +
                        "INNER JOIN personas p ON e.ci = p.ci " +
                        "ORDER BY p.apellidos, p.nombre";

        Connection con = Conexion.getConexion();
        try (PreparedStatement ps = con.prepareStatement(sqlEst);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int idEstudiante = rs.getInt("id");
                String textoVisible = rs.getString("ci") + " - " + rs.getString("apellidos") + ", " + rs.getString("nombre");
                cbEstudiantes.addItem(textoVisible);
                mapaEstudiantes.put(textoVisible, idEstudiante);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar lista de estudiantes: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }

        // 2. Cargar Grupos con información de sus Cursos asociados
        String sqlGrupos = "SELECT g.codigo, g.nombre, g.turno, " +
                           "COALESCE(GROUP_CONCAT(DISTINCT CONCAT('Curso #', c.codigo, ' (', a.nombre, ')') SEPARATOR ', '), 'Sin cursos asignados') AS cursos_info " +
                           "FROM grupos g " +
                           "LEFT JOIN cursos c ON c.grupo_codigo = g.codigo " +
                           "LEFT JOIN asignaturas a ON c.asignatura_id = a.id " +
                           "GROUP BY g.codigo, g.nombre, g.turno " +
                           "ORDER BY g.nombre";

        try (PreparedStatement ps = con.prepareStatement(sqlGrupos);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int codigoGrupo = rs.getInt("codigo");
                String cursosInfo = rs.getString("cursos_info");
                String nombreGrupo = rs.getString("nombre");
                String turno = rs.getString("turno");
                
                String textoVisible = cursosInfo + " | Grupo " + nombreGrupo + " (" + turno + ")";
                cbGrupos.addItem(textoVisible);
                mapaGrupos.put(textoVisible, codigoGrupo);
                mapaCodigoTextoGrupo.put(codigoGrupo, textoVisible);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar lista de cursos y grupos: " + ex.getMessage(), "Error SQL", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void cargarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblInscripciones.getModel();
        modelo.setRowCount(0);

        try {
            List<String[]> lista = inscripcionDAO.listarInscripciones();
            for (String[] fila : lista) {
                modelo.addRow(fila);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar la tabla de inscripciones: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        cbEstudiantes = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cbGrupos = new javax.swing.JComboBox<>();
        btnInscribir = new javax.swing.JButton();
        btnBaja = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblInscripciones = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        cbEstudiantes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione un Estudiante" }));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("Gestión de Inscripciones");

        jLabel2.setText("Estudiante:");

        jLabel3.setText("Curso / Grupo:");

        cbGrupos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione un Curso/Grupo" }));

        btnInscribir.setText("Inscribir alumno");
        btnInscribir.addActionListener(this::btnInscribirActionPerformed);

        btnBaja.setText("Dar de baja");
        btnBaja.addActionListener(this::btnBajaActionPerformed);

        btnVolver.setText("Volver al menú");
        btnVolver.addActionListener(this::btnVolverActionPerformed);

        tblInscripciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CI", "Nombre", "Apellido", "Curso(s) Asignado(s)", "Grupo", "Turno"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblInscripciones);
        if (tblInscripciones.getColumnModel().getColumnCount() > 0) {
            tblInscripciones.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(cbEstudiantes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbGrupos, 0, 179, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnVolver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnBaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnInscribir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(263, 263, 263)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbEstudiantes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbGrupos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57)
                        .addComponent(btnInscribir)
                        .addGap(18, 18, 18)
                        .addComponent(btnBaja)
                        .addGap(18, 18, 18)
                        .addComponent(btnVolver))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new java.awt.GridBagConstraints());

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInscribirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInscribirActionPerformed
        // TODO add your handling code here:
        String estSeleccionado = (String) cbEstudiantes.getSelectedItem();
        String grupoSeleccionado = (String) cbGrupos.getSelectedItem();

        if (cbEstudiantes.getSelectedIndex() <= 0 || cbGrupos.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione tanto un estudiante como un grupo.", "Selección Incompleta", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Integer idEstudiante = mapaEstudiantes.get(estSeleccionado);
        Integer codigoGrupo = mapaGrupos.get(grupoSeleccionado);

        if (idEstudiante != null && codigoGrupo != null) {
            if (inscripcionDAO.inscribirEstudiante(idEstudiante, codigoGrupo)) {
                JOptionPane.showMessageDialog(this, "Estudiante inscripto con éxito al curso/grupo.");
                cargarTabla(); 
                
                cbEstudiantes.setSelectedIndex(0);
                cbGrupos.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo realizar la inscripción. Verifique si el estudiante ya está inscripto en este grupo.", "Error de Inscripción", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnInscribirActionPerformed

    private void btnBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajaActionPerformed
        // TODO add your handling code here:
        int fila = tblInscripciones.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro de la tabla para dar de baja.", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String ci = tblInscripciones.getValueAt(fila, 0).toString();
        String nombre = tblInscripciones.getValueAt(fila, 1).toString();
        String apellido = tblInscripciones.getValueAt(fila, 2).toString();
        String cursos = tblInscripciones.getValueAt(fila, 3).toString();
        String grupo = tblInscripciones.getValueAt(fila, 4).toString();

        int confirm = JOptionPane.showConfirmDialog(this, 
                "¿Seguro que desea dar de baja al estudiante " + nombre + " " + apellido + " (CI: " + ci + ")?\n" +
                "Curso(s): " + cursos + "\nGrupo: " + grupo, 
                "Confirmar Baja", 
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean exito = inscripcionDAO.darDeBajaPorCiYGrupo(ci, grupo);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Inscripción dada de baja con éxito.");
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo dar de baja el registro en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnBajaActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new JF_Inscripciones().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBaja;
    private javax.swing.JButton btnInscribir;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<String> cbEstudiantes;
    private javax.swing.JComboBox<String> cbGrupos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblInscripciones;
    // End of variables declaration//GEN-END:variables
}
