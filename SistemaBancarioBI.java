import java.util.ArrayList;
import java.util.List;

public class SistemaBancarioBI {
    private Administrador administrador;
    private List<Cliente> clientes;
    
    public SistemaBancarioBI() {
        this.clientes = new ArrayList<>();
    }
    
    // Métodos de gestión de administrador
    public boolean crearAdministrador(String nombre, String apellidos, String cedula, String correo, String contrasena) {
        if (administrador != null) {
            return false; // Ya existe un administrador
        }
        administrador = new Administrador(nombre, apellidos, cedula, correo, contrasena);
        return true;
    }
    
    public boolean validarAdministrador(String correo, String contrasena) {
        return administrador != null && administrador.validarCredenciales(correo, contrasena);
    }
    
    public boolean existeAdministrador() {
        return administrador != null;
    }
    
    // Métodos de gestión de clientes
    public boolean registrarCliente(String nombre, String apellidos, String cedula, String correo, 
                                   String contrasena, String sexo, String profesion, String direccion) {
        // Verificar si ya existe un cliente con el mismo correo
        for (Cliente cliente : clientes) {
            if (cliente.getCorreoElectronico().equals(correo)) {
                return false;
            }
        }
        
        Cliente cliente = new Cliente(nombre, apellidos, cedula, correo, contrasena, sexo, profesion, direccion);
        clientes.add(cliente);
        return true;
    }
    
    public Cliente validarCliente(String correo, String contrasena) {
        for (Cliente cliente : clientes) {
            if (cliente.validarCredenciales(correo, contrasena)) {
                return cliente;
            }
        }
        return null;
    }
    
    public List<Cliente> obtenerClientes() {
        return new ArrayList<>(clientes);
    }
    
    public boolean hayClientes() {
        return !clientes.isEmpty();
    }
    
    // Métodos de gestión de cuentas
    public boolean crearCuentaAhorro(Cliente cliente, double saldo, double interes) {
        try {
            CuentaAhorro cuenta = new CuentaAhorro(cliente, saldo, interes);
            cliente.agregarCuenta(cuenta);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean crearCuentaDebito(Cliente cliente, double saldo, double interes) {
        try {
            CuentaDebito cuenta = new CuentaDebito(cliente, saldo, interes);
            cliente.agregarCuenta(cuenta);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean crearCuentaCredito(Cliente cliente, double limite, String tipo) {
        try {
            CuentaCredito cuenta = new CuentaCredito(cliente, limite, tipo);
            cliente.agregarCuenta(cuenta);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public List<Cuenta> obtenerCuentasPorTipo(String tipo) {
        List<Cuenta> cuentasFiltradas = new ArrayList<>();
        
        for (Cliente cliente : clientes) {
            for (Cuenta cuenta : cliente.getCuentas()) {
                String tipoCuenta = cuenta.getTipoCuenta();
                if ((tipo.equals("Ahorro") && tipoCuenta.contains("Ahorro")) ||
                    (tipo.equals("Débito") && tipoCuenta.contains("Débito")) ||
                    (tipo.equals("Crédito") && tipoCuenta.contains("Crédito"))) {
                    cuentasFiltradas.add(cuenta);
                }
            }
        }
        
        return cuentasFiltradas;
    }
    
    public Cuenta buscarCuentaPorNumero(int numeroCuenta) {
        for (Cliente cliente : clientes) {
            for (Cuenta cuenta : cliente.getCuentas()) {
                if (cuenta.getNumeroCuenta() == numeroCuenta) {
                    return cuenta;
                }
            }
        }
        return null;
    }
    
    public boolean cambiarEstadoCuenta(int numeroCuenta) {
        Cuenta cuenta = buscarCuentaPorNumero(numeroCuenta);
        if (cuenta != null) {
            cuenta.setActiva(!cuenta.isActiva());
            return true;
        }
        return false;
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
}