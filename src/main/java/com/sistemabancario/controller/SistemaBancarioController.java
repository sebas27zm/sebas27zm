package com.sistemabancario.controller;

import com.sistemabancario.dao.*;
import com.sistemabancario.model.*;
import java.util.List;

public class SistemaBancarioController {
    private IAdministradorDAO administradorDAO;
    private IClienteDAO clienteDAO;
    private ICuentaDAO cuentaDAO;
    
    public SistemaBancarioController() {
        this.administradorDAO = new AdministradorDAOImpl();
        this.clienteDAO = new ClienteDAOImpl();
        // TODO: Implement CuentaDAOImpl
        // this.cuentaDAO = new CuentaDAOImpl();
    }
    
    // Métodos de gestión de administrador
    public boolean crearAdministrador(String nombre, String apellidos, String cedula, String correo, String contrasena) {
        if (administradorDAO.existe()) {
            return false; // Ya existe un administrador
        }
        
        Administrador administrador = new Administrador(nombre, apellidos, cedula, correo, contrasena);
        return administradorDAO.crear(administrador);
    }
    
    public boolean validarAdministrador(String correo, String contrasena) {
        Administrador admin = administradorDAO.obtener();
        return admin != null && admin.validarCredenciales(correo, contrasena);
    }
    
    public boolean existeAdministrador() {
        return administradorDAO.existe();
    }
    
    public Administrador obtenerAdministrador() {
        return administradorDAO.obtener();
    }
    
    // Métodos de gestión de clientes
    public boolean registrarCliente(String nombre, String apellidos, String cedula, String correo, 
                                   String contrasena, String sexo, String profesion, String direccion) {
        if (clienteDAO.existeCorreo(correo)) {
            return false; // Ya existe un cliente con ese correo
        }
        
        Cliente cliente = new Cliente(nombre, apellidos, cedula, correo, contrasena, sexo, profesion, direccion);
        return clienteDAO.crear(cliente);
    }
    
    public Cliente validarCliente(String correo, String contrasena) {
        Cliente cliente = clienteDAO.obtenerPorCorreo(correo);
        if (cliente != null && cliente.validarCredenciales(correo, contrasena)) {
            return cliente;
        }
        return null;
    }
    
    public List<Cliente> obtenerTodosLosClientes() {
        return clienteDAO.obtenerTodos();
    }
    
    public boolean hayClientes() {
        return !clienteDAO.obtenerTodos().isEmpty();
    }
    
    public Cliente obtenerClientePorCorreo(String correo) {
        return clienteDAO.obtenerPorCorreo(correo);
    }
    
    // Métodos de gestión de cuentas (temporal - sin DAO implementado aún)
    public boolean crearCuentaAhorro(Cliente cliente, double saldo, double interes) {
        try {
            CuentaAhorro cuenta = new CuentaAhorro(cliente, saldo, interes);
            cliente.agregarCuenta(cuenta);
            return true;
        } catch (Exception e) {
            System.err.println("Error al crear cuenta de ahorro: " + e.getMessage());
            return false;
        }
    }
    
    public boolean crearCuentaDebito(Cliente cliente, double saldo, double interes) {
        try {
            CuentaDebito cuenta = new CuentaDebito(cliente, saldo, interes);
            cliente.agregarCuenta(cuenta);
            return true;
        } catch (Exception e) {
            System.err.println("Error al crear cuenta de débito: " + e.getMessage());
            return false;
        }
    }
    
    public boolean crearCuentaCredito(Cliente cliente, double limite, String tipo) {
        try {
            CuentaCredito cuenta = new CuentaCredito(cliente, limite, tipo);
            cliente.agregarCuenta(cuenta);
            return true;
        } catch (Exception e) {
            System.err.println("Error al crear cuenta de crédito: " + e.getMessage());
            return false;
        }
    }
    
    // Métodos de transacciones
    public boolean realizarDeposito(Cuenta cuenta, double monto) {
        if (cuenta instanceof CuentaAhorro) {
            return ((CuentaAhorro) cuenta).depositar(monto);
        } else if (cuenta instanceof CuentaDebito) {
            return ((CuentaDebito) cuenta).depositar(monto);
        }
        return false;
    }
    
    public boolean realizarRetiro(Cuenta cuenta, double monto) {
        return cuenta.retirar(monto);
    }
    
    public boolean realizarPago(Cuenta cuenta, double monto) {
        return cuenta.pagar(monto);
    }
    
    public boolean realizarAbono(Cuenta cuenta, double monto) {
        if (cuenta instanceof CuentaCredito) {
            return ((CuentaCredito) cuenta).abonar(monto);
        }
        return false;
    }
    
    public void generarInteresesCliente(Cliente cliente) {
        for (Cuenta cuenta : cliente.getCuentas()) {
            if (cuenta instanceof CuentaAhorro) {
                ((CuentaAhorro) cuenta).generarIntereses();
            } else if (cuenta instanceof CuentaDebito) {
                ((CuentaDebito) cuenta).generarIntereses();
            }
        }
    }
    
    public boolean tieneIntereses(Cliente cliente) {
        for (Cuenta cuenta : cliente.getCuentas()) {
            if (cuenta instanceof CuentaAhorro || cuenta instanceof CuentaDebito) {
                return true;
            }
        }
        return false;
    }
    
    public Cuenta buscarCuentaPorNumero(int numeroCuenta, List<Cliente> clientes) {
        for (Cliente cliente : clientes) {
            for (Cuenta cuenta : cliente.getCuentas()) {
                if (cuenta.getNumeroCuenta() == numeroCuenta) {
                    return cuenta;
                }
            }
        }
        return null;
    }
    
    public boolean cambiarEstadoCuenta(int numeroCuenta, List<Cliente> clientes) {
        Cuenta cuenta = buscarCuentaPorNumero(numeroCuenta, clientes);
        if (cuenta != null) {
            cuenta.setActiva(!cuenta.isActiva());
            return true;
        }
        return false;
    }
}