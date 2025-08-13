-- Script de creación de base de datos para Sistema Bancario
-- Autor: Sistema Bancario
-- Fecha: 2024

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS sistema_bancario;
USE sistema_bancario;

-- Tabla de administradores
CREATE TABLE IF NOT EXISTS administradores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    numero_cedula VARCHAR(20) NOT NULL UNIQUE,
    correo_electronico VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de clientes
CREATE TABLE IF NOT EXISTS clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    numero_cedula VARCHAR(20) NOT NULL UNIQUE,
    correo_electronico VARCHAR(100) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    sexo VARCHAR(20) NOT NULL,
    profesion VARCHAR(100) NOT NULL,
    direccion TEXT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabla de cuentas
CREATE TABLE IF NOT EXISTS cuentas (
    numero_cuenta INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT NOT NULL,
    tipo_cuenta ENUM('AHORRO', 'DEBITO', 'CREDITO') NOT NULL,
    saldo DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    activa BOOLEAN NOT NULL DEFAULT TRUE,
    porcentaje_interes DECIMAL(5,2) NULL, -- Solo para cuentas de ahorro y débito
    limite_credito DECIMAL(15,2) NULL, -- Solo para cuentas de crédito
    tipo_credito VARCHAR(50) NULL, -- Solo para cuentas de crédito (Cashback, Gane Premios, etc.)
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE,
    INDEX idx_cliente_id (cliente_id),
    INDEX idx_tipo_cuenta (tipo_cuenta)
);

-- Tabla de transacciones (opcional para auditoría)
CREATE TABLE IF NOT EXISTS transacciones (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_cuenta INT NOT NULL,
    tipo_transaccion ENUM('DEPOSITO', 'RETIRO', 'PAGO', 'ABONO', 'INTERES') NOT NULL,
    monto DECIMAL(15,2) NOT NULL,
    saldo_anterior DECIMAL(15,2) NOT NULL,
    saldo_posterior DECIMAL(15,2) NOT NULL,
    descripcion TEXT,
    fecha_transaccion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (numero_cuenta) REFERENCES cuentas(numero_cuenta) ON DELETE CASCADE,
    INDEX idx_numero_cuenta (numero_cuenta),
    INDEX idx_fecha_transaccion (fecha_transaccion)
);

-- Insertar datos de prueba (opcional)
-- Administrador por defecto
INSERT IGNORE INTO administradores (nombre, apellidos, numero_cedula, correo_electronico, contrasena) 
VALUES ('Admin', 'Sistema', '000000000', 'admin@sistemabancario.com', 'admin123');

-- Clientes de prueba
INSERT IGNORE INTO clientes (nombre, apellidos, numero_cedula, correo_electronico, contrasena, sexo, profesion, direccion) 
VALUES 
('Juan', 'Pérez', '123456789', 'juan.perez@email.com', 'juan123', 'Masculino', 'Ingeniero', 'Calle 123, Ciudad'),
('María', 'González', '987654321', 'maria.gonzalez@email.com', 'maria123', 'Femenino', 'Doctora', 'Avenida 456, Ciudad');

-- Cuentas de prueba
INSERT IGNORE INTO cuentas (cliente_id, tipo_cuenta, saldo, porcentaje_interes) 
VALUES 
(1, 'AHORRO', 1000.00, 2.5),
(1, 'DEBITO', 500.00, 1.0),
(2, 'AHORRO', 2000.00, 3.0);

INSERT IGNORE INTO cuentas (cliente_id, tipo_cuenta, saldo, limite_credito, tipo_credito) 
VALUES 
(2, 'CREDITO', 0.00, 5000.00, 'Cashback');

-- Mostrar información de las tablas creadas
SHOW TABLES;
SELECT 'Base de datos sistema_bancario creada exitosamente' AS mensaje;