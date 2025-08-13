package com.sistemabancario.view;

import com.sistemabancario.controller.SistemaBancarioController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CrearAdministradorDialog extends JDialog {
    private SistemaBancarioController controller;
    private JTextField txtNombre;
    private JTextField txtApellidos;
    private JTextField txtCedula;
    private JTextField txtCorreo;
    private JPasswordField txtContrasena;
    private JPasswordField txtConfirmarContrasena;
    
    private static final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private static final Color ACCENT_COLOR = new Color(46, 204, 113);
    private static final Color ERROR_COLOR = new Color(231, 76, 60);
    
    public CrearAdministradorDialog(JFrame parent, SistemaBancarioController controller) {
        super(parent, "Crear Administrador", true);
        this.controller = controller;
        initializeDialog();
    }
    
    private void initializeDialog() {
        setSize(450, 400);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        
        // Header
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("Crear Administrador del Sistema");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo);
        
        // Formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Campos del formulario
        addFormField(formPanel, "Nombre:", txtNombre = new JTextField(20), gbc, 0);
        addFormField(formPanel, "Apellidos:", txtApellidos = new JTextField(20), gbc, 1);
        addFormField(formPanel, "Número de Cédula:", txtCedula = new JTextField(20), gbc, 2);
        addFormField(formPanel, "Correo Electrónico:", txtCorreo = new JTextField(20), gbc, 3);
        addFormField(formPanel, "Contraseña:", txtContrasena = new JPasswordField(20), gbc, 4);
        addFormField(formPanel, "Confirmar Contraseña:", txtConfirmarContrasena = new JPasswordField(20), gbc, 5);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        JButton btnCrear = crearBoton("Crear Administrador", ACCENT_COLOR);
        btnCrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearAdministrador();
            }
        });
        
        JButton btnCancelar = crearBoton("Cancelar", ERROR_COLOR);
        btnCancelar.addActionListener(e -> dispose());
        
        buttonPanel.add(btnCrear);
        buttonPanel.add(btnCancelar);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private void addFormField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0; gbc.gridy = row;
        gbc.fill = GridBagConstraints.NONE;
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(new Color(44, 62, 80));
        panel.add(label, gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        field.setFont(new Font("Arial", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        panel.add(field, gbc);
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
    
    private void crearAdministrador() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }
        
        String nombre = txtNombre.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String cedula = txtCedula.getText().trim();
        String correo = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());
        
        try {
            if (controller.crearAdministrador(nombre, apellidos, cedula, correo, contrasena)) {
                JOptionPane.showMessageDialog(this, 
                    "Administrador creado exitosamente.", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al crear el administrador. Verifique los datos.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean validarCampos() {
        // Validar campos vacíos
        if (txtNombre.getText().trim().isEmpty() ||
            txtApellidos.getText().trim().isEmpty() ||
            txtCedula.getText().trim().isEmpty() ||
            txtCorreo.getText().trim().isEmpty() ||
            txtContrasena.getPassword().length == 0 ||
            txtConfirmarContrasena.getPassword().length == 0) {
            
            JOptionPane.showMessageDialog(this, 
                "Todos los campos son obligatorios.", 
                "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar formato de correo
        String correo = txtCorreo.getText().trim();
        if (!correo.contains("@") || !correo.contains(".")) {
            JOptionPane.showMessageDialog(this, 
                "El formato del correo electrónico no es válido.", 
                "Correo Inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar contraseñas coincidentes
        String contrasena = new String(txtContrasena.getPassword());
        String confirmarContrasena = new String(txtConfirmarContrasena.getPassword());
        
        if (!contrasena.equals(confirmarContrasena)) {
            JOptionPane.showMessageDialog(this, 
                "Las contraseñas no coinciden.", 
                "Contraseñas Diferentes", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar longitud mínima de contraseña
        if (contrasena.length() < 6) {
            JOptionPane.showMessageDialog(this, 
                "La contraseña debe tener al menos 6 caracteres.", 
                "Contraseña Débil", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
}