package com.sistemabancario.dao;

import com.sistemabancario.model.Administrador;

public interface IAdministradorDAO {
    boolean crear(Administrador administrador);
    Administrador obtener();
    boolean actualizar(Administrador administrador);
    boolean eliminar();
    boolean existe();
}