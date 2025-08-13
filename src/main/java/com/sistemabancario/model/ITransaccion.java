package com.sistemabancario.model;

public interface ITransaccion {
    boolean retirar(double monto);
    boolean pagar(double monto);
}