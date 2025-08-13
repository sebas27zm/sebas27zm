/**
 * Sistema Bancario - Módulo de Interfaz de Usuario
 * 
 * Este módulo contiene la interfaz gráfica desarrollada con Java Swing
 * y toda la lógica de presentación e interacción con el usuario.
 * 
 * @author Sistema Bancario Team
 * @version 1.0.0
 */
module sistemabancario.ui {
    // Dependencias del JDK
    requires java.base;
    requires java.desktop;          // Para Java Swing
    
    // Dependencias de otros módulos del sistema
    requires sistemabancario.core;  // Para acceder a modelos y controladores
    requires sistemabancario.data;  // Para acceder a DAOs
    
    // Exportar el paquete principal de la aplicación (para ejecución)
    exports com.sistemabancario.app;
    
    // No exportamos los paquetes de vista para mantener encapsulación
    // - com.sistemabancario.view (paquetes internos de UI)
    
    // Punto de entrada de la aplicación
    // La clase principal debe estar en el paquete exportado
}