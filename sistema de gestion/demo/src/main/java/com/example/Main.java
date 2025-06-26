package com.example;

import com.example.modelo.insumo.*;
import com.example.modelo.movimiento.Movimiento;
import com.example.patrones.comportamentales.observer.ObservadorAlerta;
import com.example.patrones.creacionales.factoryMethod.FabricaMovimiento;
import com.example.patrones.creacionales.singleton.ConfiguracionSistema;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Gestión de Inventario...");

        // S
        System.out.println("\n--- Probando Singleton ---");
        ConfiguracionSistema config = ConfiguracionSistema.getInstancia();
        System.out.println("Bienvenido a: " + config.getNombreAlmacen());
        System.out.println("Umbral de stock bajo: " + config.getUmbralStockBajo());

        // C
        System.out.println("\n--- Probando Composite ---");
        GrupoInsumo inventario = new GrupoInsumo("Inventario General");
        Insumo lapices = new Insumo("Lápices Grafito", 50);
        Insumo resmas = new Insumo("Resmas de Papel", 30);
        inventario.agregar(lapices);
        inventario.agregar(resmas);

        inventario.mostrarDetalles();

        // O
        System.out.println("\n--- Probando Observer ---");
        ObservadorAlerta alertaStock = new ObservadorAlerta();
        lapices.agregarObservador(alertaStock);
        System.out.println("Observer de alerta de stock asignado a 'Lápices'.");

        // F
        System.out.println("\n--- Probando Factory Method y Observer en acción ---");
        FabricaMovimiento fabrica = new FabricaMovimiento();

        System.out.println("Ejecutando movimiento de salida para Lápices (debería disparar la alerta)...");
        Movimiento salidaLapices = fabrica.crearMovimiento("SALIDA", 45); 
        salidaLapices.ejecutar(lapices);

        System.out.println("\nEjecutando movimiento de entrada para Resmas...");
        Movimiento entradaResmas = fabrica.crearMovimiento("ENTRADA", 100); 
        entradaResmas.ejecutar(resmas);

        System.out.println("\n----------------------------------------");
        System.out.println("Estado final del inventario:");
        inventario.mostrarDetalles();
    }
}