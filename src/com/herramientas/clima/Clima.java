package com.herramientas.clima;

/**
 * Representa las condiciones atmosféricas actuales.
 */
public record Clima(String nombre, float temperaturaAmbiente, float humedad) {
    // Los records son perfectos aquí porque el clima en un momento X es un dato inmutable
}