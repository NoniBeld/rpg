package com.rpg.teatro;

import com.rpg.ente.Creador;
import com.rpg.ente.Ente;
import com.rpg.ente.bestiario.PlantillaOrganica;
import herramientas.clima.Bioma;
import herramientas.clima.Clima;
import herramientas.tiempo.CalendarioLunar;
import java.util.Random;

public class GeneradorProcedural {
    private static long SEMILLA_MUNDO = 123456789L; 

    public static Escena generarEscena(int x, int y, int z, CalendarioLunar calActual) {
        // 1. Inicializamos el azar local con la semilla combinada con las coordenadas
        // Esto hace que cada punto del universo sea único pero constante
        long semillaLocal = SEMILLA_MUNDO ^ ((long)x << 32) ^ y ^ ((long)z << 16);
        Random azarLocal = new Random(semillaLocal);
        
        // 2. Lógica de selección de Bioma según coordenadas
        Bioma biomaActual;
        
        if (z < -20) {
            biomaActual = Bioma.CUEVA_PROFUNDA; // El inframundo gélido
        } else if (z > 20) {
            biomaActual = Bioma.VOLCAN; // Las tierras altas de ceniza
        } else {
            // En la superficie (z entre -20 y 20), variamos según la distancia al origen (X, Y)
            float distanciaAlCentro = (float) Math.sqrt(x*x + y*y);
            
            if (distanciaAlCentro > 100) {
                biomaActual = Bioma.DESIERTO; // Lejos del centro todo se seca
            } else if (distanciaAlCentro > 50) {
                biomaActual = Bioma.TUNDRA;   // El cinturón helado
            } else if (azarLocal.nextFloat() < 0.3f) {
                biomaActual = Bioma.SELVA;    // Manchas de selva tropical
            } else {
                biomaActual = Bioma.BOSQUE;   // El bioma base
            }
        }

        // 3. Crear el Clima basado en el Bioma
        Clima climaLocal = new Clima(
            biomaActual.obtenerNombre(), 
            biomaActual.obtenerTemperaturaBase(), 
            biomaActual.obtenerHumedadBase()
        );

        // 4. Descripción dinámica mejorada
        String desc = String.format("Estás en el %s. %s", 
            biomaActual.obtenerNombre(), 
            interpretarEntorno(biomaActual, azarLocal));

        // 5. Instanciar Escena
        Escena nueva = new Escena(
            "Sector [" + x + "," + y + "," + z + "]", 
            desc, calActual, climaLocal, biomaActual
        );

        // 6. Población Procedural (Aparecen según la semilla local)
        if (azarLocal.nextFloat() < 0.25f) {
            // Aquí el sistema decidiría qué bicho poner según el bioma
            // Ejemplo: if(biomaActual == Bioma.VOLCAN) Creador... crear Slime de Fuego
        }

        return nueva;
    }

    private static String interpretarEntorno(Bioma b, Random r) {
        if (b.obtenerTemperaturaBase() > 40) return "El aire quema tus pulmones y el suelo vibra.";
        if (b.obtenerHumedadBase() > 80) return "La humedad es tan densa que el agua se condensa en tu piel.";
        if (b.obtenerTemperaturaBase() < 0) return "Tus pasos crujen sobre una capa de escarcha eterna.";
        return "El ambiente es tranquilo, pero te sientes observado.";
    }
}