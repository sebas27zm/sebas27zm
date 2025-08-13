package com.sistemabancario.view;

import com.sistemabancario.controller.SistemaBancarioController;
import com.sistemabancario.model.Cliente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class LoginDialog extends JDialog {
    private SistemaBancarioController controller;
    private SistemaBancarioGUI parentFrame;
    private boolean esAdministrador;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color ACCENT_COLOR = new Color(46, 204, 113);
    private static final Color ERROR_COLOR = new Color(231, 76, 60);
    
    public LoginDialog(SistemaBancarioGUI parent, SistemaBancarioController controller, boolean esAdministrador) {
        super(parent, esAdministrador ? "Login Administrador" : "Login Cliente", true);
        this.parentFrame = parent;
        this.controller = controller;
        this.esAdministrador = esAdministrador;
        initializeDialog();
    }
    
    private void initializeDialog() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("Iniciar Sesión - " + (esAdministrador ? "Administrador" : "Cliente"));
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo);
        
        // Formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 30, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 5, 15, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Campo de correo
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        JLabel lblCorreo = new JLabel("Correo Electrónico:");
        lblCorreo.setFont(new Font("Arial", Font.BOLD, 12));
        lblCorreo.setForeground(new Color(44, 62, 80));
        formPanel.add(lblCorreo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtCorreo = new JTextField(20);
        txtCorreo.setFont(new Font("Arial", Font.PLAIN, 12));
        txtCorreo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        formPanel.add(txtCorreo, gbc);
        
        // Campo de contraseña
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(new Font("Arial", Font.BOLD, 12));
        lblContrasena.setForeground(new Color(44, 62, 80));
        formPanel.add(lblContrasena, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        txtContrasena = new JPasswordField(20);
        txtContrasena.setFont(new Font("Arial", Font.PLAIN, 12));
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        formPanel.add(txtContrasena, gbc);
        
        // Agregar listener para Enter
        KeyListener enterListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    iniciarSesion();
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {}
        };
        
        txtCorreo.addKeyListener(enterListener);
        txtContrasena.addKeyListener(enterListener);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnLogin = crearBoton("Iniciar Sesión", ACCENT_COLOR);
        btnLogin.addActionListener(e -> iniciarSesion());
        
        JButton btnCancelar = crearBoton("Cancelar", ERROR_COLOR);
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCancelar);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Agregar información de prueba si no es administrador
        if (!esAdministrador) {
            JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            infoPanel.setBackground(new Color(241, 196, 15));
            infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            
            JLabel lblInfo = new JLabel("<html><center><small>Datos de prueba:<br/>juan.perez@email.com / juan123<br/>maria.gonzalez@email.com / maria123</small></center></html>");
            lblInfo.setFont(new Font("Arial", Font.PLAIN, 10));
            lblInfo.setForeground(new Color(44, 62, 80));
            infoPanel.add(lblInfo);
            
            mainPanel.add(infoPanel, BorderLayout.SOUTH);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        }
        
        // Foco inicial en el campo de correo
        SwingUtilities.invokeLater(() -> txtCorreo.requestFocus());
    }
    
    private JButton crearBoton(String texto, Color color) {
        JButton boton = new JButton(texto);
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
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
    
    private void iniciarSesion() {
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        
        // Validar campos vacíos
        if (correo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, complete todos los campos.", 
                "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            if (esAdministrador) {
                if (controller.validarAdministrador(correo, contrasena)) {
                    JOptionPane.showMessageDialog(this, 
                        "Bienvenido, Administrador.", 
                        "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    parentFrame.mostrarMenuAdministrador();
                } else {
                    mostrarErrorLogin();
                }
            } else {
                Cliente cliente = controller.validarCliente(correo, contrasena);
                if (cliente != null) {
                    JOptionPane.showMessageDialog(this, 
                        "Bienvenido, " + cliente.getNombre() + " " + cliente.getApellidos() + ".", 
                        "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    parentFrame.mostrarMenuCliente(cliente);
                } else {
                    mostrarErrorLogin();
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al conectar con la base de datos: " + ex.getMessage(), 
                "Error de Conexión", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void mostrarErrorLogin() {
        JOptionPane.showMessageDialog(this, 
            "Credenciales incorrectas. Verifique su correo y contraseña.", 
            "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
        
        // Limpiar campos y enfocar correo
        txtContrasena.setText("");
        txtCorreo.requestFocus();
    }
}