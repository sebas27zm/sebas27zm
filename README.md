# Sistema Bancario - Java Swing Application

## Descripción

Sistema completo de gestión de cuentas bancarias desarrollado en Java utilizando los principios de Programación Orientada a Objetos (POO), patrones de diseño MVC y DAO, interfaz gráfica con Java Swing y persistencia de datos con MySQL.

## Características Principales

### 🏛️ Arquitectura
- **Patrón MVC (Model-View-Controller)**: Separación clara entre lógica de negocio, presentación y control
- **Patrón DAO (Data Access Object)**: Abstracción del acceso a datos
- **Programación Orientada a Objetos**: Aplicación completa de POO con herencia, polimorfismo, encapsulamiento y abstracción

### 👥 Gestión de Usuarios
- **Administrador único**: Gestión completa del sistema
- **Múltiples clientes**: Registro y autenticación de clientes
- **Autenticación segura**: Login con correo y contraseña

### 💳 Tipos de Cuentas
1. **Cuenta de Ahorro**
   - Saldo mínimo: $100
   - Genera intereses
   - Operaciones: Depósitos, retiros, pagos

2. **Cuenta de Débito**
   - Saldo no puede ser negativo
   - Genera intereses
   - Operaciones: Depósitos, retiros, pagos

3. **Cuenta de Crédito**
   - Saldo inicial: $0
   - Límite de crédito configurable
   - Tipos: Cashback, Gane Premios, Millas, etc.
   - Operaciones: Abonos, retiros, pagos

### 🔧 Funcionalidades
- ✅ Creación y gestión de cuentas
- ✅ Transacciones (depósitos, retiros, pagos, abonos)
- ✅ Generación de intereses
- ✅ Activación/desactivación de cuentas
- ✅ Reportes de estado de cuentas
- ✅ Interfaz gráfica moderna y intuitiva

## Tecnologías Utilizadas

- **Java 11+**: Lenguaje de programación principal
- **Java Swing**: Interfaz gráfica de usuario
- **MySQL 8.0+**: Base de datos relacional
- **Maven**: Gestión de dependencias y construcción del proyecto
- **JDBC**: Conectividad con base de datos

## Requisitos del Sistema

### Software Necesario
- Java Development Kit (JDK) 11 o superior
- MySQL Server 8.0 o superior
- Maven 3.6+ (opcional, incluido en la mayoría de IDEs)
- IDE recomendado: IntelliJ IDEA, Eclipse, o Visual Studio Code

### Dependencias
- MySQL Connector/J 8.0.33
- JUnit 5.9.2 (para pruebas)

## Instalación y Configuración

### 1. Clonar el Repositorio
```bash
git clone [URL_DEL_REPOSITORIO]
cd sistema-bancario
```

### 2. Configurar MySQL
1. Instalar MySQL Server
2. Crear la base de datos ejecutando el script:
```bash
mysql -u root -p < database_setup.sql
```

### 3. Configurar Conexión a Base de Datos
Editar el archivo `src/main/java/com/sistemabancario/util/DatabaseConnection.java`:
```java
private static final String URL = "jdbc:mysql://localhost:3306/sistema_bancario";
private static final String USER = "tu_usuario";
private static final String PASSWORD = "tu_contraseña";
```

### 4. Compilar y Ejecutar

#### Usando Maven
```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar la aplicación
mvn exec:java -Dexec.mainClass="com.sistemabancario.view.SistemaBancarioGUI"

# Crear JAR ejecutable
mvn clean package
java -jar target/sistema-bancario-1.0.0.jar
```

#### Usando IDE
1. Importar el proyecto como proyecto Maven
2. Ejecutar la clase principal: `com.sistemabancario.view.SistemaBancarioGUI`

## Estructura del Proyecto

```
src/main/java/com/sistemabancario/
├── model/                      # Modelos de datos
│   ├── Usuario.java           # Clase abstracta base
│   ├── Administrador.java     # Modelo del administrador
│   ├── Cliente.java           # Modelo del cliente
│   ├── Cuenta.java            # Clase abstracta de cuenta
│   ├── CuentaAhorro.java      # Cuenta de ahorro
│   ├── CuentaDebito.java      # Cuenta de débito
│   ├── CuentaCredito.java     # Cuenta de crédito
│   └── I*.java                # Interfaces
├── view/                       # Interfaz gráfica
│   ├── SistemaBancarioGUI.java # Ventana principal
│   ├── LoginDialog.java        # Diálogo de login
│   └── CrearAdministradorDialog.java # Crear admin
├── controller/                 # Controladores
│   └── SistemaBancarioController.java # Controlador principal
├── dao/                        # Acceso a datos
│   ├── I*DAO.java             # Interfaces DAO
│   └── *DAOImpl.java          # Implementaciones DAO
└── util/                       # Utilidades
    └── DatabaseConnection.java # Conexión a BD
```

## Uso del Sistema

### 1. Primera Ejecución
- Al iniciar por primera vez, crear un administrador del sistema
- Usar las credenciales del administrador para acceder al panel de administración

### 2. Panel de Administrador
- Registrar nuevos clientes
- Crear cuentas para los clientes
- Listar clientes y cuentas por tipo
- Activar/desactivar cuentas

### 3. Panel de Cliente
- Ver reporte de todas sus cuentas
- Realizar transacciones (depósitos, retiros, pagos, abonos)
- Generar intereses en cuentas de ahorro y débito

## Datos de Prueba

El script de base de datos incluye datos de prueba:

### Administrador
- **Correo**: admin@sistemabancario.com
- **Contraseña**: admin123

### Clientes de Prueba
- **Cliente 1**: juan.perez@email.com / juan123
- **Cliente 2**: maria.gonzalez@email.com / maria123

## Patrones de Diseño Implementados

### 1. Model-View-Controller (MVC)
- **Model**: Clases en el paquete `model`
- **View**: Interfaces gráficas en el paquete `view`
- **Controller**: Lógica de control en el paquete `controller`

### 2. Data Access Object (DAO)
- Interfaces DAO para abstracción del acceso a datos
- Implementaciones concretas para MySQL
- Separación clara entre lógica de negocio y persistencia

### 3. Singleton
- Implementado en `DatabaseConnection` para gestión de conexiones

### 4. Template Method
- Implementado en la clase abstracta `Cuenta`

## Principios SOLID Aplicados

- **Single Responsibility**: Cada clase tiene una responsabilidad específica
- **Open/Closed**: Extensible mediante herencia e interfaces
- **Liskov Substitution**: Las subclases pueden sustituir a sus clases base
- **Interface Segregation**: Interfaces específicas para cada funcionalidad
- **Dependency Inversion**: Dependencia de abstracciones, no de concreciones

## Contribución

1. Fork del repositorio
2. Crear rama para nueva funcionalidad (`git checkout -b feature/nueva-funcionalidad`)
3. Commit de cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## Contacto

Para preguntas o sugerencias sobre el proyecto, por favor crear un issue en el repositorio.

---

**Desarrollado como proyecto académico de Programación Orientada a Objetos**