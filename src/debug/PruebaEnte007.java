package debug;

import com.rpg.ente.*;
import com.rpg.combate.*;
import herramientas.texto.Narrador;

public class PruebaEnte007 {
    public static void main(String[] args) {
        Narrador n = Narrador.obtenerInstancia();
        n.narrar("--- INICIANDO PRUEBA DE SISTEMAS 007 ---", 10);

        // 1. CREACIÓN DE ENTES
        Ente depredador = Creador.obtenerInstancia().crearNuevoEnte("Cazador Alfa", Funcion.SUJETO);
        Ente presa = Creador.obtenerInstancia().crearNuevoEnte("Slime de Fresa", Funcion.SUJETO);
        
        // 2. TEST DE ATRIBUTOS Y ESTADOS
        n.narrar("\n[Fase 1: Estado Inicial]", 20);
        System.out.println(depredador.toString());
        
        // 3. COMBATE TÁCTICO (Daño Localizado)
        n.narrar("\n[Fase 2: Combate Quirúrgico]", 20);
        // Intentamos un ataque al NÚCLEO (Difícil pero letal)
        n.narrar("Intentando un golpe de precisión al NÚCLEO...", 30);
        ArbitroCombate.procesarAtaqueDirigido(depredador, presa, AtaqueBase.EMBESTIDA, ParteDelCuerpo.NUCLEO);

        // Si sobrevive, atacamos al TORSO para asegurar
        if (presa.obtenerVidaActual() > 0) {
            n.narrar("El núcleo resistió. Rematando al TORSO...", 30);
            ArbitroCombate.procesarAtaqueDirigido(depredador, presa, AtaqueBase.CABEZAZO, ParteDelCuerpo.TORSO);
        }

        // 4. TEST DE INTEGRIDAD Y DESPIECE (Loot)
        n.narrar("\n[Fase 3: Procesamiento de Materia]", 20);
        if (presa.obtenerVidaActual() <= 0) {
            // El depredador procede a "Deshuesar" (extraer partes intactas)
            depredador.ejecutarDeshuesar(presa);
            
            n.narrar("Inventario del Cazador tras el despiece:", 20);
            depredador.mostrarContenido(0);
        }

        // 5. CICLO ALIMENTICIO (Digestión)
        n.narrar("\n[Fase 4: Consumo y Transformación]", 20);
        Ente trofeo = depredador.buscarEnInventario("Resto de CABEZA de Slime de Fresa");
        if (trofeo != null) {
            depredador.interactuar(trofeo); // Engullir
            
            // Forzamos el paso del tiempo para ver la transformación (Simulado)
            n.narrar("Pasando tiempo de digestión...", 40);
            trofeo.transformar(Potencia.DIGERIDO);
        }

        n.narrar("\n--- PRUEBA 007 FINALIZADA CON ÉXITO ---", 10);
    }
}