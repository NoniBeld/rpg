package com.rpg.motor;

import com.rpg.teatro.Escena;
import com.rpg.teatro.GeneradorProcedural;
import com.rpg.ente.Ente;
import herramientas.tiempo.CalendarioLunar;

public class Navegador {
    private int x = 0, y = 0, z = 0;
    private Escena escenaActual;

    public void mover(String direccion, Ente jugador, CalendarioLunar calendario) {
        switch (direccion.toLowerCase()) {
            case "norte" -> y++;
            case "sur"   -> y--;
            case "este"  -> x++;
            case "oeste" -> x--;
            case "arriba" -> z++;
            case "abajo"  -> z--;
        }
        
        // Generamos (o cargamos) la nueva escena
        this.escenaActual = GeneradorProcedural.generarEscena(x, y, z, calendario);
        
        // Colocamos al jugador en la nueva escena
        this.escenaActual.agregarEnte(jugador);
        
        // Ejecutamos la descripción
        this.escenaActual.jugar();
    }
}