package com.combate;

import java.util.Comparator;
import java.util.List;

import com.ente.Atributo;
import com.ente.Ente;

import java.util.ArrayList;

public final class Encuentro {
    private List<Ente> colaIniciativa;

    public Encuentro(Grupo aliados, Grupo enemigos) {
        this.colaIniciativa = new ArrayList<>();
        colaIniciativa.addAll(aliados.integrantes());
        colaIniciativa.addAll(enemigos.integrantes());
        
        // Ordenamos por Agilidad de mayor a menor
        colaIniciativa.sort(Comparator.comparingInt((Ente e) -> 
            e.obtenerValorAtributo(Atributo.AGILIDAD)).reversed());
    }

    public void ejecutarRonda() {
        for (Ente actuante : colaIniciativa) {
            if (actuante.obtenerVidaActual() > 0) {
                // Aquí el GestorEntrada o la IA deciden qué hacer
                System.out.println("\n>>> Turno de: " + actuante.obtenerNombre());
                // actuante.decidirAccion(); 
            }
        }
    }
}