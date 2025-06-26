package com.example.modelo.movimiento;
import com.example.modelo.insumo.Insumo;
public class MovimientoSalida implements Movimiento {
    private int cantidad;
    public MovimientoSalida(int cantidad) { this.cantidad = cantidad; }
    @Override public void ejecutar(Insumo insumo) { insumo.setStock(insumo.getStock() - cantidad); }
}