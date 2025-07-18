package com.banco.modelo;

public class CuentaDebito extends CuentaBancaria {
    private double porcentajeInteres;
    
    public CuentaDebito(Cliente propietario, double saldoInicial, double porcentajeInteres) {
        super(propietario, saldoInicial);
        this.porcentajeInteres = porcentajeInteres;
    }
    
    @Override
    public boolean puedeRetirar(double monto) {
        double nuevoSaldo = saldo - monto;
        if (nuevoSaldo < 0) {
            System.out.println("No se puede realizar el retiro. El saldo no puede ser negativo.");
            return false;
        }
        return true;
    }
    
    @Override
    public boolean puedeDepositar(double monto) {
        return true; // Las cuentas de débito siempre pueden recibir depósitos
    }
    
    @Override
    public String getTipoCuenta() {
        return "Cuenta de Débito";
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
        if (saldo > 0) {
            double intereses = saldo * (porcentajeInteres / 100);
            saldo += intereses;
            System.out.println("Intereses generados: $" + intereses + ". Nuevo saldo: $" + saldo);
        } else {
            System.out.println("No se generan intereses porque el saldo es $0.");
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               "\nSaldo mínimo: $0" +
               "\nPorcentaje de interés: " + porcentajeInteres + "%";
    }
}