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

- **Java 11+**: Lenguaje de programación principal con **JPMS (Java Platform Module System)**
- **Java Swing**: Interfaz gráfica de usuario
- **MySQL 8.0+**: Base de datos relacional
- **Maven**: Gestión de dependencias y construcción del proyecto
- **JDBC**: Conectividad con base de datos
- **Módulos Java**: Arquitectura modular con encapsulación fuerte

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

#### Usando Maven (Recomendado - con soporte modular)
```bash
# Compilar el proyecto con módulos
mvn clean compile

# Ejecutar la aplicación modular
mvn exec:java

# Crear JAR ejecutable modular
mvn clean package
java -jar target/sistema-bancario-1.0.0.jar

# Ejecutar directamente con Java (modo modular)
java --module-path target/classes:lib --module com.sistemabancario/com.sistemabancario.app.SistemaBancarioApp
```

#### Usando IDE
1. Importar el proyecto como proyecto Maven
2. Asegurar que el IDE soporte Java 11+ y módulos JPMS
3. Ejecutar la clase principal: `com.sistemabancario.app.SistemaBancarioApp`

#### Comandos adicionales para desarrollo modular
```bash
# Verificar dependencias de módulos
java --module-path target/classes --describe-module com.sistemabancario

# Listar módulos disponibles
java --module-path target/classes --list-modules

# Crear imagen de aplicación nativa (requiere JDK con jlink)
jlink --module-path target/classes:lib --add-modules com.sistemabancario --output dist/sistema-bancario
```

## Estructura del Proyecto

```
src/main/java/
├── module-info.java            # 🆕 Descriptor de módulo JPMS
└── com/sistemabancario/
    ├── app/                    # 🆕 Aplicación principal
    │   └── SistemaBancarioApp.java # Punto de entrada modular
    ├── service/                # 🆕 Capa de servicios
    │   ├── BankingService.java # Interfaz de servicios
    │   └── impl/
    │       └── BankingServiceImpl.java # Implementación
    ├── model/                  # Modelos de datos (EXPORTADO)
    │   ├── Usuario.java       # Clase abstracta base
    │   ├── Administrador.java # Modelo del administrador
    │   ├── Cliente.java       # Modelo del cliente
    │   ├── Cuenta.java        # Clase abstracta de cuenta
    │   ├── CuentaAhorro.java  # Cuenta de ahorro
    │   ├── CuentaDebito.java  # Cuenta de débito
    │   ├── CuentaCredito.java # Cuenta de crédito
    │   └── I*.java            # Interfaces
    ├── view/                  # Interfaz gráfica (ENCAPSULADO)
    │   ├── SistemaBancarioGUI.java # Ventana principal
    │   ├── LoginDialog.java   # Diálogo de login
    │   └── CrearAdministradorDialog.java # Crear admin
    ├── controller/            # Controladores (EXPORTADO)
    │   └── SistemaBancarioController.java # Controlador principal
    ├── dao/                   # Acceso a datos (EXPORTADO)
    │   ├── I*DAO.java        # Interfaces DAO
    │   └── *DAOImpl.java     # Implementaciones DAO
    └── util/                  # Utilidades (ENCAPSULADO)
        └── DatabaseConnection.java # Conexión a BD

modules/                       # 🆕 Módulos separados (opcional)
├── sistemabancario.core/      # Módulo de lógica de negocio
├── sistemabancario.data/      # Módulo de acceso a datos
└── sistemabancario.ui/        # Módulo de interfaz de usuario
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

## Arquitectura Modular (JPMS)

### 🎯 **Beneficios de la Modularidad**
- **Encapsulación fuerte**: Los paquetes no exportados están completamente ocultos
- **Dependencias explícitas**: Todas las dependencias declaradas en `module-info.java`
- **Seguridad mejorada**: Acceso controlado a APIs internas
- **Rendimiento optimizado**: Carga selectiva de módulos
- **Mantenibilidad**: Separación clara de responsabilidades

### 📦 **Estructura Modular**
```java
module com.sistemabancario {
    // Dependencias del JDK
    requires java.desktop;          // Swing GUI
    requires java.sql;              // JDBC
    
    // Dependencias externas
    requires mysql.connector.java;
    
    // Paquetes exportados (API pública)
    exports com.sistemabancario.model;      // Modelos de datos
    exports com.sistemabancario.controller; // Lógica de control
    exports com.sistemabancario.dao;        // Interfaces DAO
    
    // Paquetes encapsulados (internos):
    // - com.sistemabancario.view (UI interna)
    // - com.sistemabancario.util (utilidades)
    // - com.sistemabancario.service.impl (implementaciones)
}
```

### 🔧 **Comandos Modulares**
```bash
# Describir módulo
java --module-path target/classes --describe-module com.sistemabancario

# Listar dependencias
java --module-path target/classes --show-module-resolution

# Ejecutar aplicación modular
java --module-path target/classes:lib \
     --module com.sistemabancario/com.sistemabancario.app.SistemaBancarioApp
```

## Patrones de Diseño Implementados

### 1. Model-View-Controller (MVC)
- **Model**: Clases en el paquete `model` (EXPORTADO)
- **View**: Interfaces gráficas en el paquete `view` (ENCAPSULADO)
- **Controller**: Lógica de control en el paquete `controller` (EXPORTADO)

### 2. Data Access Object (DAO)
- Interfaces DAO para abstracción del acceso a datos (EXPORTADAS)
- Implementaciones concretas para MySQL (ENCAPSULADAS)
- Separación clara entre lógica de negocio y persistencia

### 3. Service Layer (Fachada)
- **Interfaz**: `BankingService` para operaciones de alto nivel
- **Implementación**: `BankingServiceImpl` encapsulada
- **Beneficio**: API simplificada para la capa de presentación

### 4. Singleton
- Implementado en `DatabaseConnection` para gestión de conexiones

### 5. Template Method
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