package com.sistemabancario.service.impl;

import com.sistemabancario.controller.SistemaBancarioController;
import com.sistemabancario.model.*;
import com.sistemabancario.service.BankingService;
import java.util.List;

/**
 * Implementación del servicio principal del sistema bancario.
 * 
 * Esta clase actúa como una fachada que delega las operaciones
 * al controlador principal, proporcionando una interfaz más limpia
 * y modular para el acceso a la lógica de negocio.
 * 
 * @author Sistema Bancario Team
 * @version 1.0.0
 */
public class BankingServiceImpl implements BankingService {
    
    private final SistemaBancarioController controller;
    
    /**
     * Constructor que inicializa el servicio con el controlador.
     */
    public BankingServiceImpl() {
        this.controller = new SistemaBancarioController();
    }
    
    /**
     * Constructor para inyección de dependencias (para testing).
     * 
     * @param controller Controlador a usar
     */
    public BankingServiceImpl(SistemaBancarioController controller) {
        this.controller = controller;
    }
    
    // ==================== IMPLEMENTACIÓN DE GESTIÓN DE ADMINISTRADORES ====================
    
    @Override
    public boolean crearAdministrador(String nombre, String apellidos, String cedula, 
                                     String correo, String contrasena) {
        return controller.crearAdministrador(nombre, apellidos, cedula, correo, contrasena);
    }
    
    @Override
    public boolean validarAdministrador(String correo, String contrasena) {
        return controller.validarAdministrador(correo, contrasena);
    }
    
    @Override
    public boolean existeAdministrador() {
        return controller.existeAdministrador();
    }
    
    // ==================== IMPLEMENTACIÓN DE GESTIÓN DE CLIENTES ====================
    
    @Override
    public boolean registrarCliente(String nombre, String apellidos, String cedula, 
                                   String correo, String contrasena, String sexo, 
                                   String profesion, String direccion) {
        return controller.registrarCliente(nombre, apellidos, cedula, correo, 
                                         contrasena, sexo, profesion, direccion);
    }
    
    @Override
    public Cliente validarCliente(String correo, String contrasena) {
        return controller.validarCliente(correo, contrasena);
    }
    
    @Override
    public List<Cliente> obtenerTodosLosClientes() {
        return controller.obtenerTodosLosClientes();
    }
    
    @Override
    public boolean hayClientes() {
        return controller.hayClientes();
    }
    
    // ==================== IMPLEMENTACIÓN DE GESTIÓN DE CUENTAS ====================
    
    @Override
    public boolean crearCuentaAhorro(Cliente cliente, double saldo, double interes) {
        return controller.crearCuentaAhorro(cliente, saldo, interes);
    }
    
    @Override
    public boolean crearCuentaDebito(Cliente cliente, double saldo, double interes) {
        return controller.crearCuentaDebito(cliente, saldo, interes);
    }
    
    @Override
    public boolean crearCuentaCredito(Cliente cliente, double limite, String tipo) {
        return controller.crearCuentaCredito(cliente, limite, tipo);
    }
    
    @Override
    public List<Cuenta> obtenerCuentasPorTipo(String tipo) {
        // Esta funcionalidad necesita ser implementada en el controlador
        // Por ahora, devolvemos una lista vacía como placeholder
        return List.of();
    }
    
    @Override
    public Cuenta buscarCuentaPorNumero(int numeroCuenta) {
        return controller.buscarCuentaPorNumero(numeroCuenta, controller.obtenerTodosLosClientes());
    }
    
    @Override
    public boolean cambiarEstadoCuenta(int numeroCuenta) {
        return controller.cambiarEstadoCuenta(numeroCuenta, controller.obtenerTodosLosClientes());
    }
    
    // ==================== IMPLEMENTACIÓN DE TRANSACCIONES ====================
    
    @Override
    public boolean realizarDeposito(Cuenta cuenta, double monto) {
        return controller.realizarDeposito(cuenta, monto);
    }
    
    @Override
    public boolean realizarRetiro(Cuenta cuenta, double monto) {
        return controller.realizarRetiro(cuenta, monto);
    }
    
    @Override
    public boolean realizarPago(Cuenta cuenta, double monto) {
        return controller.realizarPago(cuenta, monto);
    }
    
    @Override
    public boolean realizarAbono(Cuenta cuenta, double monto) {
        return controller.realizarAbono(cuenta, monto);
    }
    
    @Override
    public void generarInteresesCliente(Cliente cliente) {
        controller.generarInteresesCliente(cliente);
    }
    
    @Override
    public boolean tieneIntereses(Cliente cliente) {
        return controller.tieneIntereses(cliente);
    }
}