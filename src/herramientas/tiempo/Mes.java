package herramientas.tiempo;

import herramientas.clima.Estacion;

/**
 * Define cada mes del año místico vinculado a su estación.
 */
public enum Mes {
    // Primavera
    PRIMERO_PRIMA(Estacion.PRIMA), SEGUNDO_PRIMA(Estacion.PRIMA), 
    TERCERO_PRIMA(Estacion.PRIMA), CUARTO_PRIMA(Estacion.PRIMA),
    
    // Verano (Veri)
    PRIMERO_VERI(Estacion.VERI), SEGUNDO_VERI(Estacion.VERI), 
    TERCERO_VERI(Estacion.VERI), CUARTO_VERI(Estacion.VERI),
    
    // Otoño (Otto)
    PRIMERO_OTTO(Estacion.OTTO), SEGUNDO_OTTO(Estacion.OTTO), 
    TERCERO_OTTO(Estacion.OTTO), CUARTO_OTTO(Estacion.OTTO),
    
    // Invierno (Verno)
    PRIMERO_VERNO(Estacion.VERNO), SEGUNDO_VERNO(Estacion.VERNO), 
    TERCERO_VERNO(Estacion.VERNO), CUARTO_VERNO(Estacion.VERNO);

    private final Estacion estacionPerteneciente;

    Mes(Estacion estacion) {
        this.estacionPerteneciente = estacion;
    }

    public Estacion obtenerEstacion() {
        return estacionPerteneciente;
    }
}