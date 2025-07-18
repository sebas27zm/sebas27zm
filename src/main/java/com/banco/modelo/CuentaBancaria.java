package com.banco.modelo;

public abstract class CuentaBancaria {
    protected static int contadorCuentas = 1;
    protected int numeroCuenta;
    protected double saldo;
    protected boolean activa;
    protected Cliente propietario;
    
    public CuentaBancaria(Cliente propietario, double saldoInicial) {
        this.numeroCuenta = contadorCuentas++;
        this.saldo = saldoInicial;
        this.activa = true;
        this.propietario = propietario;
    }
    
    // Getters y setters
    public int getNumeroCuenta() { return numeroCuenta; }
    public double getSaldo() { return saldo; }
    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
    public Cliente getPropietario() { return propietario; }
    
    // Métodos abstractos que deben implementar las subclases
    public abstract boolean puedeRetirar(double monto);
    public abstract boolean puedeDepositar(double monto);
    public abstract String getTipoCuenta();
    
    // Método común para retiros
    public boolean retirar(double monto) {
        if (!activa) {
            System.out.println("No se puede realizar la transacción. La cuenta está inactiva.");
            return false;
        }
        if (monto <= 0) {
            System.out.println("El monto debe ser positivo.");
            return false;
        }
        if (puedeRetirar(monto)) {
            saldo -= monto;
            System.out.println("Retiro exitoso. Nuevo saldo: $" + saldo);
            return true;
        }
        return false;
    }
    
    // Método común para pagos (igual que retiros)
    public boolean pagar(double monto) {
        if (!activa) {
            System.out.println("No se puede realizar la transacción. La cuenta está inactiva.");
            return false;
        }
        if (monto <= 0) {
            System.out.println("El monto debe ser positivo.");
            return false;
        }
        if (puedeRetirar(monto)) {
            saldo -= monto;
            System.out.println("Pago exitoso. Nuevo saldo: $" + saldo);
            return true;
        }
        return false;
    }
    
    // Método común para depósitos (solo para cuentas de ahorro y débito)
    public boolean depositar(double monto) {
        if (!activa) {
            System.out.println("No se puede realizar la transacción. La cuenta está inactiva.");
            return false;
        }
        if (monto <= 0) {
            System.out.println("El monto debe ser positivo.");
            return false;
        }
        if (puedeDepositar(monto)) {
            saldo += monto;
            System.out.println("Depósito exitoso. Nuevo saldo: $" + saldo);
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return "Número de cuenta: " + numeroCuenta +
               "\nTipo: " + getTipoCuenta() +
               "\nSaldo: $" + saldo +
               "\nEstado: " + (activa ? "Activa" : "Inactiva") +
               "\nPropietario: " + propietario.getNombre() + " " + propietario.getApellidos();
    }
}