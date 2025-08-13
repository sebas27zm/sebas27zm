package com.sistemabancario.dao;

import com.sistemabancario.model.Cliente;
import com.sistemabancario.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements IClienteDAO {
    
    @Override
    public boolean crear(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, apellidos, numero_cedula, correo_electronico, contrasena, sexo, profesion, direccion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellidos());
            stmt.setString(3, cliente.getNumeroCedula());
            stmt.setString(4, cliente.getCorreoElectronico());
            stmt.setString(5, cliente.getContrasena());
            stmt.setString(6, cliente.getSexo());
            stmt.setString(7, cliente.getProfesion());
            stmt.setString(8, cliente.getDireccion());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al crear cliente: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public Cliente obtenerPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetACliente(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener cliente por ID: " + e.getMessage());
        }
        
        return null;
    }
    
    @Override
    public Cliente obtenerPorCorreo(String correo) {
        String sql = "SELECT * FROM clientes WHERE correo_electronico = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, correo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetACliente(rs);
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener cliente por correo: " + e.getMessage());
        }
        
        return null;
    }
    
    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY nombre, apellidos";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                clientes.add(mapearResultSetACliente(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener todos los clientes: " + e.getMessage());
        }
        
        return clientes;
    }
    
    @Override
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, apellidos = ?, numero_cedula = ?, correo_electronico = ?, contrasena = ?, sexo = ?, profesion = ?, direccion = ? WHERE correo_electronico = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellidos());
            stmt.setString(3, cliente.getNumeroCedula());
            stmt.setString(4, cliente.getCorreoElectronico());
            stmt.setString(5, cliente.getContrasena());
            stmt.setString(6, cliente.getSexo());
            stmt.setString(7, cliente.getProfesion());
            stmt.setString(8, cliente.getDireccion());
            stmt.setString(9, cliente.getCorreoElectronico()); // WHERE condition
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean existeCorreo(String correo) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE correo_electronico = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, correo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de correo: " + e.getMessage());
        }
        
        return false;
    }
    
    private Cliente mapearResultSetACliente(ResultSet rs) throws SQLException {
        return new Cliente(
            rs.getString("nombre"),
            rs.getString("apellidos"),
            rs.getString("numero_cedula"),
            rs.getString("correo_electronico"),
            rs.getString("contrasena"),
            rs.getString("sexo"),
            rs.getString("profesion"),
            rs.getString("direccion")
        );
    }
    
    public int obtenerIdPorCorreo(String correo) {
        String sql = "SELECT id FROM clientes WHERE correo_electronico = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, correo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener ID por correo: " + e.getMessage());
        }
        
        return -1;
    }
}