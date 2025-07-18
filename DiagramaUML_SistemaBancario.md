# Diagrama UML - Sistema Bancario Final

## Diagrama de Clases

```mermaid
classDiagram
    %% Interfaces
    class ITransaccion {
        <<interface>>
        +retirar(monto: double) boolean
        +pagar(monto: double) boolean
    }
    
    class IDeposito {
        <<interface>>
        +depositar(monto: double) boolean
    }
    
    class IAbono {
        <<interface>>
        +abonar(monto: double) boolean
    }
    
    class IInteres {
        <<interface>>
        +generarIntereses() void
        +getPorcentajeInteres() double
        +setPorcentajeInteres(porcentaje: double) void
    }
    
    %% Clases Abstractas
    class Usuario {
        <<abstract>>
        #nombre: String
        #apellidos: String
        #numeroCedula: String
        #correoElectronico: String
        #contrasena: String
        
        +Usuario(nombre: String, apellidos: String, cedula: String, correo: String, contrasena: String)
        +getNombre() String
        +getApellidos() String
        +getNumeroCedula() String
        +getCorreoElectronico() String
        +getContrasena() String
        +setNombre(nombre: String) void
        +setApellidos(apellidos: String) void
        +setNumeroCedula(cedula: String) void
        +setCorreoElectronico(correo: String) void
        +setContrasena(contrasena: String) void
        +validarCredenciales(correo: String, contrasena: String) boolean
        +toString() String
    }
    
    class Cuenta {
        <<abstract>>
        #contadorCuentas: int
        #numeroCuenta: int
        #saldo: double
        #activa: boolean
        #propietario: Cliente
        
        +Cuenta(propietario: Cliente, saldoInicial: double)
        +getNumeroCuenta() int
        +getSaldo() double
        +isActiva() boolean
        +getPropietario() Cliente
        +setActiva(activa: boolean) void
        +retirar(monto: double) boolean*
        +pagar(monto: double) boolean*
        +getTipoCuenta() String*
        #validarTransaccion(monto: double) boolean
        +toString() String
    }
    
    %% Clases Concretas - Usuarios
    class Administrador {
        +Administrador(nombre: String, apellidos: String, cedula: String, correo: String, contrasena: String)
        +toString() String
    }
    
    class Cliente {
        -sexo: String
        -profesion: String
        -direccion: String
        -cuentas: List~Cuenta~
        
        +Cliente(nombre: String, apellidos: String, cedula: String, correo: String, contrasena: String, sexo: String, profesion: String, direccion: String)
        +getSexo() String
        +getProfesion() String
        +getDireccion() String
        +getCuentas() List~Cuenta~
        +setSexo(sexo: String) void
        +setProfesion(profesion: String) void
        +setDireccion(direccion: String) void
        +agregarCuenta(cuenta: Cuenta) void
        +removerCuenta(cuenta: Cuenta) void
        +toString() String
        +reporteCuentas() String
    }
    
    %% Clases Concretas - Cuentas
    class CuentaAhorro {
        -SALDO_MINIMO: double
        -porcentajeInteres: double
        
        +CuentaAhorro(propietario: Cliente, saldoInicial: double, porcentajeInteres: double)
        +getTipoCuenta() String
        +depositar(monto: double) boolean
        +retirar(monto: double) boolean
        +pagar(monto: double) boolean
        +generarIntereses() void
        +getPorcentajeInteres() double
        +setPorcentajeInteres(porcentaje: double) void
        +toString() String
    }
    
    class CuentaDebito {
        -porcentajeInteres: double
        
        +CuentaDebito(propietario: Cliente, saldoInicial: double, porcentajeInteres: double)
        +getTipoCuenta() String
        +depositar(monto: double) boolean
        +retirar(monto: double) boolean
        +pagar(monto: double) boolean
        +generarIntereses() void
        +getPorcentajeInteres() double
        +setPorcentajeInteres(porcentaje: double) void
        +toString() String
    }
    
    class CuentaCredito {
        -limiteCredito: double
        -tipo: String
        
        +CuentaCredito(propietario: Cliente, limiteCredito: double, tipo: String)
        +getTipoCuenta() String
        +abonar(monto: double) boolean
        +retirar(monto: double) boolean
        +pagar(monto: double) boolean
        +getLimiteCredito() double
        +setLimiteCredito(limite: double) void
        +getTipo() String
        +setTipo(tipo: String) void
        +getCreditoDisponible() double
        +toString() String
    }
    
    %% Clases de Lógica de Negocio
    class SistemaBancarioBI {
        -administrador: Administrador
        -clientes: List~Cliente~
        
        +SistemaBancarioBI()
        +crearAdministrador(nombre: String, apellidos: String, cedula: String, correo: String, contrasena: String) boolean
        +validarAdministrador(correo: String, contrasena: String) boolean
        +existeAdministrador() boolean
        +registrarCliente(nombre: String, apellidos: String, cedula: String, correo: String, contrasena: String, sexo: String, profesion: String, direccion: String) boolean
        +validarCliente(correo: String, contrasena: String) Cliente
        +obtenerClientes() List~Cliente~
        +hayClientes() boolean
        +crearCuentaAhorro(cliente: Cliente, saldo: double, interes: double) boolean
        +crearCuentaDebito(cliente: Cliente, saldo: double, interes: double) boolean
        +crearCuentaCredito(cliente: Cliente, limite: double, tipo: String) boolean
        +obtenerCuentasPorTipo(tipo: String) List~Cuenta~
        +buscarCuentaPorNumero(numeroCuenta: int) Cuenta
        +cambiarEstadoCuenta(numeroCuenta: int) boolean
        +realizarDeposito(cuenta: Cuenta, monto: double) boolean
        +realizarRetiro(cuenta: Cuenta, monto: double) boolean
        +realizarPago(cuenta: Cuenta, monto: double) boolean
        +realizarAbono(cuenta: Cuenta, monto: double) boolean
        +generarInteresesCliente(cliente: Cliente) void
        +tieneIntereses(cliente: Cliente) boolean
    }
    
    class SistemaBancarioUI {
        -logicaNegocio: SistemaBancarioBI
        -scanner: Scanner
        
        +SistemaBancarioUI()
        +iniciar() void
        -mostrarMenuPrincipal() void
        -leerOpcion() int
        -manejarCrearAdministrador() void
        -manejarLoginAdministrador() void
        -menuAdministrador() void
        -manejarRegistrarCliente() void
        -manejarListarClientes() void
        -manejarListarCuentasPorTipo(tipo: String) void
        -manejarCrearCuentaParaCliente() void
        -manejarCrearCuentaAhorro(cliente: Cliente) void
        -manejarCrearCuentaDebito(cliente: Cliente) void
        -manejarCrearCuentaCredito(cliente: Cliente) void
        -manejarActivarDesactivarCuenta() void
        -manejarLoginCliente() void
        -menuCliente(cliente: Cliente) void
        -manejarRealizarTransaccion(cliente: Cliente) void
        -manejarGenerarIntereses(cliente: Cliente) void
        +main(args: String[]) void
    }
    
    %% Relaciones de Herencia
    Usuario <|-- Administrador
    Usuario <|-- Cliente
    Cuenta <|-- CuentaAhorro
    Cuenta <|-- CuentaDebito
    Cuenta <|-- CuentaCredito
    
    %% Relaciones de Implementación
    Cuenta ..|> ITransaccion
    CuentaAhorro ..|> IDeposito
    CuentaAhorro ..|> IInteres
    CuentaDebito ..|> IDeposito
    CuentaDebito ..|> IInteres
    CuentaCredito ..|> IAbono
    
    %% Relaciones de Composición/Agregación
    Cliente "1" *-- "0..*" Cuenta : posee
    Cuenta "1" --> "1" Cliente : propietario
    SistemaBancarioBI "1" *-- "0..1" Administrador : gestiona
    SistemaBancarioBI "1" *-- "0..*" Cliente : gestiona
    SistemaBancarioUI "1" *-- "1" SistemaBancarioBI : utiliza
```

## Descripción de Relaciones

### Herencia
- **Usuario** (abstracta) ← **Administrador**, **Cliente**
- **Cuenta** (abstracta) ← **CuentaAhorro**, **CuentaDebito**, **CuentaCredito**

### Implementación de Interfaces
- **Cuenta** implementa **ITransaccion**
- **CuentaAhorro** implementa **IDeposito** e **IInteres**
- **CuentaDebito** implementa **IDeposito** e **IInteres**
- **CuentaCredito** implementa **IAbono**

### Composición/Agregación
- **Cliente** posee múltiples **Cuenta** (0..*)
- **Cuenta** tiene un **Cliente** propietario (1)
- **SistemaBancarioBI** gestiona un **Administrador** (0..1) y múltiples **Cliente** (0..*)
- **SistemaBancarioUI** utiliza **SistemaBancarioBI** (1)

### Arquitectura Modular
- **SistemaBancarioBI**: Capa de Business Intelligence (lógica de negocio)
- **SistemaBancarioUI**: Capa de User Interface (interfaz de usuario)

## Patrones de Diseño Implementados

1. **Template Method**: En la clase abstracta `Cuenta` con métodos abstractos
2. **Strategy**: A través de las interfaces para diferentes tipos de operaciones
3. **Composition**: Cliente contiene lista de cuentas
4. **Separation of Concerns**: Separación entre lógica de negocio (BI) y presentación (UI)

## Principios SOLID Aplicados

- **S**: Cada clase tiene una responsabilidad específica
- **O**: Extensible a través de herencia e interfaces
- **L**: Las subclases pueden sustituir a sus clases base
- **I**: Interfaces segregadas por funcionalidad específica
- **D**: Dependencia de abstracciones, no de implementaciones concretas