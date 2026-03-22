package debug.z;

import herramientas.tiempo.CalendarioLunar;

public class PruebaTiempo001 {
    public static void main(String[] args) {
        CalendarioLunar calendario = new CalendarioLunar();
        
        // Simulamos el paso de un mes entero (28 días)
        for(int i = 0; i < 30; i++) {
            calendario.obtenerFechaFormateada();
            calendario.avanzarDia();
        }
    }
}