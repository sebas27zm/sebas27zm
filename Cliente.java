import java.util.ArrayList;
import java.util.List;

public class Cliente extends Usuario implements IGestionCuentas {
    private String sexo;
    private String profesion;
    private String direccion;
    private List<Cuenta> cuentas;
    
    public Cliente(String nombre, String apellidos, String numeroCedula, String correoElectronico, 
                   String contrasena, String sexo, String profesion, String direccion) {
        super(nombre, apellidos, numeroCedula, correoElectronico, contrasena);
        this.sexo = sexo;
        this.profesion = profesion;
        this.direccion = direccion;
        this.cuentas = new ArrayList<>();
    }
    
    // Getters adicionales
    public String getSexo() { return sexo; }
    public String getProfesion() { return profesion; }
    public String getDireccion() { return direccion; }
    public List<Cuenta> getCuentas() { return cuentas; }
    
    // Setters adicionales
    public void setSexo(String sexo) { this.sexo = sexo; }
    public void setProfesion(String profesion) { this.profesion = profesion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    
    @Override
    public void agregarCuenta(Cuenta cuenta) {
        cuentas.add(cuenta);
    }
    
    @Override
    public void removerCuenta(Cuenta cuenta) {
        cuentas.remove(cuenta);
    }
    
    @Override
    public String toString() {
        return "=== CLIENTE ===\n" + super.toString() + 
               "\nSexo: " + sexo + 
               "\nProfesión: " + profesion + 
               "\nDirección: " + direccion +
               "\nCantidad de cuentas: " + cuentas.size();
    }
    
    public String reporteCuentas() {
        StringBuilder reporte = new StringBuilder();
        reporte.append("=== REPORTE DE CUENTAS PARA ").append(nombre).append(" ").append(apellidos).append(" ===\n");
        
        if (cuentas.isEmpty()) {
            reporte.append("No tiene cuentas registradas.\n");
        } else {
            for (int i = 0; i < cuentas.size(); i++) {
                reporte.append("\n--- Cuenta ").append(i + 1).append(" ---\n");
                reporte.append(cuentas.get(i).toString()).append("\n");
            }
        }
        
        return reporte.toString();
    }
}