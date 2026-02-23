package com.meridia.entities;

public interface Habilidad {
    String getNombre();
    void ejecutar(Entidad usuario, Entidad objetivo);
    int nivelRequerido();
}