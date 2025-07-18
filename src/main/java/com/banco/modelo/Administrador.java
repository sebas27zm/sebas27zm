package com.banco.modelo;

public class Administrador extends Usuario {
    
    public Administrador(String nombre, String apellidos, String cedula, String correoElectronico, String contrasena) {
        super(nombre, apellidos, cedula, correoElectronico, contrasena);
    }
    
    @Override
    public String toString() {
        return "=== ADMINISTRADOR ===\n" + super.toString();
    }
}