package com.example.modelo.insumo;

import com.example.patrones.comportamentales.observer.Observador;
import com.example.patrones.comportamentales.observer.Sujeto;
import java.util.ArrayList;
import java.util.List;

public class Insumo implements ComponenteInsumo, Sujeto {
    private String nombre;
    private int stock;
    private List<Observador> observadores = new ArrayList<>();

    public Insumo(String nombre, int stockInicial) {
        this.nombre = nombre;
        this.stock = stockInicial;
    }

    @Override public String getNombre() { return nombre; }
    public int getStock() { return stock; }

    public void setStock(int nuevoStock) {
        this.stock = nuevoStock;
        System.out.println("Stock de '" + nombre + "' actualizado a: " + stock);
        notificarObservadores();
    }

    @Override public void mostrarDetalles() { System.out.println("- Insumo: " + nombre + " (Stock: " + stock + ")"); }
    @Override public void agregarObservador(Observador o) { observadores.add(o); }
    @Override public void removerObservador(Observador o) { observadores.remove(o); }
    @Override public void notificarObservadores() {
        for (Observador o : new ArrayList<>(observadores)) {
            o.actualizar(this);
        }
    }
}