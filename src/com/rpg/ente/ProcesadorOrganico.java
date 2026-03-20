package com.rpg.ente;

import com.rpg.ente.Ente;
import com.rpg.ente.Funcion;
import herramientas.texto.Narrador;

public class ProcesadorOrganico implements SistemaDigestivo {
    private Ente contenidoEnEstomago;
    private double tiempoDigestion = 0;

    @Override
    public void ingerir(Ente alimento, Ente organismo) {
        if (alimento.obtenerFuncionActual() == Funcion.ALIMENTO) {
            this.contenidoEnEstomago = alimento;
            this.tiempoDigestion = 0;
            Narrador.obtenerInstancia().narrar(organismo.obtenerNombre() + " engulle el " + alimento.obtenerNombre(), 30);
        }
    }

    @Override
    public void procesar(double delta, Ente organismo) {
        if (contenidoEnEstomago != null) {
            tiempoDigestion += delta;
            // Después de 10 segundos de juego, se digiere
            if (tiempoDigestion >= 10.0) {
                excretar(organismo);
            }
        }
    }

    @Override
    public void excretar(Ente organismo) {
        Narrador.obtenerInstancia().narrar(organismo.obtenerNombre() + " ha completado la digestión.", 40);
        // Aquí transformamos el alimento en un "Desecho" o "Producto Especial"
        if (contenidoEnEstomago.obtenerNombre().contains("Grano de Café")) {
            contenidoEnEstomago.cambiarNombre("Café de Especialidad");
            Narrador.obtenerInstancia().narrar("¡Se ha obtenido un objeto valioso!", 20);
        }
        
        // El objeto sale del sistema digestivo y vuelve al mundo/inventario
        this.contenidoEnEstomago = null;
    }
}