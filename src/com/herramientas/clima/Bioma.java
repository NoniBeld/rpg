package com.herramientas.clima;

import java.util.List;

import com.ente.Funcion;

/**
 * Define las reglas de generación de una zona del mundo.
 */
public enum Bioma {
    DESIERTO("Dunas Infinitas", 45.0f, 0.1f),
    SELVA("Selva Esmeralda", 30.0f, 0.9f),
    TUNDRA("Páramo Helado", -15.0f, 0.3f),
    VOLCAN("Tierras de Ceniza", 60.0f, 0.05f), 
    CUEVA_PROFUNDA("Colmena Gelida ", 0.0f, 50.0f), 
    BOSQUE("Bosque Rojo", 07.0f, 75.0f ), 
    PLANICIE("Plancie de Flores",30.0f, 50.0f) ;

    private final String nombre;
    private final float temperaturaPromedio;
    private final float humedadPromedio;

    Bioma(String nombre, float temp, float hum) {
        this.nombre = nombre;
        this.temperaturaPromedio = temp;
        this.humedadPromedio = hum;
    }

    public Clima generarClimaActual() {
        // Aquí podríamos añadir un factor aleatorio para que no siempre sea igual
        return new Clima(this.nombre, temperaturaPromedio, humedadPromedio);
    }

    public String obtenerNombre() { return nombre; }
    public float obtenerTemperaturaBase() { return temperaturaPromedio; }
    public float obtenerHumedadBase() { return humedadPromedio; }
}