import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaBancario {
    private Administrador administrador;
    private List<Cliente> clientes;
    private Scanner scanner;
    
    public SistemaBancario() {
        this.clientes = new ArrayList<>();
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
                    if (administrador == null) {
                        crearAdministrador();
                    } else {
                        System.out.println("Ya existe un administrador registrado.");
                    }
                    break;
                case 2:
                    loginAdministrador();
                    break;
                case 3:
                    loginCliente();
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
    
    private void crearAdministrador() {
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
        
        administrador = new Administrador(nombre, apellidos, cedula, correo, contrasena);
        System.out.println("Administrador creado exitosamente.");
    }
    
    private void loginAdministrador() {
        if (administrador == null) {
            System.out.println("No hay administrador registrado. Debe crear uno primero.");
            return;
        }
        
        System.out.println("\n=== LOGIN ADMINISTRADOR ===");
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        
        if (administrador.validarCredenciales(correo, contrasena)) {
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
                    registrarCliente();
                    break;
                case 2:
                    listarClientes();
                    break;
                case 3:
                    listarCuentasPorTipo("Ahorro");
                    break;
                case 4:
                    listarCuentasPorTipo("Débito");
                    break;
                case 5:
                    listarCuentasPorTipo("Crédito");
                    break;
                case 6:
                    crearCuentaParaCliente();
                    break;
                case 7:
                    activarDesactivarCuenta();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
    
    private void registrarCliente() {
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
        
        // Verificar si ya existe un cliente con el mismo correo
        for (Cliente cliente : clientes) {
            if (cliente.getCorreoElectronico().equals(correo)) {
                System.out.println("Ya existe un cliente con ese correo electrónico.");
                return;
            }
        }
        
        Cliente cliente = new Cliente(nombre, apellidos, cedula, correo, contrasena, sexo, profesion, direccion);
        clientes.add(cliente);
        System.out.println("Cliente registrado exitosamente.");
    }
    
    private void listarClientes() {
        System.out.println("\n=== LISTA DE CLIENTES ===");
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
        } else {
            for (int i = 0; i < clientes.size(); i++) {
                System.out.println("\n--- Cliente " + (i + 1) + " ---");
                System.out.println(clientes.get(i));
            }
        }
    }
    
    private void listarCuentasPorTipo(String tipo) {
        System.out.println("\n=== LISTA DE CUENTAS DE " + tipo.toUpperCase() + " ===");
        boolean encontradas = false;
        
        for (Cliente cliente : clientes) {
            for (Cuenta cuenta : cliente.getCuentas()) {
                String tipoCuenta = cuenta.getTipoCuenta();
                if ((tipo.equals("Ahorro") && tipoCuenta.contains("Ahorro")) ||
                    (tipo.equals("Débito") && tipoCuenta.contains("Débito")) ||
                    (tipo.equals("Crédito") && tipoCuenta.contains("Crédito"))) {
                    System.out.println("\n" + cuenta);
                    encontradas = true;
                }
            }
        }
        
        if (!encontradas) {
            System.out.println("No hay cuentas de " + tipo.toLowerCase() + " registradas.");
        }
    }
    
    private void crearCuentaParaCliente() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        
        System.out.println("\n=== CREAR CUENTA PARA CLIENTE ===");
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
        
        try {
            switch (tipoCuenta) {
                case 1:
                    crearCuentaAhorro(cliente);
                    break;
                case 2:
                    crearCuentaDebito(cliente);
                    break;
                case 3:
                    crearCuentaCredito(cliente);
                    break;
                default:
                    System.out.println("Tipo de cuenta inválido.");
            }
        } catch (Exception e) {
            System.out.println("Error al crear la cuenta: " + e.getMessage());
        }
    }
    
    private void crearCuentaAhorro(Cliente cliente) {
        System.out.print("Saldo inicial (mínimo $100): ");
        double saldo = Double.parseDouble(scanner.nextLine());
        System.out.print("Porcentaje de interés: ");
        double interes = Double.parseDouble(scanner.nextLine());
        
        CuentaAhorro cuenta = new CuentaAhorro(cliente, saldo, interes);
        cliente.agregarCuenta(cuenta);
        System.out.println("Cuenta de ahorro creada exitosamente.");
    }
    
    private void crearCuentaDebito(Cliente cliente) {
        System.out.print("Saldo inicial: ");
        double saldo = Double.parseDouble(scanner.nextLine());
        System.out.print("Porcentaje de interés: ");
        double interes = Double.parseDouble(scanner.nextLine());
        
        CuentaDebito cuenta = new CuentaDebito(cliente, saldo, interes);
        cliente.agregarCuenta(cuenta);
        System.out.println("Cuenta de débito creada exitosamente.");
    }
    
    private void crearCuentaCredito(Cliente cliente) {
        System.out.print("Límite de crédito: ");
        double limite = Double.parseDouble(scanner.nextLine());
        System.out.print("Tipo de crédito (ej: Cashback, Gane Premios, Millas): ");
        String tipo = scanner.nextLine();
        
        CuentaCredito cuenta = new CuentaCredito(cliente, limite, tipo);
        cliente.agregarCuenta(cuenta);
        System.out.println("Cuenta de crédito creada exitosamente.");
    }
    
    private void activarDesactivarCuenta() {
        System.out.println("\n=== ACTIVAR/DESACTIVAR CUENTA ===");
        System.out.print("Número de cuenta: ");
        int numeroCuenta = leerOpcion();
        
        Cuenta cuenta = buscarCuentaPorNumero(numeroCuenta);
        if (cuenta == null) {
            System.out.println("Cuenta no encontrada.");
            return;
        }
        
        System.out.println("Estado actual: " + (cuenta.isActiva() ? "Activa" : "Inactiva"));
        System.out.print("¿Desea " + (cuenta.isActiva() ? "desactivar" : "activar") + " la cuenta? (s/n): ");
        String respuesta = scanner.nextLine();
        
        if (respuesta.equalsIgnoreCase("s")) {
            cuenta.setActiva(!cuenta.isActiva());
            System.out.println("Cuenta " + (cuenta.isActiva() ? "activada" : "desactivada") + " exitosamente.");
        }
    }
    
    private Cuenta buscarCuentaPorNumero(int numeroCuenta) {
        for (Cliente cliente : clientes) {
            for (Cuenta cuenta : cliente.getCuentas()) {
                if (cuenta.getNumeroCuenta() == numeroCuenta) {
                    return cuenta;
                }
            }
        }
        return null;
    }
    
    private void loginCliente() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados.");
            return;
        }
        
        System.out.println("\n=== LOGIN CLIENTE ===");
        System.out.print("Correo electrónico: ");
        String correo = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        
        Cliente cliente = null;
        for (Cliente c : clientes) {
            if (c.validarCredenciales(correo, contrasena)) {
                cliente = c;
                break;
            }
        }
        
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
                    realizarTransaccion(cliente);
                    break;
                case 3:
                    generarIntereses(cliente);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opción inválida. Intente nuevamente.");
            }
        }
    }
    
    private void realizarTransaccion(Cliente cliente) {
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
        
        if (cuenta instanceof CuentaAhorro) {
            CuentaAhorro cuentaAhorro = (CuentaAhorro) cuenta;
            switch (tipoTransaccion) {
                case 1:
                    cuentaAhorro.depositar(monto);
                    break;
                case 2:
                    cuentaAhorro.retirar(monto);
                    break;
                case 3:
                    cuentaAhorro.pagar(monto);
                    break;
                default:
                    System.out.println("Tipo de transacción inválido.");
            }
        } else if (cuenta instanceof CuentaDebito) {
            CuentaDebito cuentaDebito = (CuentaDebito) cuenta;
            switch (tipoTransaccion) {
                case 1:
                    cuentaDebito.depositar(monto);
                    break;
                case 2:
                    cuentaDebito.retirar(monto);
                    break;
                case 3:
                    cuentaDebito.pagar(monto);
                    break;
                default:
                    System.out.println("Tipo de transacción inválido.");
            }
        } else if (cuenta instanceof CuentaCredito) {
            CuentaCredito cuentaCredito = (CuentaCredito) cuenta;
            switch (tipoTransaccion) {
                case 1:
                    cuentaCredito.abonar(monto);
                    break;
                case 2:
                    cuentaCredito.retirar(monto);
                    break;
                case 3:
                    cuentaCredito.pagar(monto);
                    break;
                default:
                    System.out.println("Tipo de transacción inválido.");
            }
        }
    }
    
    private void generarIntereses(Cliente cliente) {
        System.out.println("\n=== GENERAR INTERESES ===");
        boolean tieneIntereses = false;
        
        for (Cuenta cuenta : cliente.getCuentas()) {
            if (cuenta instanceof CuentaAhorro) {
                System.out.println("Generando intereses para cuenta de ahorro #" + cuenta.getNumeroCuenta());
                ((CuentaAhorro) cuenta).generarIntereses();
                tieneIntereses = true;
            } else if (cuenta instanceof CuentaDebito) {
                System.out.println("Generando intereses para cuenta de débito #" + cuenta.getNumeroCuenta());
                ((CuentaDebito) cuenta).generarIntereses();
                tieneIntereses = true;
            }
        }
        
        if (!tieneIntereses) {
            System.out.println("No tiene cuentas que generen intereses.");
        }
    }
    
    public static void main(String[] args) {
        SistemaBancario sistema = new SistemaBancario();
        sistema.iniciar();
    }
}