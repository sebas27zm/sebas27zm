/**
 * Sistema Bancario - Módulo Principal
 * 
 * Este módulo contiene la aplicación completa del sistema bancario
 * incluyendo la interfaz gráfica, lógica de negocio y acceso a datos.
 * 
 * @author Sistema Bancario Team
 * @version 1.0.0
 * @since Java 11
 */
module com.sistemabancario {
    // Dependencias de módulos del JDK
    requires java.desktop;          // Para Java Swing (GUI)
    requires java.sql;              // Para JDBC (acceso a base de datos)
    requires java.base;             // Módulo base (implícito, pero explícito para claridad)
    
    // Dependencias externas
    requires mysql.connector.java;  // Driver MySQL
    
    // Dependencias opcionales para testing (si se necesitan)
    requires static org.junit.jupiter.api;
    requires static org.junit.jupiter.engine;
    
    // Exportar paquetes públicos
    // Modelo de datos - disponible para otros módulos que lo necesiten
    exports com.sistemabancario.model;
    
    // Controlador - para permitir extensiones del sistema
    exports com.sistemabancario.controller;
    
    // Interfaces DAO - para permitir implementaciones alternativas
    exports com.sistemabancario.dao;
    
    // Paquetes internos no exportados (encapsulación a nivel de módulo):
    // - com.sistemabancario.view (interfaz gráfica interna)
    // - com.sistemabancario.util (utilidades internas)
    
    // Servicios (para futuras extensiones usando ServiceLoader)
    // uses com.sistemabancario.dao.IAdministradorDAO;
    // uses com.sistemabancario.dao.IClienteDAO;
    // uses com.sistemabancario.dao.ICuentaDAO;
    
    // Proveer implementaciones de servicios
    // provides com.sistemabancario.dao.IAdministradorDAO 
    //     with com.sistemabancario.dao.AdministradorDAOImpl;
    // provides com.sistemabancario.dao.IClienteDAO 
    //     with com.sistemabancario.dao.ClienteDAOImpl;
}