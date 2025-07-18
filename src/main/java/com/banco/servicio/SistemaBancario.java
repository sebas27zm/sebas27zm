package com.banco.servicio;

import com.banco.modelo.*;
import java.util.ArrayList;
import java.util.List;

public class SistemaBancario {
    private Administrador administrador;
    private List<Cliente> clientes;
    private List<CuentaBancaria> todasLasCuentas;
    
    public SistemaBancario() {
        this.clientes = new ArrayList<>();
        this.todasLasCuentas = new ArrayList<>();
    }
    
    // Gestión del administrador
    public boolean crearAdministrador(String nombre, String apellidos, String cedula, 
                                     String correoElectronico, String contrasena) {
        if (administrador != null) {
            System.out.println("Ya existe un administrador registrado.");
            return false;
        }
        administrador = new Administrador(nombre, apellidos, cedula, correoElectronico, contrasena);
        System.out.println("Administrador creado exitosamente.");
        return true;
    }
    
    public Administrador getAdministrador() {
        return administrador;
    }
    
    public boolean existeAdministrador() {
        return administrador != null;
    }
    
    // Gestión de clientes
    public boolean crearCliente(String nombre, String apellidos, String cedula, String correoElectronico,
                               String contrasena, String sexo, String profesion, String direccion) {
        if (!existeAdministrador()) {
            System.out.println("No se puede crear cliente. Primero debe registrar un administrador.");
            return false;
        }
        
        // Verificar que no exista un cliente con la misma cédula
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                System.out.println("Ya existe un cliente con esa cédula.");
                return false;
            }
        }
        
        Cliente nuevoCliente = new Cliente(nombre, apellidos, cedula, correoElectronico, 
                                         contrasena, sexo, profesion, direccion);
        clientes.add(nuevoCliente);
        System.out.println("Cliente creado exitosamente.");
        return true;
    }
    
    public List<Cliente> getClientes() {
        return clientes;
    }
    
    // Autenticación
    public Administrador autenticarAdministrador(String correo, String contrasena) {
        if (administrador != null && administrador.validarCredenciales(correo, contrasena)) {
            return administrador;
        }
        return null;
    }
    
    public Cliente autenticarCliente(String correo, String contrasena) {
        for (Cliente cliente : clientes) {
            if (cliente.validarCredenciales(correo, contrasena)) {
                return cliente;
            }
        }
        return null;
    }
    
    // Gestión de cuentas
    public boolean crearCuentaAhorro(Cliente cliente, double saldoInicial, double porcentajeInteres) {
        try {
            CuentaAhorro cuenta = new CuentaAhorro(cliente, saldoInicial, porcentajeInteres);
            cliente.agregarCuenta(cuenta);
            todasLasCuentas.add(cuenta);
            System.out.println("Cuenta de ahorro creada exitosamente. Número: " + cuenta.getNumeroCuenta());
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("Error al crear cuenta de ahorro: " + e.getMessage());
            return false;
        }
    }
    
    public boolean crearCuentaDebito(Cliente cliente, double saldoInicial, double porcentajeInteres) {
        CuentaDebito cuenta = new CuentaDebito(cliente, saldoInicial, porcentajeInteres);
        cliente.agregarCuenta(cuenta);
        todasLasCuentas.add(cuenta);
        System.out.println("Cuenta de débito creada exitosamente. Número: " + cuenta.getNumeroCuenta());
        return true;
    }
    
    public boolean crearCuentaCredito(Cliente cliente, double limiteCredito, String tipo) {
        CuentaCredito cuenta = new CuentaCredito(cliente, limiteCredito, tipo);
        cliente.agregarCuenta(cuenta);
        todasLasCuentas.add(cuenta);
        System.out.println("Cuenta de crédito creada exitosamente. Número: " + cuenta.getNumeroCuenta());
        return true;
    }
    
    // Reportes
    public String generarReporteClientes() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE CLIENTES ===\n");
        reporte.append("Total de clientes registrados: ").append(clientes.size()).append("\n\n");
        
        for (int i = 0; i < clientes.size(); i++) {
            reporte.append("Cliente #").append(i + 1).append(":\n");
            reporte.append(clientes.get(i).toString()).append("\n\n");
        }
        
        return reporte.toString();
    }
    
    public String generarReporteCuentas() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE TODAS LAS CUENTAS ===\n");
        reporte.append("Total de cuentas registradas: ").append(todasLasCuentas.size()).append("\n\n");
        
        // Contar por tipo
        int cuentasAhorro = 0, cuentasDebito = 0, cuentasCredito = 0;
        for (CuentaBancaria cuenta : todasLasCuentas) {
            if (cuenta instanceof CuentaAhorro) cuentasAhorro++;
            else if (cuenta instanceof CuentaDebito) cuentasDebito++;
            else if (cuenta instanceof CuentaCredito) cuentasCredito++;
        }
        
        reporte.append("Cuentas de Ahorro: ").append(cuentasAhorro).append("\n");
        reporte.append("Cuentas de Débito: ").append(cuentasDebito).append("\n");
        reporte.append("Cuentas de Crédito: ").append(cuentasCredito).append("\n\n");
        
        for (int i = 0; i < todasLasCuentas.size(); i++) {
            reporte.append("Cuenta #").append(i + 1).append(":\n");
            reporte.append(todasLasCuentas.get(i).toString()).append("\n\n");
        }
        
        return reporte.toString();
    }
    
    public List<CuentaBancaria> getTodasLasCuentas() {
        return todasLasCuentas;
    }
}