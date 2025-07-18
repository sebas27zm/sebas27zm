public abstract class Cuenta {
    protected static int contadorCuentas = 1;
    protected int numeroCuenta;
    protected double saldo;
    protected boolean activa;
    protected Cliente propietario;
    
    public Cuenta(Cliente propietario, double saldoInicial) {
        this.numeroCuenta = contadorCuentas++;
        this.saldo = saldoInicial;
        this.activa = true;
        this.propietario = propietario;
    }
    
    // Getters
    public int getNumeroCuenta() { return numeroCuenta; }
    public double getSaldo() { return saldo; }
    public boolean isActiva() { return activa; }
    public Cliente getPropietario() { return propietario; }
    
    // Setters
    public void setActiva(boolean activa) { this.activa = activa; }
    
    // Métodos abstractos que deben implementar las clases hijas
    public abstract boolean retirar(double monto);
    public abstract boolean pagar(double monto);
    public abstract String getTipoCuenta();
    
    protected boolean validarTransaccion(double monto) {
        if (!activa) {
            System.out.println("Error: La cuenta está inactiva. No se pueden realizar transacciones.");
            return false;
        }
        if (monto <= 0) {
            System.out.println("Error: El monto debe ser mayor a 0.");
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Número de cuenta: " + numeroCuenta +
               "\nTipo: " + getTipoCuenta() +
               "\nSaldo: $" + String.format("%.2f", saldo) +
               "\nEstado: " + (activa ? "Activa" : "Inactiva") +
               "\nPropietario: " + propietario.getNombre() + " " + propietario.getApellidos();
    }
}