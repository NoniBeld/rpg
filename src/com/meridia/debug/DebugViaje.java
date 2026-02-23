package com.meridia.debug;

import com.meridia.entities.Entidad;
import com.meridia.entities.PersonajeFactory; // Necesario para crear a los héroes
import com.meridia.world.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class DebugViaje {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Ubicacion claro = MundoBuilder.crearMundo();
        Ubicacion actual = claro;

        // Necesitamos a los héroes para que el evento funcione
        List<Entidad> heroes = new ArrayList<>(List.of(
            PersonajeFactory.crearSoul(),
            PersonajeFactory.crearLaxir(),
            PersonajeFactory.crearAlejandra(),
            PersonajeFactory.crearNalanga()
        ));

        System.out.println("--- MODO DEBUG: NAVEGACIÓN Y EVENTOS ---");

        while (true) {
            actual.registrarVisita();
            System.out.println("\n📍 Estás en: " + actual.getNombre());
            System.out.println("Salidas: " + actual.getSalidas());
            System.out.print("¿A dónde vas? (o 'stop'): ");
            
            String entradaUsuario = sc.nextLine().toLowerCase();
            if (entradaUsuario.equals("stop")) break;

            // LÓGICA ESPECIAL: ENTRAR A LA CUEVA
            if (entradaUsuario.equals("entrar") && actual.getNombre().equals("Pie del Gran Pico")) {
                
                // 1. Generar enemigos usando el Gestor
                List<Entidad> enemigos = GestorEventos.generarEncuentroCueva();
                
                // 2. Aquí llamarías a tu método de combate (debes tenerlo accesible)
                // Por ahora simularemos la victoria para el debug
                System.out.println("[DEBUG] Combate contra " + enemigos.size() + " enemigos iniciado...");
                
                // 3. Evento del Muro
                System.out.println("\n--- EL SELLO MISTERIOSO ---");
                System.out.println("El Ser Gris corre emocionado hacia la entrada, pero...");
                System.out.println("¡ZAP! Una chispa azul lo lanza hacia atrás.");
                System.out.println("Ser Gris: '¡Imposible! Un sello de energía neutral... ¡pero no es mío!'");
                
                System.out.println("\n[INFO]: El Ser Gris no puede entrar. Los héroes deberán seguir solos.");
                
                // Buscamos la cueva en las salidas para movernos
                Ubicacion cueva = actual.getDestino("entrar");
                if (cueva != null) {
                    actual = cueva;
                    continue; // Saltamos al inicio del bucle en la nueva ubicación
                }
            }

            // NAVEGACIÓN NORMAL
            Ubicacion sig = actual.getDestino(entradaUsuario);
            if (sig != null) {
                actual = sig;
            } else {
                System.out.println("❌ No hay salida en esa dirección.");
            }
        }
        sc.close();
    }
}