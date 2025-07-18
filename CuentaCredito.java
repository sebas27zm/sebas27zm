public class CuentaCredito extends Cuenta {
    private double limiteCredito;
    private String tipo;
    
    public CuentaCredito(Cliente propietario, double limiteCredito, String tipo) {
        super(propietario, 0.0); // Las cuentas de crédito siempre inician con saldo 0
        if (limiteCredito <= 0) {
            throw new IllegalArgumentException("El límite de crédito debe ser mayor a 0");
        }
        this.limiteCredito = limiteCredito;
        this.tipo = tipo;
    }
    
    @Override
    public String getTipoCuenta() {
        return "Cuenta de Crédito";
    }
    
    public boolean abonar(double monto) {
        if (!validarTransaccion(monto)) {
            return false;
        }
        
        if (saldo + monto > 0) {
            System.out.println("Error: El saldo no puede ser positivo en una cuenta de crédito.");
            return false;
        }
        
        saldo += monto;
        System.out.println("Abono exitoso. Nuevo saldo: $" + String.format("%.2f", saldo));
        return true;
    }
    
    @Override
    public boolean retirar(double monto) {
        if (!validarTransaccion(monto)) {
            return false;
        }
        
        if (saldo - monto < -limiteCredito) {
            System.out.println("Error: No se puede retirar. Se excedería el límite de crédito de $" + String.format("%.2f", limiteCredito));
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
        
        if (saldo - monto < -limiteCredito) {
            System.out.println("Error: No se puede realizar el pago. Se excedería el límite de crédito de $" + String.format("%.2f", limiteCredito));
            return false;
        }
        
        saldo -= monto;
        System.out.println("Pago exitoso. Nuevo saldo: $" + String.format("%.2f", saldo));
        return true;
    }
    
    public double getLimiteCredito() {
        return limiteCredito;
    }
    
    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public double getCreditoDisponible() {
        return limiteCredito + saldo; // saldo es negativo, por eso se suma
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               "\nTipo de crédito: " + tipo +
               "\nLímite de crédito: $" + String.format("%.2f", limiteCredito) +
               "\nCrédito disponible: $" + String.format("%.2f", getCreditoDisponible());
    }
}