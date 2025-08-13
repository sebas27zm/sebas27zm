/**
 * Sistema Bancario - Módulo de Datos
 * 
 * Este módulo maneja la persistencia de datos, conexiones a base de datos
 * e implementaciones de los patrones DAO.
 * 
 * @author Sistema Bancario Team
 * @version 1.0.0
 */
module sistemabancario.data {
    // Dependencias del JDK
    requires java.base;
    requires java.sql;              // Para JDBC
    
    // Dependencias de otros módulos del sistema
    requires sistemabancario.core;   // Necesita acceso a los modelos
    
    // Dependencias externas
    requires mysql.connector.java;   // Driver MySQL
    
    // Exportar interfaces DAO públicas
    exports com.sistemabancario.dao;
    
    // Exportar utilidades de conexión si es necesario
    exports com.sistemabancario.util;
    
    // Proveer implementaciones de servicios DAO
    provides com.sistemabancario.dao.IAdministradorDAO 
        with com.sistemabancario.dao.impl.AdministradorDAOImpl;
    provides com.sistemabancario.dao.IClienteDAO 
        with com.sistemabancario.dao.impl.ClienteDAOImpl;
    provides com.sistemabancario.dao.ICuentaDAO 
        with com.sistemabancario.dao.impl.CuentaDAOImpl;
}