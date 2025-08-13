package com.sistemabancario.dao;

import com.sistemabancario.model.Cuenta;
import java.util.List;

public interface ICuentaDAO {
    boolean crear(Cuenta cuenta);
    Cuenta obtenerPorNumero(int numeroCuenta);
    List<Cuenta> obtenerPorCliente(int clienteId);
    List<Cuenta> obtenerPorTipo(String tipo);
    List<Cuenta> obtenerTodas();
    boolean actualizar(Cuenta cuenta);
    boolean eliminar(int numeroCuenta);
}