package com.banco.modelo;

public abstract class Usuario {
    protected String nombre;
    protected String apellidos;
    protected String cedula;
    protected String correoElectronico;
    protected String contrasena;
    
    public Usuario(String nombre, String apellidos, String cedula, String correoElectronico, String contrasena) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.cedula = cedula;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
    }
    
    // Getters y setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    
    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }
    
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    
    public boolean validarCredenciales(String correo, String contrasena) {
        return this.correoElectronico.equals(correo) && this.contrasena.equals(contrasena);
    }
    
    @Override
    public String toString() {
        return "Nombre: " + nombre + " " + apellidos + 
               "\nCédula: " + cedula + 
               "\nCorreo: " + correoElectronico;
    }
}