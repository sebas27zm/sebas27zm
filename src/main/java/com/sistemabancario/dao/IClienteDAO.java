package com.sistemabancario.dao;

import com.sistemabancario.model.Cliente;
import java.util.List;

public interface IClienteDAO {
    boolean crear(Cliente cliente);
    Cliente obtenerPorId(int id);
    Cliente obtenerPorCorreo(String correo);
    List<Cliente> obtenerTodos();
    boolean actualizar(Cliente cliente);
    boolean eliminar(int id);
    boolean existeCorreo(String correo);
}