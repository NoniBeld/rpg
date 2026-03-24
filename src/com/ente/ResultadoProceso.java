package com.ente;

import com.ente.Funcion;

/**
 * Define en qué se convierte un Ente tras pasar por un proceso.
 */
public record ResultadoProceso(
    String nuevoNombre, 
    Funcion nuevaFuncion, 
    int valorNutricional,
    boolean esDesecho
) {}