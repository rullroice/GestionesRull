package com.example.patrones.comportamentales.observer;
public interface Sujeto {
    void agregarObservador(Observador o);
    void removerObservador(Observador o);
    void notificarObservadores();
}