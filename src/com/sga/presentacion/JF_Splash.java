package com.sga.presentacion;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/**
 *
 * @author gonca
 */

public class JF_Splash extends javax.swing.JFrame implements Runnable {

    private JProgressBar progressBar;
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblAutores;
    private JLabel lblEstado;

    public JF_Splash() {
        initComponentsCustom();
        this.setLocationRelativeTo(null); 
    }

    private void initComponentsCustom() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true); 
        setSize(500, 300);

        javax.swing.JPanel panelFondo = new javax.swing.JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(24, 43, 73), getWidth(), getHeight(), new Color(41, 128, 185));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelFondo.setLayout(null);
        setContentPane(panelFondo);

        // Título del Sistema
        lblTitulo = new JLabel("SGA - Taller Integrador - INET 2026");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(20, 60, 460, 40);
        panelFondo.add(lblTitulo);
        
        lblSubtitulo = new JLabel("Sistema de Gestión Académica");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSubtitulo.setForeground(new Color(200, 230, 255));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitulo.setBounds(20, 105, 460, 25);
        panelFondo.add(lblSubtitulo);
        
        lblAutores = new JLabel ("Alexander Goncalves - Santiago Rocha");
        lblAutores.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblAutores.setForeground(new Color(200,230,255));
        lblAutores.setHorizontalAlignment(SwingConstants.CENTER);
        lblAutores.setBounds(20, 135, 460, 25);
        panelFondo.add(lblAutores);
        
        lblEstado = new JLabel("Cargando módulos...");
        lblEstado.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblEstado.setForeground(new Color(220, 220, 220));
        lblEstado.setBounds(40, 210, 420, 20);
        panelFondo.add(lblEstado);

        progressBar = new JProgressBar(0, 100);
        progressBar.setBounds(40, 235, 420, 12);
        progressBar.setForeground(new Color(46, 204, 113));
        progressBar.setBackground(new Color(15, 30, 50));
        progressBar.setBorderPainted(false);
        progressBar.setValue(0);
        panelFondo.add(progressBar);
    }

    @Override
    public void run() {
        this.setVisible(true);
        try {
            // Animación de la barra de progreso 
            for (int i = 0; i <= 100; i++) {
                Thread.sleep(30);
                progressBar.setValue(i);

                if (i == 20) {
                    lblEstado.setText("Conectando con la base de datos...");
                } else if (i == 50) {
                    lblEstado.setText("Cargando interfaces de usuario...");
                } else if (i == 85) {
                    lblEstado.setText("Iniciando aplicación...");
                }
            }

            Thread.sleep(300); 
            this.dispose();   
            
            //Abrir Login
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new JF_Login().setVisible(true);
                }
            });

        } catch (InterruptedException ex) {
            Logger.getLogger(JF_Splash.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        new Thread(new JF_Splash()).start();
    }
}