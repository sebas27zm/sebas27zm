public abstract class Usuario {
    protected String nombre;
    protected String apellidos;
    protected String numeroCedula;
    protected String correoElectronico;
    protected String contrasena;
    
    public Usuario(String nombre, String apellidos, String numeroCedula, String correoElectronico, String contrasena) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numeroCedula = numeroCedula;
        this.correoElectronico = correoElectronico;
        this.contrasena = contrasena;
    }
    
    // Getters
    public String getNombre() { return nombre; }
    public String getApellidos() { return apellidos; }
    public String getNumeroCedula() { return numeroCedula; }
    public String getCorreoElectronico() { return correoElectronico; }
    public String getContrasena() { return contrasena; }
    
    // Setters
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public void setNumeroCedula(String numeroCedula) { this.numeroCedula = numeroCedula; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
    
    public boolean validarCredenciales(String correo, String contrasena) {
        return this.correoElectronico.equals(correo) && this.contrasena.equals(contrasena);
    }
    
    @Override
    public String toString() {
        return "Nombre: " + nombre + " " + apellidos + 
               "\nCédula: " + numeroCedula + 
               "\nCorreo: " + correoElectronico;
    }
}