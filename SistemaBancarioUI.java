import java.util.List;
import java.util.Scanner;

public class SistemaBancarioUI {
    private SistemaBancarioBI logicaNegocio;
    private Scanner scanner;
    
    public SistemaBancarioUI() {
        this.logicaNegocio = new SistemaBancarioBI();
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
                    manejarCrearAdministrador();
                    break;
                case 2:
                    manejarLoginAdministrador();
                    break;
                case 3:
                    manejarLoginCliente();
                    break;
                case 4:
                    System.out.println("¡Gracias por usar el Sistema Bancario!");
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
        System.out.println("3. Login Cliente");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }
    
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
    
    private void manejarCrearAdministrador() {
        if (logicaNegocio.existeAdministrador()) {
            System.out.println("Ya existe un administrador registrado.");
            return;
        }
        
        System.out.println("\n=== CREAR ADMINISTRADOR ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();
        System.out.print("Número de cédula: ");
        String cedula = scanner.nextLine();
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        
        if (logicaNegocio.crearAdministrador(nombre, apellidos, cedula, correo, contrasena)) {
            System.out.println("Administrador creado exitosamente.");
        } else {
            System.out.println("Error al crear el administrador.");
        }
    }
    
    private void manejarLoginAdministrador() {
        if (!logicaNegocio.existeAdministrador()) {
            System.out.println("No hay administrador registrado. Debe crear uno primero.");
            return;
        }
        
        System.out.println("\n=== LOGIN ADMINISTRADOR ===");
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        
        if (logicaNegocio.validarAdministrador(correo, contrasena)) {
            menuAdministrador();
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }
    
    private void menuAdministrador() {
        while (true) {
            System.out.println("\n=== MENÚ ADMINISTRADOR ===");
            System.out.println("1. Registrar Cliente");
            System.out.println("2. Listar Clientes");
            System.out.println("3. Listar Cuentas de Ahorro");
            System.out.println("4. Listar Cuentas de Débito");
            System.out.println("5. Listar Cuentas de Crédito");
            System.out.println("6. Crear Cuenta para Cliente");
            System.out.println("7. Activar/Desactivar Cuenta");
            System.out.println("8. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    manejarRegistrarCliente();
                    break;
                case 2:
                    manejarListarClientes();
                    break;
                case 3:
                    manejarListarCuentasPorTipo("Ahorro");
                    break;
                case 4:
                    manejarListarCuentasPorTipo("Débito");
                    break;
                case 5:
                    manejarListarCuentasPorTipo("Crédito");
                    break;
                case 6:
                    manejarCrearCuentaParaCliente();
                    break;
                case 7:
                    manejarActivarDesactivarCuenta();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
    
    private void manejarRegistrarCliente() {
        System.out.println("\n=== REGISTRAR CLIENTE ===");
        System.out.print("Nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Apellidos: ");
        String apellidos = scanner.nextLine();
        System.out.print("Número de cédula: ");
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
        
        if (logicaNegocio.registrarCliente(nombre, apellidos, cedula, correo, contrasena, sexo, profesion, direccion)) {
            System.out.println("Cliente registrado exitosamente.");
        } else {
            System.out.println("Ya existe un cliente con ese correo electrónico.");
        }
    }
    
    private void manejarListarClientes() {
        System.out.println("\n=== LISTA DE CLIENTES ===");
        List<Cliente> clientes = logicaNegocio.obtenerClientes();
        
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (int i = 0; i < clientes.size(); i++) {
                System.out.println("\n--- Cliente " + (i + 1) + " ---");
                System.out.println(clientes.get(i));
            }
        }
    }
    
    private void manejarListarCuentasPorTipo(String tipo) {
        System.out.println("\n=== LISTA DE CUENTAS DE " + tipo.toUpperCase() + " ===");
        List<Cuenta> cuentas = logicaNegocio.obtenerCuentasPorTipo(tipo);
        
        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas de " + tipo.toLowerCase() + " registradas.");
        } else {
            for (Cuenta cuenta : cuentas) {
                System.out.println("\n" + cuenta);
            }
        }
    }
    
    private void manejarCrearCuentaParaCliente() {
        if (!logicaNegocio.hayClientes()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        
        System.out.println("\n=== CREAR CUENTA PARA CLIENTE ===");
        List<Cliente> clientes = logicaNegocio.obtenerClientes();
        
        System.out.println("Seleccione el cliente:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + ". " + clientes.get(i).getNombre() + " " + clientes.get(i).getApellidos());
        }
        
        System.out.print("Número de cliente: ");
        int numeroCliente = leerOpcion() - 1;
        
        if (numeroCliente < 0 || numeroCliente >= clientes.size()) {
            System.out.println("Cliente inválido.");
            return;
        }
        
        Cliente cliente = clientes.get(numeroCliente);
        
        System.out.println("Tipo de cuenta:");
        System.out.println("1. Cuenta de Ahorro");
        System.out.println("2. Cuenta de Débito");
        System.out.println("3. Cuenta de Crédito");
        System.out.print("Seleccione el tipo: ");
        
        int tipoCuenta = leerOpcion();
        
        switch (tipoCuenta) {
            case 1:
                manejarCrearCuentaAhorro(cliente);
                break;
            case 2:
                manejarCrearCuentaDebito(cliente);
                break;
            case 3:
                manejarCrearCuentaCredito(cliente);
                break;
            default:
                System.out.println("Tipo de cuenta inválido.");
        }
    }
    
    private void manejarCrearCuentaAhorro(Cliente cliente) {
        System.out.print("Saldo inicial (mínimo $100): ");
        double saldo = Double.parseDouble(scanner.nextLine());
        System.out.print("Porcentaje de interés: ");
        double interes = Double.parseDouble(scanner.nextLine());
        
        if (logicaNegocio.crearCuentaAhorro(cliente, saldo, interes)) {
            System.out.println("Cuenta de ahorro creada exitosamente.");
        } else {
            System.out.println("Error al crear la cuenta. Verifique que el saldo sea al menos $100.");
        }
    }
    
    private void manejarCrearCuentaDebito(Cliente cliente) {
        System.out.print("Saldo inicial: ");
        double saldo = Double.parseDouble(scanner.nextLine());
        System.out.print("Porcentaje de interés: ");
        double interes = Double.parseDouble(scanner.nextLine());
        
        if (logicaNegocio.crearCuentaDebito(cliente, saldo, interes)) {
            System.out.println("Cuenta de débito creada exitosamente.");
        } else {
            System.out.println("Error al crear la cuenta. Verifique que el saldo no sea negativo.");
        }
    }
    
    private void manejarCrearCuentaCredito(Cliente cliente) {
        System.out.print("Límite de crédito: ");
        double limite = Double.parseDouble(scanner.nextLine());
        System.out.print("Tipo de crédito (ej: Cashback, Gane Premios, Millas): ");
        String tipo = scanner.nextLine();
        
        if (logicaNegocio.crearCuentaCredito(cliente, limite, tipo)) {
            System.out.println("Cuenta de crédito creada exitosamente.");
        } else {
            System.out.println("Error al crear la cuenta. Verifique que el límite sea mayor a 0.");
        }
    }
    
    private void manejarActivarDesactivarCuenta() {
        System.out.println("\n=== ACTIVAR/DESACTIVAR CUENTA ===");
        System.out.print("Número de cuenta: ");
        int numeroCuenta = leerOpcion();
        
        Cuenta cuenta = logicaNegocio.buscarCuentaPorNumero(numeroCuenta);
        if (cuenta == null) {
            System.out.println("Cuenta no encontrada.");
            return;
        }
        
        System.out.println("Estado actual: " + (cuenta.isActiva() ? "Activa" : "Inactiva"));
        System.out.print("¿Desea " + (cuenta.isActiva() ? "desactivar" : "activar") + " la cuenta? (s/n): ");
        String respuesta = scanner.nextLine();
        
        if (respuesta.equalsIgnoreCase("s")) {
            if (logicaNegocio.cambiarEstadoCuenta(numeroCuenta)) {
                System.out.println("Cuenta " + (cuenta.isActiva() ? "activada" : "desactivada") + " exitosamente.");
            }
        }
    }
    
    private void manejarLoginCliente() {
        if (!logicaNegocio.hayClientes()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        
        System.out.println("\n=== LOGIN CLIENTE ===");
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        
        Cliente cliente = logicaNegocio.validarCliente(correo, contrasena);
        if (cliente != null) {
            menuCliente(cliente);
        } else {
            System.out.println("Credenciales incorrectas.");
        }
    }
    
    private void menuCliente(Cliente cliente) {
        while (true) {
            System.out.println("\n=== MENÚ CLIENTE ===");
            System.out.println("Bienvenido, " + cliente.getNombre() + " " + cliente.getApellidos());
            System.out.println("1. Ver Reporte de Cuentas");
            System.out.println("2. Realizar Transacción");
            System.out.println("3. Generar Intereses");
            System.out.println("4. Cerrar Sesión");
            System.out.print("Seleccione una opción: ");
            
            int opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    System.out.println(cliente.reporteCuentas());
                    break;
                case 2:
                    manejarRealizarTransaccion(cliente);
                    break;
                case 3:
                    manejarGenerarIntereses(cliente);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
    
    private void manejarRealizarTransaccion(Cliente cliente) {
        if (cliente.getCuentas().isEmpty()) {
            System.out.println("No tiene cuentas registradas.");
            return;
        }
        
        System.out.println("\n=== REALIZAR TRANSACCIÓN ===");
        System.out.println("Sus cuentas:");
        for (int i = 0; i < cliente.getCuentas().size(); i++) {
            Cuenta cuenta = cliente.getCuentas().get(i);
            System.out.println((i + 1) + ". " + cuenta.getTipoCuenta() + " - Número: " + cuenta.getNumeroCuenta() + " - Saldo: $" + String.format("%.2f", cuenta.getSaldo()));
        }
        
        System.out.print("Seleccione la cuenta: ");
        int numeroCuenta = leerOpcion() - 1;
        
        if (numeroCuenta < 0 || numeroCuenta >= cliente.getCuentas().size()) {
            System.out.println("Cuenta inválida.");
            return;
        }
        
        Cuenta cuenta = cliente.getCuentas().get(numeroCuenta);
        
        System.out.println("Tipo de transacción:");
        if (cuenta instanceof CuentaAhorro || cuenta instanceof CuentaDebito) {
            System.out.println("1. Depósito");
            System.out.println("2. Retiro");
            System.out.println("3. Pago");
        } else if (cuenta instanceof CuentaCredito) {
            System.out.println("1. Abono");
            System.out.println("2. Retiro");
            System.out.println("3. Pago");
        }
        
        System.out.print("Seleccione el tipo: ");
        int tipoTransaccion = leerOpcion();
        
        System.out.print("Monto: ");
        double monto = Double.parseDouble(scanner.nextLine());
        
        boolean resultado = false;
        
        if (cuenta instanceof CuentaAhorro || cuenta instanceof CuentaDebito) {
            switch (tipoTransaccion) {
                case 1:
                    resultado = logicaNegocio.realizarDeposito(cuenta, monto);
                    break;
                case 2:
                    resultado = logicaNegocio.realizarRetiro(cuenta, monto);
                    break;
                case 3:
                    resultado = logicaNegocio.realizarPago(cuenta, monto);
                    break;
                default:
                    System.out.println("Tipo de transacción inválido.");
            }
        } else if (cuenta instanceof CuentaCredito) {
            switch (tipoTransaccion) {
                case 1:
                    resultado = logicaNegocio.realizarAbono(cuenta, monto);
                    break;
                case 2:
                    resultado = logicaNegocio.realizarRetiro(cuenta, monto);
                    break;
                case 3:
                    resultado = logicaNegocio.realizarPago(cuenta, monto);
                    break;
                default:
                    System.out.println("Tipo de transacción inválido.");
            }
        }
        
        if (!resultado) {
            System.out.println("Transacción fallida.");
        }
    }
    
    private void manejarGenerarIntereses(Cliente cliente) {
        System.out.println("\n=== GENERAR INTERESES ===");
        
        if (!logicaNegocio.tieneIntereses(cliente)) {
            System.out.println("No tiene cuentas que generen intereses.");
            return;
        }
        
        logicaNegocio.generarInteresesCliente(cliente);
    }
    
    public static void main(String[] args) {
        SistemaBancarioUI ui = new SistemaBancarioUI();
        ui.iniciar();
    }
}