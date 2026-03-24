package debug.z;

import com.ente.*;

import com.herramientas.clima.Estacion;
import com.herramientas.tiempo.*;

public class PruebaAtributosTiempo000 {
    public static void main(String[] args) {
        Ente slime = Creador.obtenerInstancia().crearNuevoEnte("Slime Glacial", Funcion.OBJETO);
        
        // Asignamos fuerza inicial
        slime.establecerValorAtributo(Atributo.FUERZA, 10);
        
        // Imaginamos que el Calendario dice que estamos en VERNO
        Estacion estacionActual = Estacion.VERNO;
        
        if (estacionActual == Estacion.VERNO) {
            int fuerzaExtra = 5;
            int fuerzaTotal = slime.obtenerValorAtributo(Atributo.FUERZA) + fuerzaExtra;
            slime.establecerValorAtributo(Atributo.FUERZA, fuerzaTotal);
            
            slime.hablar("¡Me siento más fuerte en el frío del " + estacionActual + "!");
            slime.hablar("Fuerza actual: " + slime.obtenerValorAtributo(Atributo.FUERZA));
        }
    }
}