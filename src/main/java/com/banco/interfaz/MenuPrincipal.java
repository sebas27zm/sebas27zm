package com.banco.interfaz;

import com.banco.modelo.*;
import com.banco.servicio.SistemaBancario;
import java.util.Scanner;

public class MenuPrincipal {
    private SistemaBancario sistema;
    private Scanner scanner;
    
    public MenuPrincipal() {
        this.sistema = new SistemaBancario();
        this.scanner = new Scanner(System.in);
    }
    
    public void iniciar() {
        System.out.println("=== SISTEMA BANCARIO ===");
        System.out.println("Bienvenido al Sistema de Administración de Cuentas Bancarias");
        
        while (true) {
            mostrarMenuPrincipal();
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    crearAdministrador();
                    break;
                case 2:
                    loginAdministrador();
                    break;
                case 3:
                    crearCliente();
                    break;
                case 4:
                    loginCliente();
                    break;
                case 5:
                    System.out.println("Gracias por usar el Sistema Bancario. ¡Hasta luego!");
                    return;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
    
    private void mostrarMenuPrincipal() {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Crear Administrador");
        System.out.println("2. Login Administrador");
        System.out.println("3. Crear Cliente");
        System.out.println("4. Login Cliente");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }
    
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void crearAdministrador() {
        System.out.println("\n=== CREAR ADMINISTRADOR ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();
        System.out.print("Cédula: ");
        String cedula = scanner.nextLine();
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        
        sistema.crearAdministrador(nombre, apellidos, cedula, correo, contrasena);
    }
    
    private void loginAdministrador() {
        if (!sistema.existeAdministrador()) {
            System.out.println("No existe un administrador registrado. Debe crear uno primero.");
            return;
        }
        
        System.out.println("\n=== LOGIN ADMINISTRADOR ===");
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        
        Administrador admin = sistema.autenticarAdministrador(correo, contrasena);
        if (admin != null) {
            System.out.println("¡Bienvenido, " + admin.getNombre() + "!");
            menuAdministrador(admin);
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }
    
    private void crearCliente() {
        System.out.println("\n=== CREAR CLIENTE ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();
        System.out.print("Cédula: ");
        String cedula = scanner.nextLine();
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        System.out.print("Sexo: ");
        String sexo = scanner.nextLine();
        System.out.print("Profesión: ");
        String profesion = scanner.nextLine();
        System.out.print("Dirección: ");
        String direccion = scanner.nextLine();
        
        sistema.crearCliente(nombre, apellidos, cedula, correo, contrasena, sexo, profesion, direccion);
    }
    
    private void loginCliente() {
        System.out.println("\n=== LOGIN CLIENTE ===");
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        
        Cliente cliente = sistema.autenticarCliente(correo, contrasena);
        if (cliente != null) {
            System.out.println("¡Bienvenido, " + cliente.getNombre() + "!");
            menuCliente(cliente);
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }
    
    private void menuAdministrador(Administrador admin) {
        while (true) {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Ver información personal");
            System.out.println("2. Listar todos los clientes");
            System.out.println("3. Listar todas las cuentas");
            System.out.println("4. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    System.out.println("\n" + admin.toString());
                    break;
                case 2:
                    System.out.println("\n" + sistema.generarReporteClientes());
                    break;
                case 3:
                    System.out.println("\n" + sistema.generarReporteCuentas());
                    break;
                case 4:
                    System.out.println("Sesión cerrada.");
                    return;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
    
    private void menuCliente(Cliente cliente) {
        while (true) {
            System.out.println("\n=== MENÚ CLIENTE ===");
            System.out.println("1. Ver información personal");
            System.out.println("2. Crear cuenta de ahorro");
            System.out.println("3. Crear cuenta de débito");
            System.out.println("4. Crear cuenta de crédito");
            System.out.println("5. Ver reporte de mis cuentas");
            System.out.println("6. Realizar transacciones");
            System.out.println("7. Generar intereses");
            System.out.println("8. Cerrar sesión");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    System.out.println("\n" + cliente.toString());
                    break;
                case 2:
                    crearCuentaAhorro(cliente);
                    break;
                case 3:
                    crearCuentaDebito(cliente);
                    break;
                case 4:
                    crearCuentaCredito(cliente);
                    break;
                case 5:
                    System.out.println("\n" + cliente.generarReporteCuentas());
                    break;
                case 6:
                    menuTransacciones(cliente);
                    break;
                case 7:
                    generarIntereses(cliente);
                    break;
                case 8:
                    System.out.println("Sesión cerrada.");
                    return;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
    
    private void crearCuentaAhorro(Cliente cliente) {
        System.out.println("\n=== CREAR CUENTA DE AHORRO ===");
        System.out.print("Saldo inicial (mínimo $100): ");
        double saldoInicial = leerDouble();
        System.out.print("Porcentaje de interés anual: ");
        double porcentajeInteres = leerDouble();
        
        sistema.crearCuentaAhorro(cliente, saldoInicial, porcentajeInteres);
    }
    
    private void crearCuentaDebito(Cliente cliente) {
        System.out.println("\n=== CREAR CUENTA DE DÉBITO ===");
        System.out.print("Saldo inicial: ");
        double saldoInicial = leerDouble();
        System.out.print("Porcentaje de interés anual: ");
        double porcentajeInteres = leerDouble();
        
        sistema.crearCuentaDebito(cliente, saldoInicial, porcentajeInteres);
    }
    
    private void crearCuentaCredito(Cliente cliente) {
        System.out.println("\n=== CREAR CUENTA DE CRÉDITO ===");
        System.out.print("Límite de crédito: ");
        double limiteCredito = leerDouble();
        System.out.print("Tipo de crédito (ej: Cashback, Gane Premios, Millas): ");
        String tipo = scanner.nextLine();
        
        sistema.crearCuentaCredito(cliente, limiteCredito, tipo);
    }
    
    private void menuTransacciones(Cliente cliente) {
        if (cliente.getCuentas().isEmpty()) {
            System.out.println("No tiene cuentas registradas.");
            return;
        }
        
        System.out.println("\n=== SELECCIONAR CUENTA ===");
        for (int i = 0; i < cliente.getCuentas().size(); i++) {
            System.out.println((i + 1) + ". " + cliente.getCuentas().get(i).getTipoCuenta() + 
                             " - Número: " + cliente.getCuentas().get(i).getNumeroCuenta());
        }
        System.out.print("Seleccione una cuenta: ");
        
        int indiceCuenta = leerOpcion() - 1;
        if (indiceCuenta >= 0 && indiceCuenta < cliente.getCuentas().size()) {
            CuentaBancaria cuenta = cliente.getCuentas().get(indiceCuenta);
            menuTransaccionesCuenta(cuenta);
        } else {
            System.out.println("Cuenta inválida.");
        }
    }
    
    private void menuTransaccionesCuenta(CuentaBancaria cuenta) {
        while (true) {
            System.out.println("\n=== TRANSACCIONES - " + cuenta.getTipoCuenta() + " ===");
            System.out.println("Saldo actual: $" + cuenta.getSaldo());
            System.out.println("1. Retirar");
            System.out.println("2. Pagar");
            if (cuenta instanceof CuentaAhorro || cuenta instanceof CuentaDebito) {
                System.out.println("3. Depositar");
            }
            if (cuenta instanceof CuentaCredito) {
                System.out.println("3. Abonar");
            }
            System.out.println("4. Activar/Desactivar cuenta");
            System.out.println("5. Volver al menú anterior");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    System.out.print("Monto a retirar: ");
                    double montoRetiro = leerDouble();
                    cuenta.retirar(montoRetiro);
                    break;
                case 2:
                    System.out.print("Monto a pagar: ");
                    double montoPago = leerDouble();
                    cuenta.pagar(montoPago);
                    break;
                case 3:
                    if (cuenta instanceof CuentaCredito) {
                        System.out.print("Monto a abonar: ");
                        double montoAbono = leerDouble();
                        ((CuentaCredito) cuenta).abonar(montoAbono);
                    } else {
                        System.out.print("Monto a depositar: ");
                        double montoDeposito = leerDouble();
                        cuenta.depositar(montoDeposito);
                    }
                    break;
                case 4:
                    cuenta.setActiva(!cuenta.isActiva());
                    System.out.println("Cuenta " + (cuenta.isActiva() ? "activada" : "desactivada") + ".");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
    
    private void generarIntereses(Cliente cliente) {
        System.out.println("\n=== GENERAR INTERESES ===");
        boolean hayIntereses = false;
        
        for (CuentaBancaria cuenta : cliente.getCuentas()) {
            if (cuenta instanceof CuentaAhorro) {
                System.out.println("Generando intereses para cuenta de ahorro #" + cuenta.getNumeroCuenta());
                ((CuentaAhorro) cuenta).generarIntereses();
                hayIntereses = true;
            } else if (cuenta instanceof CuentaDebito) {
                System.out.println("Generando intereses para cuenta de débito #" + cuenta.getNumeroCuenta());
                ((CuentaDebito) cuenta).generarIntereses();
                hayIntereses = true;
            }
        }
        
        if (!hayIntereses) {
            System.out.println("No tiene cuentas que generen intereses.");
        }
    }
    
    private double leerDouble() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Valor inválido. Se asignará 0.");
            return 0.0;
        }
    }
}