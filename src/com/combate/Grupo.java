package com.combate;

import java.util.ArrayList;
import java.util.List;

import com.ente.Ente;

public record Grupo(String nombreEquipo, List<Ente> integrantes) {
    public Grupo(String nombre) {
        this(nombre, new ArrayList<>());
    }

    public void añadir(Ente e) { integrantes.add(e); }
    
    public boolean estaVivo() {
        return integrantes.stream().anyMatch(e -> e.obtenerVidaActual() > 0);
    }
}