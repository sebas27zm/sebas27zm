package com.sistemabancario.view;

import com.sistemabancario.controller.SistemaBancarioController;
import com.sistemabancario.model.Cliente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SistemaBancarioGUI extends JFrame {
    private SistemaBancarioController controller;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    // Colores del tema
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private static final Color ACCENT_COLOR = new Color(46, 204, 113);
    private static final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private static final Color TEXT_COLOR = new Color(44, 62, 80);
    
    public SistemaBancarioGUI() {
        this.controller = new SistemaBancarioController();
        initializeGUI();
    }
    
    private void initializeGUI() {
        setTitle("Sistema Bancario - Gestión de Cuentas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Configurar Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (ClassNotFoundException | InstantiationException | 
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        // Crear el panel principal con CardLayout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        // Crear las diferentes vistas
        crearPantallaInicio();
        
        add(mainPanel);
        setVisible(true);
    }
    
    private void crearPantallaInicio() {
        JPanel pantallaInicio = new JPanel(new BorderLayout());
        pantallaInicio.setBackground(BACKGROUND_COLOR);
        
        // Header
        JPanel headerPanel = crearHeader("Sistema Bancario", "Bienvenido al Sistema de Gestión de Cuentas Bancarias");
        pantallaInicio.add(headerPanel, BorderLayout.NORTH);
        
        // Panel central con opciones
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Verificar si existe administrador
        if (!controller.existeAdministrador()) {
            // Mostrar opción para crear administrador
            JPanel adminPanel = crearPanelOpcion("Crear Administrador", 
                "Debe crear un administrador antes de usar el sistema", 
                "Crear", e -> mostrarCrearAdministrador());
            gbc.gridx = 0; gbc.gridy = 0;
            centerPanel.add(adminPanel, gbc);
        } else {
            // Mostrar opciones de login
            JPanel adminLoginPanel = crearPanelOpcion("Acceso Administrador", 
                "Gestionar clientes y cuentas del sistema", 
                "Ingresar", e -> mostrarLoginAdministrador());
            gbc.gridx = 0; gbc.gridy = 0;
            centerPanel.add(adminLoginPanel, gbc);
            
            JPanel clienteLoginPanel = crearPanelOpcion("Acceso Cliente", 
                "Consultar cuentas y realizar transacciones", 
                "Ingresar", e -> mostrarLoginCliente());
            gbc.gridx = 1; gbc.gridy = 0;
            centerPanel.add(clienteLoginPanel, gbc);
        }
        
        // Botón de salir
        JButton btnSalir = crearBoton("Salir del Sistema", PRIMARY_COLOR);
        btnSalir.addActionListener(e -> System.exit(0));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        centerPanel.add(btnSalir, gbc);
        
        pantallaInicio.add(centerPanel, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = crearFooter();
        pantallaInicio.add(footerPanel, BorderLayout.SOUTH);
        
        mainPanel.add(pantallaInicio, "INICIO");
        cardLayout.show(mainPanel, "INICIO");
    }
    
    private JPanel crearHeader(String titulo, String subtitulo) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setForeground(Color.WHITE);
        
        JLabel lblSubtitulo = new JLabel(subtitulo);
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.WHITE);
        
        JPanel textoPanel = new JPanel(new BorderLayout());
        textoPanel.setBackground(PRIMARY_COLOR);
        textoPanel.add(lblTitulo, BorderLayout.NORTH);
        textoPanel.add(lblSubtitulo, BorderLayout.SOUTH);
        
        headerPanel.add(textoPanel, BorderLayout.WEST);
        
        return headerPanel;
    }
    
    private JPanel crearPanelOpcion(String titulo, String descripcion, String textoBoton, ActionListener accion) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panel.setPreferredSize(new Dimension(350, 150));
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(TEXT_COLOR);
        
        JLabel lblDescripcion = new JLabel("<html><p style='width:280px'>" + descripcion + "</p></html>");
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDescripcion.setForeground(TEXT_COLOR);
        
        JButton boton = crearBoton(textoBoton, SECONDARY_COLOR);
        boton.addActionListener(accion);
        
        JPanel textoPanel = new JPanel(new BorderLayout());
        textoPanel.setBackground(Color.WHITE);
        textoPanel.add(lblTitulo, BorderLayout.NORTH);
        textoPanel.add(lblDescripcion, BorderLayout.CENTER);
        
        panel.add(textoPanel, BorderLayout.CENTER);
        panel.add(boton, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(color);
            }
        });
        
        return boton;
    }
    
    private JPanel crearFooter() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        footerPanel.setBackground(new Color(127, 140, 141));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblFooter = new JLabel("© 2024 Sistema Bancario - Desarrollado con Java Swing");
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 10));
        lblFooter.setForeground(Color.WHITE);
        
        footerPanel.add(lblFooter);
        return footerPanel;
    }
    
    private void mostrarCrearAdministrador() {
        CrearAdministradorDialog dialog = new CrearAdministradorDialog(this, controller);
        dialog.setVisible(true);
        
        // Actualizar la pantalla después de crear el administrador
        if (controller.existeAdministrador()) {
            mainPanel.removeAll();
            crearPantallaInicio();
            mainPanel.revalidate();
            mainPanel.repaint();
        }
    }
    
    private void mostrarLoginAdministrador() {
        LoginDialog dialog = new LoginDialog(this, controller, true);
        dialog.setVisible(true);
    }
    
    private void mostrarLoginCliente() {
        LoginDialog dialog = new LoginDialog(this, controller, false);
        dialog.setVisible(true);
    }
    
    public void mostrarMenuAdministrador() {
        // TODO: Implementar menú del administrador
        JOptionPane.showMessageDialog(this, "Menú del Administrador - En desarrollo", 
            "Sistema Bancario", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void mostrarMenuCliente(Cliente cliente) {
        // TODO: Implementar menú del cliente
        JOptionPane.showMessageDialog(this, "Menú del Cliente: " + cliente.getNombre() + " - En desarrollo", 
            "Sistema Bancario", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new SistemaBancarioGUI();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Error al inicializar la aplicación: " + e.getMessage(), 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}