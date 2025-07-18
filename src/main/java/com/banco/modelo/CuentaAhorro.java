package com.banco.modelo;

public class CuentaAhorro extends CuentaBancaria {
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
    public boolean puedeRetirar(double monto) {
        double nuevoSaldo = saldo - monto;
        if (nuevoSaldo < SALDO_MINIMO) {
            System.out.println("No se puede realizar el retiro. El saldo mínimo debe ser $" + SALDO_MINIMO);
            return false;
        }
        return true;
    }
    
    @Override
    public boolean puedeDepositar(double monto) {
        return true; // Las cuentas de ahorro siempre pueden recibir depósitos
    }
    
    @Override
    public String getTipoCuenta() {
        return "Cuenta de Ahorro";
    }
    
    public double getPorcentajeInteres() {
        return porcentajeInteres;
    }
    
    public void setPorcentajeInteres(double porcentajeInteres) {
        this.porcentajeInteres = porcentajeInteres;
    }
    
    public void generarIntereses() {
        if (!activa) {
            System.out.println("No se pueden generar intereses. La cuenta está inactiva.");
            return;
        }
        double intereses = saldo * (porcentajeInteres / 100);
        saldo += intereses;
        System.out.println("Intereses generados: $" + intereses + ". Nuevo saldo: $" + saldo);
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               "\nSaldo mínimo: $" + SALDO_MINIMO +
               "\nPorcentaje de interés: " + porcentajeInteres + "%";
    }
}