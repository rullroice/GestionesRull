package com.example.modelo.insumo;

import java.util.ArrayList;
import java.util.List;

public class GrupoInsumo implements ComponenteInsumo {
    private String nombre;
    private List<ComponenteInsumo> componentes = new ArrayList<>();

    public GrupoInsumo(String nombre) { this.nombre = nombre; }
    public void agregar(ComponenteInsumo componente) { componentes.add(componente); }
    @Override public String getNombre() { return nombre; }

    @Override
    public void mostrarDetalles() {
        System.out.println("Grupo: " + nombre);
        for (ComponenteInsumo componente : componentes) {
            componente.mostrarDetalles();
        }
    }
}