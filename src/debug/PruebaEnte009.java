package debug;

import com.rpg.ente.*;
import com.rpg.ente.bestiario.fauna.*;
import com.rpg.ente.bestiario.flora.Musgo;
import com.rpg.ente.bestiario.elemento.Hidrogeno;
import com.rpg.combate.ArbitroCombate;
import com.rpg.combate.AtaqueBase;

import herramientas.texto.Narrador;

public class PruebaEnte009 {
    public static void main(String[] args) {
        Narrador n = Narrador.obtenerInstancia();
        Creador creador = Creador.obtenerInstancia();

        // 1. Instanciación usando el contrato PlantillaEnte
        Ente mora = creador.instanciarDesdePlantilla(Slime.SLIME_MORA);
        Ente og = creador.instanciarDesdePlantilla(Orco.Og);
        Ente ambiente = creador.instanciarDesdePlantilla(Musgo.musgo);

        n.narrar("--- INICIO DE PRUEBA ENTE 009: DUELO DE ATRIBUTOS ---", 10);
        n.narrar(mora.obtenerNombre() + " (Vida: " + mora.obtenerValorAtributo(Atributo.VIDA_MAX) + ") vs " + 
                 og.obtenerNombre() + " (Fuerza: " + og.obtenerValorAtributo(Atributo.FUERZA) + ")", 20);

        // 2. Simulación de Combate Táctico
        // El Orco intenta un ataque pesado al NÚCLEO del Slime
        n.narrar("\n" + og.obtenerNombre() + " lanza un garrotazo al Núcleo...", 30);
        ArbitroCombate.procesarAtaqueDirigido(og, mora, AtaqueBase.EMBESTIDA, ParteDelCuerpo.NUCLEO);

        // 3. Chequeo de Resistencia y Constitución
        if (mora.obtenerVidaActual() < mora.obtenerValorAtributo(Atributo.VIDA_MAX)) {
            n.narrar(mora.obtenerNombre() + " resiste gracias a su Constitucion de " + 
                     mora.obtenerValorAtributo(Atributo.CONSTITUCION), 20);
        }

        // 4. Intervención Elemental (Micro-mundo)
        n.narrar("\nEl " + ambiente.obtenerNombre() + " en el suelo absorbe restos de " + 
                 Hidrogeno.hidrogeno.nombre(), 40);
        
        n.narrar("--- FIN DE PRUEBA 009 ---", 10);
    }
}