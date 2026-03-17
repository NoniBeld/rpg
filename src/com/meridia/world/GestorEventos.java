package com.meridia.world;

import com.meridia.entities.*;
import java.util.*;

public class GestorEventos {
    private static Random rand = new Random();

    // AQUÍ VA TU CÓDIGO 1: Generador de Encuentros
    public static List<Entidad> generarEncuentroCueva() {
        List<Entidad> enemigos = new ArrayList<>();
        int suerte = rand.nextInt(3); 

        switch (suerte) {
            case 0 -> { // Murciélagos
                int num = rand.nextInt(7) + 2; 
                System.out.println("\n[!] Una bandada de " + num + " murciélagos desciende del techo.");
                for(int i=0; i<num; i++) enemigos.add(EnemigoFactory.crearMurcielago());
            }
            case 1 -> { // Lobo Gris
                System.out.println("\n[!] ¡Un Lobo Gris de ojos gélidos surge de la ventisca!");
                enemigos.add(EnemigoFactory.crearLoboGris());
            }
            case 2 -> { // Ratas
                int num = rand.nextInt(8) + 5; 
                System.out.println("\n[!] ¡Una horda de " + num + " ratas gigantes bloquea el paso!");
                for(int i=0; i<num; i++) enemigos.add(EnemigoFactory.crearRataGigante());
            }
        }
        return enemigos;
    }
    
    public static void eventoMuroFuerza(List<Entidad> heroes, Scanner sc) {
        System.out.println("\n--- EL SELLO DE LA CUEVA ---");
        System.out.println("El Ser Gris choca contra el muro. Solo los huérfanos pueden pasar.");
        System.out.println("Deben tocar el sello en el orden elemental correcto.");

        String[] ordenCorrecto = {"Alejandra", "Nalanga", "Soul", "Laxir"};
        int pasoActual = 0;

        while (pasoActual < ordenCorrecto.length) {
            System.out.println("\nFase " + (pasoActual + 1) + ": ¿Quién se acerca al muro?");
            for (int i = 0; i < heroes.size(); i++) {
                System.out.println(i + ". " + heroes.get(i).getNombre());
            }
            
            int seleccion = sc.nextInt();
            String nombreSeleccionado = heroes.get(seleccion).getNombre();

            if (nombreSeleccionado.equals(ordenCorrecto[pasoActual])) {
                System.out.println("¡El muro vibra en armonía con " + nombreSeleccionado + "!");
                pasoActual++;
            } else {
                System.out.println("¡ZAP! El muro repele a " + nombreSeleccionado + ". El sello se reinicia.");
                pasoActual = 0; // Reinicio del puzzle
            }
        }
        System.out.println("\nEl sello se desvanece. La Cueva de los Lamentos está abierta.");
    }
}