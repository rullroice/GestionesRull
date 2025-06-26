package com.example.patrones.comportamentales.observer;

import com.example.modelo.insumo.Insumo;
import com.example.patrones.creacionales.singleton.ConfiguracionSistema;

public class ObservadorAlerta implements Observador {
    @Override
    public void actualizar(Sujeto sujeto) {
        if (sujeto instanceof Insumo) {
            Insumo insumo = (Insumo) sujeto;
            int umbral = ConfiguracionSistema.getInstancia().getUmbralStockBajo();
            if (insumo.getStock() < umbral) {
                System.out.println(">>> ALERTA: Stock bajo para '" + insumo.getNombre() + "'. Stock actual: " + insumo.getStock() + " (Umbral: " + umbral + ")");
            }
        }
    }
}