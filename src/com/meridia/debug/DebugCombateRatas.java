package com.meridia.debug;

import com.meridia.entities.*;
import com.meridia.world.*; // Para el LootManager e Inventario
import java.util.*;

public class DebugCombateRatas {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // Necesitamos instanciar la mochila para que la línea del botín funcione
        Inventario mochila = new Inventario();
        
        // Setup de Héroes
        List<Entidad> heroes = new ArrayList<>(List.of(
            PersonajeFactory.crearSoul(),
            PersonajeFactory.crearLaxir(),
            PersonajeFactory.crearAlejandra(),
            PersonajeFactory.crearNalanga()
        ));

        // Evento único: 6 Ratas
        List<Entidad> ratas = new ArrayList<>();
        System.out.println("\n[DEBUG EVENTO: RESCATE DEL SER GRIS]");
        for (int i = 0; i < 6; i++) {
            ratas.add(EnemigoFactory.crearRataGigante()); 
        }

        // Bucle de combate
        while (!heroes.isEmpty() && !ratas.isEmpty()) {
            // Turno de Héroes
            for (int hIdx = 0; hIdx < heroes.size(); hIdx++) {
                if (ratas.isEmpty()) break;
                Entidad h = heroes.get(hIdx);
                
                h.reducirEspera(); 

                System.out.println("\n--- Turno de " + h.getNombre() + " ---");
                System.out.println("Selecciona rata a la cual atacar:");
                for (int i = 0; i < ratas.size(); i++) {
                    System.out.printf("%d. %s (HP: %.2f)%n", i, ratas.get(i).getNombre(), ratas.get(i).getVidaActual());
                }

                int targetIdx = sc.nextInt();
                System.out.println("1. Especial  2. Básico");
                int op = sc.nextInt();

                double dano = h.calcularDano(op);
                Entidad rataObjetivo = ratas.get(targetIdx);
                rataObjetivo.recibirDano(dano);

                System.out.printf("¡%s inflige %.2f de daño!%n", h.getNombre(), dano);

                // Lógica de muerte y DROP
                if (rataObjetivo.getVidaActual() <= 0) {
                    System.out.println("¡Rata eliminada!");
                    
                    // Calculamos el drop antes de borrarla de la lista
                    // Usamos un valor fijo de 'golpes' por ahora (ej. 2) para probar
                    String botin = LootManager.calcularDrop(rataObjetivo, dano, 2); 
                    mochila.agregarItem(botin);
                    
                    ratas.remove(targetIdx);
                }
            }
            
            // Turno de las ratas
            for (Entidad r : ratas) {
                if (heroes.isEmpty()) break;
                Entidad victim = heroes.get(0); 
                double damage = 2.0; // Bajé el daño para que no mueran tan rápido en el debug
                victim.recibirDano(damage);
                System.out.println("Una rata muerde a " + victim.getNombre() + " por " + damage + " de daño.");
                
                if (victim.getVidaActual() <= 0) {
                    System.out.println("¡" + victim.getNombre() + " ha caído!");
                    heroes.remove(0);
                }
            }
        }
        
        if (ratas.isEmpty()) {
            System.out.println("\n¡HAS RESCATADO AL SER DIMINUTO GRIS!");
            mochila.mostrarInventario();
        }
    }
}