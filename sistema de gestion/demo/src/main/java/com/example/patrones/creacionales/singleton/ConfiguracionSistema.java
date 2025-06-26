package com.example.patrones.creacionales.singleton;

public class ConfiguracionSistema {
    private static ConfiguracionSistema instancia;
    private int umbralStockBajo = 10;
    private String nombreAlmacen = "Almacén Central";

    private ConfiguracionSistema() {}

    public static synchronized ConfiguracionSistema getInstancia() {
        if (instancia == null) {
            instancia = new ConfiguracionSistema();
        }
        return instancia;
    }

    public int getUmbralStockBajo() { return umbralStockBajo; }
    public String getNombreAlmacen() { return nombreAlmacen; }
}