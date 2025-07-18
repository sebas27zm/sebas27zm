package com.banco.modelo;

import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario {
    private String sexo;
    private String profesion;
    private String direccion;
    private List<CuentaBancaria> cuentas;
    
    public Cliente(String nombre, String apellidos, String cedula, String correoElectronico, 
                   String contrasena, String sexo, String profesion, String direccion) {
        super(nombre, apellidos, cedula, correoElectronico, contrasena);
        this.sexo = sexo;
        this.profesion = profesion;
        this.direccion = direccion;
        this.cuentas = new ArrayList<>();
    }
    
    // Getters y setters específicos
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    
    public String getProfesion() { return profesion; }
    public void setProfesion(String profesion) { this.profesion = profesion; }
    
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    public List<CuentaBancaria> getCuentas() { return cuentas; }
    
    public void agregarCuenta(CuentaBancaria cuenta) {
        cuentas.add(cuenta);
    }
    
    public void removerCuenta(CuentaBancaria cuenta) {
        cuentas.remove(cuenta);
    }
    
    public String generarReporteCuentas() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE CUENTAS ===\n");
        reporte.append("Cliente: ").append(nombre).append(" ").append(apellidos).append("\n");
        reporte.append("Cédula: ").append(cedula).append("\n\n");
        
        if (cuentas.isEmpty()) {
            reporte.append("No tiene cuentas registradas.\n");
        } else {
            for (int i = 0; i < cuentas.size(); i++) {
                reporte.append("Cuenta #").append(i + 1).append(":\n");
                reporte.append(cuentas.get(i).toString()).append("\n");
            }
        }
        
        return reporte.toString();
    }
    
    @Override
    public String toString() {
        return "=== CLIENTE ===\n" + super.toString() + 
               "\nSexo: " + sexo + 
               "\nProfesión: " + profesion + 
               "\nDirección: " + direccion +
               "\nCantidad de cuentas: " + cuentas.size();
    }
}