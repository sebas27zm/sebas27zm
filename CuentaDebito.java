public class CuentaDebito extends Cuenta implements IDeposito, IInteres {
    private double porcentajeInteres;
    
    public CuentaDebito(Cliente propietario, double saldoInicial, double porcentajeInteres) {
        super(propietario, saldoInicial);
        if (saldoInicial < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
        this.porcentajeInteres = porcentajeInteres;
    }
    
    @Override
    public String getTipoCuenta() {
        return "Cuenta de Débito";
    }
    
    @Override
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
        
        if (saldo - monto < 0) {
            System.out.println("Error: No se puede retirar. El saldo no puede ser negativo.");
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
        
        if (saldo - monto < 0) {
            System.out.println("Error: No se puede realizar el pago. El saldo no puede ser negativo.");
            return false;
        }
        
        saldo -= monto;
        System.out.println("Pago exitoso. Nuevo saldo: $" + String.format("%.2f", saldo));
        return true;
    }
    
    @Override
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
    
    @Override
    public double getPorcentajeInteres() {
        return porcentajeInteres;
    }
    
    @Override
    public void setPorcentajeInteres(double porcentajeInteres) {
        this.porcentajeInteres = porcentajeInteres;
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               "\nPorcentaje de interés: " + porcentajeInteres + "%";
    }
}