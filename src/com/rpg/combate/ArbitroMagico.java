package com.rpg.combate;

import com.rpg.ente.Ente;
import com.rpg.ente.Atributo;
import herramientas.clima.Clima;
import herramientas.texto.Narrador;

public final class ArbitroMagico {
    
    public static void lanzarHechizo(Ente lanzador, Ente objetivo, MagiaBase hechizo, Clima climaActual) {
        Narrador n = Narrador.obtenerInstancia();
        
        // 1. Verificación de Maná (Atributo INTELIGENCIA o ESPIRITU)
        int manaActual = lanzador.obtenerValorAtributo(Atributo.INTELIGENCIA); // Suponiendo que INT es el tope de maná
        if (manaActual < hechizo.costeMana()) {
            lanzador.hablar("Mi mente está agotada... no puedo canalizar " + hechizo.nombre());
            return;
        }

        // 2. Cálculo de Potencia con Bonos de Clima
        float multiplicadorClima = 1.0f;
        
        // Ejemplo: Fuego en clima tropical (caliente) se potencia
        if (hechizo.nombre().contains("Fuego") && climaActual.temperaturaAmbiente() > 30) {
            multiplicadorClima = 1.5f;
            n.narrar("¡El calor ambiental intensifica las llamas!", 10);
        }
        // Ejemplo: Agua/Rayo en clima de Monzón
        if (hechizo.nombre().contains("Rayo") && climaActual.nombre().equalsIgnoreCase("Monzón")) {
            multiplicadorClima = 2.0f;
            n.narrar("¡La tormenta actúa como un conductor perfecto!", 10);
        }

        int dañoFinal = (int) (lanzador.obtenerValorAtributo(hechizo.escala()) * hechizo.potencia() * multiplicadorClima);
        
        // 3. Consumo de recurso
        lanzador.establecerValorAtributo(Atributo.INTELIGENCIA, manaActual - hechizo.costeMana());
        
        n.narrar(lanzador.obtenerNombre() + " convoca " + hechizo.nombre() + " sobre " + objetivo.obtenerNombre(), 30);
        objetivo.recibirImpacto(dañoFinal);
    }
}