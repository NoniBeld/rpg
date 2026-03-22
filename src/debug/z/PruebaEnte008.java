package debug.z;

import com.rpg.ente.*;
import com.rpg.combate.*;
import herramientas.texto.Narrador;

public class PruebaEnte008 {
    public static void main(String[] args) {
        Narrador n = Narrador.obtenerInstancia();
        n.narrar("--- INICIANDO PRUEBA DE SISTEMAS 007 ---", 10);

        // 1. CREACIÓN DE ENTES
        Ente depredador = Creador.obtenerInstancia().crearNuevoEnte("Cazador Alfa", Funcion.SUJETO);
        Ente presa = Creador.obtenerInstancia().crearNuevoEnte("Slime de Fresa", Funcion.SUJETO);
        
     // --- Fase 2: Combate Quirúrgico ---
        n.narrar("\n[Fase 2: Combate]", 20);

        // Forzamos Atributos para el Test:
        depredador.establecerValorAtributo(Atributo.AGILIDAD, 999); // Para no fallar al Núcleo
        depredador.establecerValorAtributo(Atributo.FUERZA, 100);    // Para asegurar la muerte

        n.narrar("Ejecutando One-Shot Kill al NÚCLEO...", 30);
        ArbitroCombate.procesarAtaqueDirigido(depredador, presa, AtaqueBase.EMBESTIDA, ParteDelCuerpo.NUCLEO);

        // --- Fase 3: Procesamiento ---
        n.narrar("\n[Fase 3: Procesamiento de Materia]", 20);

        if (presa.obtenerVidaActual() <= 0) {
            // IMPORTANTE: Asegúrate de que el cadáver esté en el suelo o inventario
            depredador.ejecutarDeshuesar(presa);
        } else {
            n.narrar("La presa sobrevivió... no hay nada que deshuesar.", 10);
        }

        // --- Fase 4: Consumo ---
        n.narrar("\n[Fase 4: Consumo]", 20);
        // Buscamos específicamente el trofeo generado
        Ente trofeo = depredador.buscarEnInventario("Resto de TORSO de Slime de Fresa");

        if (trofeo != null) {
            depredador.interactuar(trofeo); // Engullir
            trofeo.transformar(Potencia.DIGERIDO); // Ver el cambio de nombre
        } else {
            n.narrar("No se encontró el trofeo para digerir.", 10);
        }

        // 5. CICLO ALIMENTICIO (Digestión)
        n.narrar("\n[Fase 4: Consumo y Transformación]", 20);
        Ente trofeo1 = depredador.buscarEnInventario("Resto de CABEZA de Slime de Fresa");
        
        if (trofeo1 != null) {
            depredador.interactuar(trofeo1); // Engullir
            
            // Forzamos el paso del tiempo para ver la transformación (Simulado)
            n.narrar("Pasando tiempo de digestión...", 40);
            trofeo1.transformar(Potencia.DIGERIDO);
        }

        n.narrar("\n--- PRUEBA 007 FINALIZADA CON ÉXITO ---", 10);
    }
}