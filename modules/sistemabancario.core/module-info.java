/**
 * Sistema Bancario - Módulo Core
 * 
 * Este módulo contiene la lógica de negocio principal y los modelos de datos.
 * Es el núcleo del sistema bancario sin dependencias de UI o persistencia específica.
 * 
 * @author Sistema Bancario Team
 * @version 1.0.0
 */
module sistemabancario.core {
    // Dependencias mínimas del JDK
    requires java.base;
    
    // Exportar el modelo de datos público
    exports com.sistemabancario.model;
    
    // Exportar el controlador de negocio
    exports com.sistemabancario.controller;
    
    // Exportar las interfaces de servicios
    exports com.sistemabancario.service;
    
    // No exportamos paquetes internos para mantener encapsulación
}