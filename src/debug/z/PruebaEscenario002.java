package debug.z;

import com.rpg.ente.*;
import com.rpg.ente.bestiario.fauna.*;
import com.rpg.ente.bestiario.flora.Musgo;
import com.rpg.ente.bestiario.elemento.Hidrogeno;
import com.rpg.combate.ArbitroCombate;
import com.rpg.combate.AtaqueBase;
import com.rpg.teatro.Escena;
import herramientas.texto.Narrador;

public class PruebaEscenario002 {
    public static void main(String[] args) {
        Narrador n = Narrador.obtenerInstancia();
        Creador c = Creador.obtenerInstancia();
        
        // 1. EL ELENCO (Todos en Escala MEDIO inicial)
        Ente adan = c.instanciarDesdePlantilla(Humano.Adan);
        Ente og = c.instanciarDesdePlantilla(Orco.Og);
        Ente slime1 = c.instanciarDesdePlantilla(Slime.SLIME_FRESA);
        Ente slime2 = c.instanciarDesdePlantilla(Slime.SLIME_LIMON);
        Ente slime3 = c.instanciarDesdePlantilla(Slime.SLIME_MORA);
        
        // El Micro-entorno
        Ente musgoLocal = c.instanciarDesdePlantilla(Musgo.musgo);
        
        n.narrar("=== BATTLE ROYALE BIOLÓGICO: SECTOR [0,0,0] ===", 10);

        // 2. PRIMERA FASE: EL CHOQUE DE TITANES
        n.narrar("\n[Fase 1]: Adán y Og se enfrentan a la plaga de Slimes.", 20);
        
        // Adán vs Slime Fresa
        ArbitroCombate.procesarAtaqueDirigido(adan, slime1, AtaqueBase.ESTOCADA, ParteDelCuerpo.TORSO);
        
        // Og vs Slime Mora
        ArbitroCombate.procesarAtaqueDirigido(og, slime3, AtaqueBase.EMBESTIDA, ParteDelCuerpo.NUCLEO);

        // 3. FASE DE FRAGMENTACIÓN (El Slime Mora muere y se multiplica)
        if (slime3.obtenerVidaActual() <= 0) {
            n.narrar("\n[EVENTO]: El Slime Mora explota en mil pedazos microscópicos...", 30);
            for(int i = 0; i < 5; i++) {
                Ente micro = c.instanciarDesdePlantilla(Slime.MICRO_BASURERO);
                micro.cambiarTamaño(Tamaño.MICROSCOPICO);
                
                // Los Micro-Slimes interactúan con el Musgo
                n.narrar("Un Micro-Slime devora una fibra de Musgo.", 10);
                micro.interactuar(musgoLocal);
                
                // Liberación de Hidrógeno
                Ente gas = c.instanciarDesdePlantilla(Hidrogeno.hidrogeno);
                n.narrar("Se libera una burbuja de " + gas.obtenerNombre() + " al aire.", 5);
            }
        }

        // 4. FASE FINAL: EL DUELO DE VENCEDORES
        n.narrar("\n[Fase Final]: Los sobrevivientes se miran fijamente.", 20);
        if (adan.obtenerVidaActual() > 0 && og.obtenerVidaActual() > 0) {
            n.narrar("Adán encara a Og en un duelo a muerte.", 30);
            ArbitroCombate.procesarAtaqueDirigido(og, adan, AtaqueBase.EMBESTIDA, ParteDelCuerpo.CABEZA);
        }

        n.narrar("\n=== FIN DE LA SIMULACIÓN 002 ===", 10);
    }
    
    
}