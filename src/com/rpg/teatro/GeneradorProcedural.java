package com.rpg.teatro;

import com.rpg.ente.*;
import com.rpg.ente.bestiario.*;
import com.rpg.ente.bestiario.fauna.Humano;
import com.rpg.ente.bestiario.fauna.Orco;
import com.rpg.ente.bestiario.fauna.Slime;

import herramientas.clima.*;
import herramientas.tiempo.CalendarioLunar;
import java.util.Random;

public class GeneradorProcedural {
    private static long SEMILLA_MUNDO = 123456789L;

    public static Escena generarEscena(int x, int y, int z, CalendarioLunar calActual) {
        long semillaLocal = SEMILLA_MUNDO ^ ((long)x << 32) ^ y ^ ((long)z << 16);
        Random azarLocal = new Random(semillaLocal);
        
        // --- PASO 1: Determinar el Bioma primero ---
        Bioma biomaActual;
        if (z < -20) {
            biomaActual = Bioma.CUEVA_PROFUNDA;
        } else if (z > 20) {
            biomaActual = Bioma.VOLCAN;
        } else {
            float distancia = (float) Math.sqrt(x*x + y*y);
            if (distancia > 100) biomaActual = Bioma.DESIERTO;
            else if (distancia > 50) biomaActual = Bioma.TUNDRA;
            else if (azarLocal.nextFloat() < 0.3f) biomaActual = Bioma.SELVA;
            else biomaActual = Bioma.BOSQUE;
        }

        // --- PASO 2: Crear Clima y Escena ---
        Clima climaLocal = new Clima(biomaActual.obtenerNombre(), biomaActual.obtenerTemperaturaBase(), biomaActual.obtenerHumedadBase());
        String desc = String.format("Estás en el %s. %s", biomaActual.obtenerNombre(), interpretarEntorno(biomaActual, azarLocal));
        
        Escena nueva = new Escena("Sector [" + x + "," + y + "," + z + "]", desc, calActual, climaLocal, biomaActual);

        // --- PASO 3: Población (Ahora que 'nueva' existe y 'biomaActual' no es null) ---
        if (azarLocal.nextFloat() < 0.30f) { 
            Creador c = Creador.obtenerInstancia(); // Obtenemos la fábrica
            
            switch (biomaActual) {
            case BOSQUE -> {
                nueva.agregarEnte(c.instanciarDesdePlantilla(Humano.Adan));
                nueva.agregarEnte(c.instanciarDesdePlantilla(Slime.SLIME_FRESA));
            }
                case CUEVA_PROFUNDA -> {
                    nueva.agregarEnte(c.instanciarDesdePlantilla(Orco.Og));
                    nueva.agregarEnte(c.instanciarDesdePlantilla(Slime.MICRO_BASURERO));
                }
                case SELVA -> {
                    nueva.agregarEnte(c.instanciarDesdePlantilla(Slime.SLIME_LIMON));
                }
            }
        }

        return nueva;
    }

    private static String interpretarEntorno(Bioma b, Random r) {
        if (b.obtenerTemperaturaBase() > 40) return "El aire quema tus pulmones.";
        if (b.obtenerHumedadBase() > 80) return "La humedad es asfixiante.";
        if (b.obtenerTemperaturaBase() < 0) return "El frío cala hasta los huesos.";
        return "El ambiente es tranquilo.";
    }
}