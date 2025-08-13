package com.sistemabancario.app;

import com.sistemabancario.service.BankingService;
import com.sistemabancario.service.impl.BankingServiceImpl;
import com.sistemabancario.view.SistemaBancarioGUI;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.JOptionPane;

/**
 * Clase principal de la aplicación Sistema Bancario.
 * 
 * Esta clase actúa como punto de entrada de la aplicación modular,
 * inicializando los servicios necesarios y lanzando la interfaz gráfica.
 * 
 * Características de la aplicación:
 * - Arquitectura modular con JPMS
 * - Patrón MVC (Model-View-Controller)
 * - Patrón DAO (Data Access Object)
 * - Patrón Service (Fachada de servicios)
 * - Interfaz gráfica con Java Swing
 * - Persistencia con MySQL
 * 
 * @author Sistema Bancario Team
 * @version 1.0.0
 * @since Java 11
 */
public class SistemaBancarioApp {
    
    private static final String APP_NAME = "Sistema Bancario";
    private static final String APP_VERSION = "1.0.0";
    
    /**
     * Punto de entrada principal de la aplicación.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Configurar propiedades del sistema
        System.setProperty("java.awt.headless", "false");
        
        // Mostrar información de inicio
        logStartupInfo();
        
        // Inicializar la aplicación en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            try {
                initializeApplication();
            } catch (Exception e) {
                handleStartupError(e);
            }
        });
    }
    
    /**
     * Inicializa la aplicación configurando el Look and Feel y creando la GUI.
     */
    private static void initializeApplication() {
        try {
            // Configurar Look and Feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
            
            // Configurar propiedades adicionales de la UI
            configureUIProperties();
            
            // Inicializar servicios
            BankingService bankingService = initializeServices();
            
            // Crear y mostrar la interfaz principal
            SistemaBancarioGUI mainWindow = new SistemaBancarioGUI();
            mainWindow.setVisible(true);
            
            logSuccessfulStartup();
            
        } catch (Exception e) {
            System.err.println("Error al inicializar la aplicación: " + e.getMessage());
            e.printStackTrace();
            
            // Mostrar error al usuario
            JOptionPane.showMessageDialog(null, 
                "Error al inicializar la aplicación:\n" + e.getMessage(), 
                "Error de Inicio", 
                JOptionPane.ERROR_MESSAGE);
            
            System.exit(1);
        }
    }
    
    /**
     * Configura propiedades adicionales de la interfaz de usuario.
     */
    private static void configureUIProperties() {
        // Configurar propiedades de Swing para mejor apariencia
        System.setProperty("swing.aatext", "true");
        System.setProperty("awt.useSystemAAFontSettings", "on");
        
        // Configurar título de ventanas por defecto
        System.setProperty("swing.defaultlaf", UIManager.getSystemLookAndFeel().getClass().getName());
    }
    
    /**
     * Inicializa los servicios de la aplicación.
     * 
     * @return El servicio principal de banking
     */
    private static BankingService initializeServices() {
        System.out.println("Inicializando servicios del sistema bancario...");
        
        // Crear el servicio principal
        BankingService bankingService = new BankingServiceImpl();
        
        System.out.println("Servicios inicializados correctamente.");
        return bankingService;
    }
    
    /**
     * Registra información de inicio de la aplicación.
     */
    private static void logStartupInfo() {
        System.out.println("=".repeat(60));
        System.out.println("  " + APP_NAME + " v" + APP_VERSION);
        System.out.println("  Sistema de Gestión de Cuentas Bancarias");
        System.out.println("=".repeat(60));
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Java Vendor: " + System.getProperty("java.vendor"));
        System.out.println("OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
        System.out.println("Arquitectura: " + System.getProperty("os.arch"));
        System.out.println("Usuario: " + System.getProperty("user.name"));
        System.out.println("Directorio de trabajo: " + System.getProperty("user.dir"));
        System.out.println("-".repeat(60));
        System.out.println("Iniciando aplicación...");
    }
    
    /**
     * Registra el inicio exitoso de la aplicación.
     */
    private static void logSuccessfulStartup() {
        System.out.println("✓ Aplicación iniciada correctamente");
        System.out.println("✓ Interfaz gráfica cargada");
        System.out.println("✓ Servicios disponibles");
        System.out.println("-".repeat(60));
        System.out.println("Sistema Bancario listo para usar.");
    }
    
    /**
     * Maneja errores durante el inicio de la aplicación.
     * 
     * @param e Excepción ocurrida
     */
    private static void handleStartupError(Exception e) {
        System.err.println("✗ Error crítico durante el inicio de la aplicación");
        System.err.println("Detalles del error: " + e.getMessage());
        e.printStackTrace();
        
        // Mostrar diálogo de error
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(null,
                "<html><body style='width: 300px'>" +
                "<h3>Error de Inicio</h3>" +
                "<p>No se pudo inicializar el Sistema Bancario.</p>" +
                "<p><b>Error:</b> " + e.getMessage() + "</p>" +
                "<p>Por favor, verifique:</p>" +
                "<ul>" +
                "<li>Conexión a la base de datos MySQL</li>" +
                "<li>Configuración de credenciales</li>" +
                "<li>Dependencias del sistema</li>" +
                "</ul>" +
                "</body></html>",
                "Sistema Bancario - Error",
                JOptionPane.ERROR_MESSAGE
            );
        });
        
        System.exit(1);
    }
    
    /**
     * Obtiene la versión de la aplicación.
     * 
     * @return Versión de la aplicación
     */
    public static String getVersion() {
        return APP_VERSION;
    }
    
    /**
     * Obtiene el nombre de la aplicación.
     * 
     * @return Nombre de la aplicación
     */
    public static String getAppName() {
        return APP_NAME;
    }
}