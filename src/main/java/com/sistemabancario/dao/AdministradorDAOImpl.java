package com.sistemabancario.dao;

import com.sistemabancario.model.Administrador;
import com.sistemabancario.util.DatabaseConnection;
import java.sql.*;

public class AdministradorDAOImpl implements IAdministradorDAO {
    
    @Override
    public boolean crear(Administrador administrador) {
        String sql = "INSERT INTO administradores (nombre, apellidos, numero_cedula, correo_electronico, contrasena) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, administrador.getNombre());
            stmt.setString(2, administrador.getApellidos());
            stmt.setString(3, administrador.getNumeroCedula());
            stmt.setString(4, administrador.getCorreoElectronico());
            stmt.setString(5, administrador.getContrasena());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al crear administrador: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public Administrador obtener() {
        String sql = "SELECT * FROM administradores LIMIT 1";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return new Administrador(
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getString("numero_cedula"),
                    rs.getString("correo_electronico"),
                    rs.getString("contrasena")
                );
            }
            
        } catch (SQLException e) {
            System.err.println("Error al obtener administrador: " + e.getMessage());
        }
        
        return null;
    }
    
    @Override
    public boolean actualizar(Administrador administrador) {
        String sql = "UPDATE administradores SET nombre = ?, apellidos = ?, numero_cedula = ?, correo_electronico = ?, contrasena = ? WHERE id = 1";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, administrador.getNombre());
            stmt.setString(2, administrador.getApellidos());
            stmt.setString(3, administrador.getNumeroCedula());
            stmt.setString(4, administrador.getCorreoElectronico());
            stmt.setString(5, administrador.getContrasena());
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar administrador: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean eliminar() {
        String sql = "DELETE FROM administradores";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar administrador: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public boolean existe() {
        String sql = "SELECT COUNT(*) FROM administradores";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de administrador: " + e.getMessage());
        }
        
        return false;
    }
}