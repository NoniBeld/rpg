package debug.z;

import com.combate.ArbitroCombate;
import com.combate.AtaqueBase;
import com.ente.*;
import com.rpg.ente.bestiario.fauna.Humano;
import com.rpg.ente.bestiario.fauna.Orco;

import herramientas.texto.Narrador;

public class PruebaEscenario006 {/*
    public static void main(String[] args) {
        Narrador n = Narrador.obtenerInstancia();
        Creador c = Creador.obtenerInstancia();

        n.narrar("=== [SISTEMA]: INICIANDO PRUEBA 006 - EL FACTOR D20 ===", 10);

        // 1. CARGA DE ENTES (ADN a Cuerpo Físico)
        // Instanciamos a Og y Adán usando sus plantillas de Record
        Ente og = c.instanciarDesdePlantilla(Orco.Og);
        Ente adan = c.instanciarDesdePlantilla(Humano.Adan);

        // Aseguramos que Adán tenga su chasis Humanoide cargado
        adan.inicializarAnatomiaPorChasis("HUMANOIDE");
        
        n.narrar(String.format("Combatientes listos: %s vs %s", og.obtenerNombre(), adan.obtenerNombre()), 20);
        n.narrar("--------------------------------------------------", 5);

        // 2. BUCLE DE INTERCAMBIO (Máximo 3 golpes para testear zonas)
        for (int i = 1; i <= 3; i++) {
            n.narrar("\n--- ASALTO " + i + " ---", 10);
            
            // Og ataca a Adán usando el nuevo sistema D20 de ArbitroCombate
            ArbitroCombate.procesarDuelo(og, adan, AtaqueBase.EMBESTIDA);

            // Reporte de salud post-golpe
            System.out.println(String.format("   [INFO]: Vida Total de %s: %d", 
                adan.obtenerNombre(), adan.obtenerVidaActual()));

            // 3. VERIFICACIÓN DE CAÍDA Y LOOT
            if (adan.obtenerVidaActual() <= 0) {
                n.narrar("\n" + adan.obtenerNombre() + " ha colapsado. Og se prepara para la cosecha...", 20);
                
                // Aplicamos el nuevo método de Deshuesar que valida partes sanas
                og.ejecutarDeshuesar(adan);
                
                n.narrar("Cosecha finalizada. Og guarda los restos en su inventario.", 10);
                break; // Terminamos la prueba si muere
            }
        }

        // 4. REPORTE FINAL DE ANATOMÍA (Para verificar el D20)
        System.out.println("\n===== REPORTE DE DAÑOS EN ADÁN =====");
        for (ParteDelCuerpo parte : ParteDelCuerpo.values()) {
            int salud = adan.obtenerSaludDeParte(parte);
            if (salud != 100) { // Solo mostramos lo que fue golpeado
                System.out.println(String.format("-> %s: %d%% de integridad.", parte, salud));
            }
        }
        System.out.println("====================================");
        
        n.narrar("\n=== PRUEBA 006 FINALIZADA ===", 10);
    }*/
}