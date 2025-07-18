#!/bin/bash

echo "=== COMPILANDO SISTEMA BANCARIO ==="

# Crear directorio de compilación
mkdir -p build

# Compilar todas las clases Java
javac -d build -cp src/main/java src/main/java/com/banco/modelo/*.java src/main/java/com/banco/servicio/*.java src/main/java/com/banco/interfaz/*.java src/main/java/com/banco/Main.java

if [ $? -eq 0 ]; then
    echo "Compilación exitosa!"
    echo ""
    echo "=== EJECUTANDO SISTEMA BANCARIO ==="
    echo ""
    
    # Ejecutar el programa
    java -cp build com.banco.Main
else
    echo "Error en la compilación."
fi