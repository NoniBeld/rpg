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
}