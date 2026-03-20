package com.rpg.combate;

import com.rpg.ente.Ente;
import com.rpg.ente.AtaqueBase;
import com.rpg.ente.Atributo;
import herramientas.texto.Narrador;
import java.util.Random;

public final class ArbitroCombate {
    private static final Random azar = new Random();

    public static void procesarDuelo(Ente atacante, Ente defensor, AtaqueBase ataque) {
        Narrador n = Narrador.obtenerInstancia();
        
        // Fórmula: (Atributo * Multiplicador) + un pequeño factor de suerte
        int poderAtacante = atacante.obtenerValorAtributo(ataque.escala());
        int dañoBase = (int) (poderAtacante * ataque.multiplicador());
        int suerte = azar.nextInt(5); // 0 a 4 puntos extra
        
        int dañoFinal = dañoBase + suerte;

        n.narrar(String.format("¡%s lanza un %s contra %s!", 
            atacante.obtenerNombre(), ataque.nombre(), defensor.obtenerNombre()), 30);
        
        defensor.recibirImpacto(dañoFinal);
        
        n.narrar(String.format("%s recibe %d de daño. Vida restante: %d", 
            defensor.obtenerNombre(), dañoFinal, defensor.obtenerVidaActual()), 40);
            
        // Si el defensor cae, el atacante puede activar el ciclo alimenticio
        if (defensor.obtenerVidaActual() <= 0) {
            n.narrar("¡" + defensor.obtenerNombre() + " ha sido derrotado!", 20);
        }
    }
}