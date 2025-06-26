package com.example.patrones.creacionales.factoryMethod;
import com.example.modelo.movimiento.Movimiento;
import com.example.modelo.movimiento.MovimientoEntrada;
import com.example.modelo.movimiento.MovimientoSalida;

public class FabricaMovimiento {
    public Movimiento crearMovimiento(String tipo, int cantidad) {
        if (tipo == null) {
            return null;
        }
        if (tipo.equalsIgnoreCase("ENTRADA")) {
            return new MovimientoEntrada(cantidad);
        } else if (tipo.equalsIgnoreCase("SALIDA")) {
            return new MovimientoSalida(cantidad);
        }
        return null;
    }
}