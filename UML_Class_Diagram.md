# Diagrama de Clases UML - Sistema Bancario

## Representación Textual del Diagrama de Clases

```
┌─────────────────────────────────────────────────────────────────────────────────┐
│                                SISTEMA BANCARIO                                 │
│                              Diagrama de Clases UML                            │
└─────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────┐         ┌─────────────────────────────┐
│        <<abstract>>         │         │         <<interface>>       │
│          Usuario            │         │        ITransaccion         │
├─────────────────────────────┤         ├─────────────────────────────┤
│ # nombre: String            │         │ + retirar(double): boolean  │
│ # apellidos: String         │         │ + pagar(double): boolean    │
│ # numeroCedula: String      │         └─────────────────────────────┘
│ # correoElectronico: String │                        ▲
│ # contrasena: String        │                        │
├─────────────────────────────┤                        │
│ + getNombre(): String       │         ┌─────────────────────────────┐
│ + getApellidos(): String    │         │         <<interface>>       │
│ + validarCredenciales(...): │         │         IDeposito           │
│   boolean                   │         ├─────────────────────────────┤
│ + toString(): String        │         │ + depositar(double): boolean│
└─────────────────────────────┘         └─────────────────────────────┘
            ▲                                          ▲
            │                                          │
    ┌───────┴───────┐                   ┌─────────────────────────────┐
    │               │                   │         <<interface>>       │
┌───▼─────┐    ┌────▼──────┐           │           IAbono            │
│Administr│    │  Cliente  │           ├─────────────────────────────┤
│ador     │    ├───────────┤           │ + abonar(double): boolean   │
└─────────┘    │ - sexo    │           └─────────────────────────────┘
               │ - profesion│                          ▲
               │ - direccion│                          │
               │ - cuentas: │           ┌─────────────────────────────┐
               │   List<..> │           │         <<interface>>       │
               ├───────────┤           │          IInteres           │
               │ + reporteC│           ├─────────────────────────────┤
               │   uentas()│           │ + generarIntereses(): void  │
               └───────────┘           │ + getPorcentajeInteres()    │
                    │                  │ + setPorcentajeInteres()    │
                    │ 1              * └─────────────────────────────┘
                    │                                 ▲
                    ▼                                 │
┌─────────────────────────────────────────────────────────────┐
│                    <<abstract>>                             │
│                      Cuenta                                 │
├─────────────────────────────────────────────────────────────┤
│ # numeroCuenta: int                                         │
│ # saldo: double                                             │
│ # activa: boolean                                           │
│ # propietario: Cliente                                      │
│ + contadorCuentas: int (static)                            │
├─────────────────────────────────────────────────────────────┤
│ + getNumeroCuenta(): int                                    │
│ + getSaldo(): double                                        │
│ + isActiva(): boolean                                       │
│ + setActiva(boolean): void                                  │
│ + getTipoCuenta(): String {abstract}                        │
│ + retirar(double): boolean {abstract}                       │
│ + pagar(double): boolean {abstract}                         │
│ # validarTransaccion(double): boolean                       │
│ + toString(): String                                        │
└─────────────────────────────────────────────────────────────┘
                              ▲
            ┌─────────────────┼─────────────────┐
            │                 │                 │
┌───────────▼──────────┐ ┌────▼─────────┐ ┌────▼──────────────┐
│    CuentaAhorro      │ │ CuentaDebito │ │   CuentaCredito   │
├──────────────────────┤ ├──────────────┤ ├───────────────────┤
│ - SALDO_MINIMO: 100  │ │ - porcentaje │ │ - limiteCredito   │
│ - porcentajeInteres  │ │   Interes    │ │ - tipo: String    │
├──────────────────────┤ ├──────────────┤ ├───────────────────┤
│ + depositar(): bool  │ │ + depositar()│ │ + abonar(): bool  │
│ + retirar(): bool    │ │ + retirar()  │ │ + retirar(): bool │
│ + pagar(): bool      │ │ + pagar()    │ │ + pagar(): bool   │
│ + generarIntereses() │ │ + generarInt│ │ + getCreditoDisp()│
│ + getTipoCuenta()    │ │   ereses()   │ │ + getTipoCuenta() │
└──────────────────────┘ │ + getTipoCu  │ └───────────────────┘
      implements         │   enta()     │       implements
   IDeposito, IInteres   └──────────────┘         IAbono
                           implements
                      IDeposito, IInteres

┌─────────────────────────────────────────────────────────────────────────────────┐
│                               CAPA DAO                                         │
└─────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────┐    ┌─────────────────────────────┐
│      <<interface>>          │    │      <<interface>>          │
│    IAdministradorDAO        │    │       IClienteDAO           │
├─────────────────────────────┤    ├─────────────────────────────┤
│ + crear(Admin): boolean     │    │ + crear(Cliente): boolean   │
│ + obtener(): Administrador  │    │ + obtenerPorId(int): Client │
│ + actualizar(Admin): bool   │    │ + obtenerPorCorreo(String)  │
│ + eliminar(): boolean       │    │ + obtenerTodos(): List<...> │
│ + existe(): boolean         │    │ + actualizar(Cliente): bool │
└─────────────────────────────┘    │ + eliminar(int): boolean    │
            ▲                       │ + existeCorreo(String): bool│
            │                       └─────────────────────────────┘
            │                                      ▲
┌───────────▼─────────────────┐                   │
│   AdministradorDAOImpl      │    ┌──────────────▼──────────────┐
├─────────────────────────────┤    │      ClienteDAOImpl         │
│ + crear(Admin): boolean     │    ├─────────────────────────────┤
│ + obtener(): Administrador  │    │ + crear(Cliente): boolean   │
│ + actualizar(Admin): bool   │    │ + obtenerPorId(int): Client │
│ + eliminar(): boolean       │    │ + obtenerPorCorreo(String)  │
│ + existe(): boolean         │    │ + obtenerTodos(): List<...> │
└─────────────────────────────┘    │ + actualizar(Cliente): bool │
                                   │ + eliminar(int): boolean    │
                                   │ + existeCorreo(String): bool│
┌─────────────────────────────┐    │ - mapearResultSetACliente() │
│      <<interface>>          │    └─────────────────────────────┘
│       ICuentaDAO            │
├─────────────────────────────┤
│ + crear(Cuenta): boolean    │    ┌─────────────────────────────┐
│ + obtenerPorNumero(int)     │    │     DatabaseConnection      │
│ + obtenerPorCliente(int)    │    ├─────────────────────────────┤
│ + obtenerPorTipo(String)    │    │ - URL: String (static)      │
│ + obtenerTodas(): List<...> │    │ - USER: String (static)     │
│ + actualizar(Cuenta): bool  │    │ - PASSWORD: String (static) │
│ + eliminar(int): boolean    │    │ - instance: DatabaseConn.   │
└─────────────────────────────┘    │ - connection: Connection    │
                                   ├─────────────────────────────┤
                                   │ + getInstance(): Database.. │
                                   │ + getConnection(): Connec.. │
                                   │ + closeConnection(): void   │
                                   │ - isConnectionValid(): bool │
                                   └─────────────────────────────┘
                                              Singleton

┌─────────────────────────────────────────────────────────────────────────────────┐
│                            CAPA CONTROLADOR                                     │
└─────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────┐
│                      SistemaBancarioController                                  │
├─────────────────────────────────────────────────────────────────────────────────┤
│ - administradorDAO: IAdministradorDAO                                           │
│ - clienteDAO: IClienteDAO                                                       │
│ - cuentaDAO: ICuentaDAO                                                         │
├─────────────────────────────────────────────────────────────────────────────────┤
│ + crearAdministrador(...): boolean                                              │
│ + validarAdministrador(String, String): boolean                                 │
│ + existeAdministrador(): boolean                                                │
│ + registrarCliente(...): boolean                                                │
│ + validarCliente(String, String): Cliente                                       │
│ + obtenerTodosLosClientes(): List<Cliente>                                      │
│ + crearCuentaAhorro(Cliente, double, double): boolean                          │
│ + crearCuentaDebito(Cliente, double, double): boolean                          │
│ + crearCuentaCredito(Cliente, double, String): boolean                         │
│ + realizarDeposito(Cuenta, double): boolean                                     │
│ + realizarRetiro(Cuenta, double): boolean                                       │
│ + realizarPago(Cuenta, double): boolean                                         │
│ + realizarAbono(Cuenta, double): boolean                                        │
│ + generarInteresesCliente(Cliente): void                                        │
└─────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────┐
│                               CAPA VISTA                                        │
└─────────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────────┐
│                           SistemaBancarioGUI                                    │
├─────────────────────────────────────────────────────────────────────────────────┤
│ - controller: SistemaBancarioController                                         │
│ - mainPanel: JPanel                                                             │
│ - cardLayout: CardLayout                                                        │
│ + PRIMARY_COLOR, SECONDARY_COLOR, etc. (static final)                          │
├─────────────────────────────────────────────────────────────────────────────────┤
│ + SistemaBancarioGUI()                                                          │
│ - initializeGUI(): void                                                         │
│ - crearPantallaInicio(): void                                                   │
│ - crearHeader(String, String): JPanel                                           │
│ - crearPanelOpcion(...): JPanel                                                 │
│ - crearBoton(String, Color): JButton                                            │
│ - mostrarCrearAdministrador(): void                                             │
│ - mostrarLoginAdministrador(): void                                             │
│ - mostrarLoginCliente(): void                                                   │
│ + mostrarMenuAdministrador(): void                                              │
│ + mostrarMenuCliente(Cliente): void                                             │
│ + main(String[]): void                                                          │
└─────────────────────────────────────────────────────────────────────────────────┘
                                      │
                    ┌─────────────────┼─────────────────┐
                    │                 │                 │
┌───────────────────▼───────┐ ┌───────▼────────┐ ┌──────▼─────────────────┐
│ CrearAdministradorDialog  │ │   LoginDialog  │ │ [Otros Diálogos]       │
├───────────────────────────┤ ├────────────────┤ ├────────────────────────┤
│ - controller: Sistema...  │ │ - controller   │ │ MenuAdministradorPanel │
│ - txtNombre: JTextField   │ │ - parentFrame  │ │ MenuClientePanel       │
│ - txtApellidos: JTextField│ │ - esAdministr. │ │ TransaccionDialog      │
│ - txtCedula: JTextField   │ │ - txtCorreo    │ │ ReportePanel           │
│ - txtCorreo: JTextField   │ │ - txtContraseña│ │ ...                    │
│ - txtContrasena: JPassw.. │ ├────────────────┤ └────────────────────────┘
│ - txtConfirmarContr.: ... │ │ + LoginDialog()│
├───────────────────────────┤ │ - iniciarSes() │
│ + CrearAdministradorD...()│ │ - mostrarError │
│ - crearAdministrador()    │ │   Login()      │
│ - validarCampos(): boolean│ └────────────────┘
└───────────────────────────┘

RELACIONES PRINCIPALES:
═══════════════════════════

Usuario ◄──────── Administrador (Herencia)
Usuario ◄──────── Cliente (Herencia)

Cliente ──────────► Cuenta (Composición 1:*)
Cuenta ◄────────── CuentaAhorro (Herencia)
Cuenta ◄────────── CuentaDebito (Herencia)  
Cuenta ◄────────── CuentaCredito (Herencia)

CuentaAhorro ──────► IDeposito, IInteres (Implementación)
CuentaDebito ──────► IDeposito, IInteres (Implementación)
CuentaCredito ─────► IAbono (Implementación)
Cuenta ─────────────► ITransaccion (Implementación)

SistemaBancarioController ──► IAdministradorDAO (Dependencia)
SistemaBancarioController ──► IClienteDAO (Dependencia)
SistemaBancarioController ──► ICuentaDAO (Dependencia)

AdministradorDAOImpl ─────► IAdministradorDAO (Implementación)
ClienteDAOImpl ───────────► IClienteDAO (Implementación)

SistemaBancarioGUI ───────► SistemaBancarioController (Dependencia)
CrearAdministradorDialog ─► SistemaBancarioController (Dependencia)
LoginDialog ──────────────► SistemaBancarioController (Dependencia)
```

## Descripción de las Relaciones

### Herencia
- `Usuario` es la clase abstracta base para `Administrador` y `Cliente`
- `Cuenta` es la clase abstracta base para `CuentaAhorro`, `CuentaDebito` y `CuentaCredito`

### Implementación de Interfaces
- Todas las cuentas implementan `ITransaccion`
- `CuentaAhorro` y `CuentaDebito` implementan `IDeposito` e `IInteres`
- `CuentaCredito` implementa `IAbono`

### Composición
- `Cliente` tiene una lista de `Cuenta` (1:*)
- Relación fuerte donde las cuentas no existen sin el cliente

### Dependencia
- Los controladores dependen de las interfaces DAO
- Las vistas dependen del controlador
- Los DAO implementaciones dependen de `DatabaseConnection`

### Patrones Aplicados
- **MVC**: Separación clara entre Model, View y Controller
- **DAO**: Abstracción del acceso a datos
- **Singleton**: En `DatabaseConnection`
- **Template Method**: En la clase abstracta `Cuenta`