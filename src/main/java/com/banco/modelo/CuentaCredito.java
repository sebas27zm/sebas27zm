package com.banco.modelo;

public class CuentaCredito extends CuentaBancaria {
    private double limiteCredito;
    private String tipo;
    
    public CuentaCredito(Cliente propietario, double limiteCredito, String tipo) {
        super(propietario, 0.0); // Las cuentas de crédito siempre inician con saldo 0
        this.limiteCredito = limiteCredito;
        this.tipo = tipo;
    }
    
    @Override
    public boolean puedeRetirar(double monto) {
        double nuevoSaldo = saldo - monto;
        if (nuevoSaldo < -limiteCredito) {
            System.out.println("No se puede realizar el retiro. Se excedería el límite de crédito de $" + limiteCredito);
            return false;
        }
        return true;
    }
    
    @Override
    public boolean puedeDepositar(double monto) {
        return false; // Las cuentas de crédito no pueden recibir depósitos, solo abonos
    }
    
    @Override
    public String getTipoCuenta() {
        return "Cuenta de Crédito";
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
        return limiteCredito + saldo; // Si saldo es negativo, resta del límite
    }
    
    // Método específico para abonos (reduce la deuda)
    public boolean abonar(double monto) {
        if (!activa) {
            System.out.println("No se puede realizar la transacción. La cuenta está inactiva.");
            return false;
        }
        if (monto <= 0) {
            System.out.println("El monto debe ser positivo.");
            return false;
        }
        
        double nuevoSaldo = saldo + monto;
        if (nuevoSaldo > 0) {
            System.out.println("No se puede abonar más de la deuda. La cuenta de crédito no puede tener saldo positivo.");
            return false;
        }
        
        saldo += monto;
        System.out.println("Abono exitoso. Nuevo saldo: $" + saldo);
        return true;
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               "\nTipo de crédito: " + tipo +
               "\nLímite de crédito: $" + limiteCredito +
               "\nCrédito disponible: $" + getCreditoDisponible();
    }
}