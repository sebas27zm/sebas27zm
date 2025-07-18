public class CuentaAhorro extends Cuenta {
    private static final double SALDO_MINIMO = 100.0;
    private double porcentajeInteres;
    
    public CuentaAhorro(Cliente propietario, double saldoInicial, double porcentajeInteres) {
        super(propietario, saldoInicial);
        if (saldoInicial < SALDO_MINIMO) {
            throw new IllegalArgumentException("El saldo inicial debe ser al menos $" + SALDO_MINIMO);
        }
        this.porcentajeInteres = porcentajeInteres;
    }
    
    @Override
    public String getTipoCuenta() {
        return "Cuenta de Ahorro";
    }
    
    public boolean depositar(double monto) {
        if (!validarTransaccion(monto)) {
            return false;
        }
        
        saldo += monto;
        System.out.println("Depósito exitoso. Nuevo saldo: $" + String.format("%.2f", saldo));
        return true;
    }
    
    @Override
    public boolean retirar(double monto) {
        if (!validarTransaccion(monto)) {
            return false;
        }
        
        if (saldo - monto < SALDO_MINIMO) {
            System.out.println("Error: No se puede retirar. El saldo debe mantenerse en al menos $" + SALDO_MINIMO);
            return false;
        }
        
        saldo -= monto;
        System.out.println("Retiro exitoso. Nuevo saldo: $" + String.format("%.2f", saldo));
        return true;
    }
    
    @Override
    public boolean pagar(double monto) {
        if (!validarTransaccion(monto)) {
            return false;
        }
        
        if (saldo - monto < SALDO_MINIMO) {
            System.out.println("Error: No se puede realizar el pago. El saldo debe mantenerse en al menos $" + SALDO_MINIMO);
            return false;
        }
        
        saldo -= monto;
        System.out.println("Pago exitoso. Nuevo saldo: $" + String.format("%.2f", saldo));
        return true;
    }
    
    public void generarIntereses() {
        if (!activa) {
            System.out.println("No se pueden generar intereses en una cuenta inactiva.");
            return;
        }
        
        double intereses = saldo * (porcentajeInteres / 100);
        saldo += intereses;
        System.out.println("Intereses generados: $" + String.format("%.2f", intereses));
        System.out.println("Nuevo saldo: $" + String.format("%.2f", saldo));
    }
    
    public double getPorcentajeInteres() {
        return porcentajeInteres;
    }
    
    public void setPorcentajeInteres(double porcentajeInteres) {
        this.porcentajeInteres = porcentajeInteres;
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               "\nPorcentaje de interés: " + porcentajeInteres + "%";
    }
}