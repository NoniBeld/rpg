package debug.z;

import com.rpg.combate.ArbitroCombate;
import com.rpg.combate.AtaqueBase;
import com.rpg.ente.Atributo;
import com.rpg.ente.Creador;
import com.rpg.ente.Ente;
import com.rpg.ente.Funcion;
import com.rpg.ente.ParteDelCuerpo;

import herramientas.texto.Narrador;

public class PruebaMapa000 {
/*	public static void main(String[] args) {
        Narrador n = Narrador.obtenerInstancia();
        
        // 1. EL DESPERTAR DEL SLIME DE MORA
        Ente jugador = Creador.obtenerInstancia().crearNuevoEnte("Slime de Mora", Funcion.SUJETO);
        jugador.establecerValorAtributo(Atributo.CONSTITUCION, 20); // Bono de vida
        jugador.establecerValorAtributo(Atributo.FUERZA, 15);
        
        n.narrar("Eres un Slime de Mora. Tu vida total orgánica es: " + jugador.calcularVidaMaximaOrganica(), 30);

        // 2. ENCUENTRO Y FUERZA (Combate en el Sector [0,0,0])
        Ente enemigo = Creador.obtenerInstancia().crearNuevoEnte("Slime de Fresa", Funcion.SUJETO);
        n.narrar("\n[COMBATE]: Un Slime de Fresa bloquea el camino.", 20);
        
        // Atacamos al TORSO del enemigo
        ArbitroCombate.procesarAtaqueDirigido(jugador, enemigo, AtaqueBase.EMBESTIDA, ParteDelCuerpo.TORSO);
        
        // 3. DAÑO RECIBIDO Y CURACIÓN (El Slime de Limón)
        // Simulamos que el enemigo nos golpeó la CABEZA
        jugador.recibirImpactoLocalizado(25, ParteDelCuerpo.CABEZA);
        n.narrar("¡Tu CABEZA está dañada! Salud actual: " + jugador.obtenerSaludDeParte(ParteDelCuerpo.CABEZA), 30);

        // Aparece el Slime de Limón (Curación)
        Ente cura = Creador.obtenerInstancia().crearNuevoEnte("Slime de Limón", Funcion.ALIMENTO);
        n.narrar("\nEncuentras un Slime de Limón brillante...", 20);
        
        // El Slime de Mora interactúa (se lo come)
        jugador.interactuar(cura); 
        
        // EFECTO ESPECIAL: El Limón restaura la integridad física
        for (ParteDelCuerpo p : ParteDelCuerpo.values()) {
            int saludFaltante = p.saludBase - jugador.obtenerSaludDeParte(p);
            if (saludFaltante > 0) {
                jugador.recibirImpactoLocalizado(-saludFaltante, p); // Curación negativa = Sanar
            }
        }
        
        n.narrar("El ácido del Slime de Limón ha regenerado tus tejidos.", 30);
    }*/
}