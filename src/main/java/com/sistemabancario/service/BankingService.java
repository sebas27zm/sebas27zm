package com.sistemabancario.service;

import com.sistemabancario.model.*;
import java.util.List;

/**
 * Servicio principal del sistema bancario.
 * 
 * Esta interfaz define las operaciones principales del sistema bancario
 * y actúa como una fachada para la lógica de negocio, promoviendo
 * el desacoplamiento entre capas.
 * 
 * @author Sistema Bancario Team
 * @version 1.0.0
 */
public interface BankingService {
    
    // ==================== GESTIÓN DE ADMINISTRADORES ====================
    
    /**
     * Crea un nuevo administrador en el sistema.
     * Solo puede existir un administrador.
     * 
     * @param nombre Nombre del administrador
     * @param apellidos Apellidos del administrador
     * @param cedula Número de cédula
     * @param correo Correo electrónico
     * @param contrasena Contraseña
     * @return true si se creó exitosamente, false si ya existe un administrador
     */
    boolean crearAdministrador(String nombre, String apellidos, String cedula, 
                              String correo, String contrasena);
    
    /**
     * Valida las credenciales del administrador.
     * 
     * @param correo Correo electrónico
     * @param contrasena Contraseña
     * @return true si las credenciales son válidas
     */
    boolean validarAdministrador(String correo, String contrasena);
    
    /**
     * Verifica si existe un administrador en el sistema.
     * 
     * @return true si existe un administrador
     */
    boolean existeAdministrador();
    
    // ==================== GESTIÓN DE CLIENTES ====================
    
    /**
     * Registra un nuevo cliente en el sistema.
     * 
     * @param nombre Nombre del cliente
     * @param apellidos Apellidos del cliente
     * @param cedula Número de cédula
     * @param correo Correo electrónico (debe ser único)
     * @param contrasena Contraseña
     * @param sexo Sexo del cliente
     * @param profesion Profesión del cliente
     * @param direccion Dirección del cliente
     * @return true si se registró exitosamente
     */
    boolean registrarCliente(String nombre, String apellidos, String cedula, 
                           String correo, String contrasena, String sexo, 
                           String profesion, String direccion);
    
    /**
     * Valida las credenciales de un cliente.
     * 
     * @param correo Correo electrónico
     * @param contrasena Contraseña
     * @return El cliente si las credenciales son válidas, null en caso contrario
     */
    Cliente validarCliente(String correo, String contrasena);
    
    /**
     * Obtiene todos los clientes registrados.
     * 
     * @return Lista de todos los clientes
     */
    List<Cliente> obtenerTodosLosClientes();
    
    /**
     * Verifica si existen clientes registrados.
     * 
     * @return true si hay al menos un cliente
     */
    boolean hayClientes();
    
    // ==================== GESTIÓN DE CUENTAS ====================
    
    /**
     * Crea una nueva cuenta de ahorro.
     * 
     * @param cliente Cliente propietario
     * @param saldo Saldo inicial (mínimo $100)
     * @param interes Porcentaje de interés
     * @return true si se creó exitosamente
     */
    boolean crearCuentaAhorro(Cliente cliente, double saldo, double interes);
    
    /**
     * Crea una nueva cuenta de débito.
     * 
     * @param cliente Cliente propietario
     * @param saldo Saldo inicial (no negativo)
     * @param interes Porcentaje de interés
     * @return true si se creó exitosamente
     */
    boolean crearCuentaDebito(Cliente cliente, double saldo, double interes);
    
    /**
     * Crea una nueva cuenta de crédito.
     * 
     * @param cliente Cliente propietario
     * @param limite Límite de crédito
     * @param tipo Tipo de crédito (Cashback, Gane Premios, etc.)
     * @return true si se creó exitosamente
     */
    boolean crearCuentaCredito(Cliente cliente, double limite, String tipo);
    
    /**
     * Obtiene todas las cuentas de un tipo específico.
     * 
     * @param tipo Tipo de cuenta ("Ahorro", "Débito", "Crédito")
     * @return Lista de cuentas del tipo especificado
     */
    List<Cuenta> obtenerCuentasPorTipo(String tipo);
    
    /**
     * Busca una cuenta por su número.
     * 
     * @param numeroCuenta Número de la cuenta
     * @return La cuenta si existe, null en caso contrario
     */
    Cuenta buscarCuentaPorNumero(int numeroCuenta);
    
    /**
     * Cambia el estado (activa/inactiva) de una cuenta.
     * 
     * @param numeroCuenta Número de la cuenta
     * @return true si se cambió exitosamente
     */
    boolean cambiarEstadoCuenta(int numeroCuenta);
    
    // ==================== TRANSACCIONES ====================
    
    /**
     * Realiza un depósito en una cuenta.
     * Solo disponible para cuentas de ahorro y débito.
     * 
     * @param cuenta Cuenta destino
     * @param monto Monto a depositar
     * @return true si la transacción fue exitosa
     */
    boolean realizarDeposito(Cuenta cuenta, double monto);
    
    /**
     * Realiza un retiro de una cuenta.
     * 
     * @param cuenta Cuenta origen
     * @param monto Monto a retirar
     * @return true si la transacción fue exitosa
     */
    boolean realizarRetiro(Cuenta cuenta, double monto);
    
    /**
     * Realiza un pago desde una cuenta.
     * 
     * @param cuenta Cuenta origen
     * @param monto Monto del pago
     * @return true si la transacción fue exitosa
     */
    boolean realizarPago(Cuenta cuenta, double monto);
    
    /**
     * Realiza un abono a una cuenta de crédito.
     * 
     * @param cuenta Cuenta de crédito
     * @param monto Monto del abono
     * @return true si la transacción fue exitosa
     */
    boolean realizarAbono(Cuenta cuenta, double monto);
    
    /**
     * Genera intereses para todas las cuentas aplicables de un cliente.
     * 
     * @param cliente Cliente propietario de las cuentas
     */
    void generarInteresesCliente(Cliente cliente);
    
    /**
     * Verifica si un cliente tiene cuentas que generan intereses.
     * 
     * @param cliente Cliente a verificar
     * @return true si tiene cuentas con intereses
     */
    boolean tieneIntereses(Cliente cliente);
}