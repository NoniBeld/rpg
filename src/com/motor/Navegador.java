package com.motor;

import com.ente.Ente;
import com.rpg.teatro.Escena;
import com.rpg.teatro.GeneradorProcedural;

import com.herramientas.guardado.GestorMapa;
import com.herramientas.tiempo.CalendarioLunar;
import java.io.File;

public class Navegador {
    private int x = 0, y = 0, z = 0;
    private Escena escenaActual;

    public Escena viajar(String direccion, Ente jugador, CalendarioLunar calendario) {
        // 1. Antes de irnos, guardamos el estado de la escena actual (si existe)
        if (escenaActual != null) {
            GestorMapa.dormirEscena(escenaActual);
        }

        // 2. Actualizamos coordenadas
        actualizarCoordenadas(direccion);

        String nombreArchivo = "usuario/mapa/Sector_" + x + "_" + y + "_" + z + ".txt";
        File archivoEscena = new File(nombreArchivo);

        // 3. Lógica de Carga vs Generación
        if (archivoEscena.exists()) {
            // TERRENO CONOCIDO: Cargamos los cambios que el jugador hizo antes
            this.escenaActual = GestorMapa.despertarEscena(nombreArchivo, calendario);
            System.out.println("[SISTEMA]: Cargando sector conocido...");
        } else {
            // TERRENO VIRGEN: La semilla matemática crea el mundo
            this.escenaActual = GeneradorProcedural.generarEscena(x, y, z, calendario);
            System.out.println("[SISTEMA]: Generando terreno nuevo...");
        }

        // 4. Colocamos al jugador en la escena y la ejecutamos
        this.escenaActual.agregarEnte(jugador);
		return escenaActual;
       
    }
    private void actualizarCoordenadas(String dir) {
        switch (dir.toLowerCase()) {
            case "norte" -> y++;
            case "sur" -> y--;
            case "este" -> x++;
            case "oeste" -> x--;
            case "arriba" -> z++;
            case "abajo" -> z--;
        }
    }
}