package com.rpg.logica;

import com.rpg.ente.Ente;
import com.rpg.ente.Atributo;
import herramientas.texto.Narrador;

public final class Coliseo {
    
    public void duelo(Ente atacante, Ente defensor) {
        Narrador n = Narrador.obtenerInstancia();
        n.narrar("¡Inicia el duelo entre " + atacante.obtenerNombre() + " y " + defensor.obtenerNombre() + "!", 40);
        
        // Lógica simple: Fuerza vs Agilidad (Evasión)
        int poderAtacante = atacante.obtenerValorAtributo(Atributo.FUERZA);
        int defensaDefensor = defensor.obtenerValorAtributo(Atributo.AGILIDAD);
        
        int daño = Math.max(0, poderAtacante - (defensaDefensor / 2));
        
        n.narrar(atacante.obtenerNombre() + " golpea causando " + daño + " de daño.", 30);
        // Aquí luego conectaremos con integridadFisica
    }
}