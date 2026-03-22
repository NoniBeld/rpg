package debug;

import com.rpg.ente.*;
import com.rpg.ente.mecanica.*;
import com.rpg.combate.*;
import com.rpg.teatro.Escena;
import herramientas.clima.*;
import herramientas.tiempo.CalendarioLunar;

public class PruebaEnte10 {
    public static void main(String[] args) {
        // 1. PREPARACIÓN DEL ESCENARIO
        CalendarioLunar cal = new CalendarioLunar();
        Clima clima = new Clima("Selva", 30.0f, 80.0f); // Calor para acelerar la pudrición
        Escena arena = new Escena("Valle de Pruebas", "Un ecosistema cruel", cal, clima, Bioma.SELVA);

        // 2. INSTANCIACIÓN (Usando tus plantillas)
        Ente og = Creador.obtenerInstancia().crearNuevoEnte("Og el Orco", Funcion.SUJETO);
        Creador.obtenerInstancia().configurarCuerpo(og, "HUMANOIDE");
        og.establecerAlineamiento(EjeEtico.CAOTICO, EjeMoral.MALO);
        og.establecerValorAtributo(Atributo.FUERZA, 30);

        Ente fresa = Creador.obtenerInstancia().crearNuevoEnte("Slime Fresa", Funcion.ALIMENTO);
        Creador.obtenerInstancia().configurarCuerpo(fresa, "AMORFO");
        
        Ente basurero = Creador.obtenerInstancia().crearNuevoEnte("Micro-Slime", Funcion.SUJETO);
        Creador.obtenerInstancia().configurarCuerpo(basurero, "AMORFO");

        arena.agregarEnte(og);
        arena.agregarEnte(fresa);
        arena.agregarEnte(basurero);

        // 3. DESARROLLO DE LA ACCIÓN
        System.out.println("=== FASE 1: LA CAZA ===");
        // Og ataca directamente al NÚCLEO del Slime
        ArbitroCombate.procesarAtaqueDirigido(og, fresa, AtaqueBase.EMBESTIDA, ParteDelCuerpo.NUCLEO);

        System.out.println("\n=== FASE 2: PROCESAMIENTO ===");
        if (fresa.obtenerEstadoVital() == EstadoVital.MUERTO) {
            // Og deshuesa al Slime para obtener comida
            og.ejecutarDeshuesar(fresa);
            arena.removerEnte(fresa); // Quitamos el cuerpo original
        }

        System.out.println("\n=== FASE 3: EL BASURERO ENTRA EN ACCIÓN ===");
        // El Micro-Slime busca el "Resto de NUCLEO" que dejó Og
        Ente comidaEnSuelo = arena.buscarObjetivoCercano(basurero);
        if (comidaEnSuelo != null) {
            basurero.avanzarHacia(comidaEnSuelo.obtenerPosicionX(), 0, comidaEnSuelo.obtenerPosicionZ());
            basurero.interactuar(comidaEnSuelo);
        }

        System.out.println("\n=== FASE 4: ENTROPÍA Y GUARDADO ===");
        // Simulamos el paso del tiempo para ver si lo que queda se pudre
        arena.simularCiclo();
        
        // Guardamos el estado final del mundo
        herramientas.guardado.GestorMapa.dormirEscena(arena);
    }
}