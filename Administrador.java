public class Administrador extends Usuario {
    
    public Administrador(String nombre, String apellidos, String numeroCedula, String correoElectronico, String contrasena) {
        super(nombre, apellidos, numeroCedula, correoElectronico, contrasena);
    }
    
    @Override
    public String toString() {
        return "=== ADMINISTRADOR ===\n" + super.toString();
    }
}