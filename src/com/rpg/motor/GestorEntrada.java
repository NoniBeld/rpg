package com.rpg.motor;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import herramientas.texto.Narrador;

import com.rpg.ente.Atributo;
import com.rpg.ente.Ente;
import com.rpg.ente.Funcion;
import com.rpg.teatro.Escena;

/**
 * Escucha y procesa los comandos del jugador en la consola.
 */
public final class GestorEntrada {
    private final Scanner teclado;
    private boolean escuchando;
    
    private final Map<String, Runnable> comandos = new HashMap<>();
    
    public GestorEntrada() {
        this.teclado = new Scanner(System.in);
        this.escuchando = true;
    }
    public void iniciarBucleEscucha(Ente jugador, MotorJuego motor, Escena escenaActual) {
        Narrador.obtenerInstancia().narrar(">>> Sistema de entrada listo. Escribe un comando (o 'salir'):", 20);
        
        while (escuchando) {
            System.out.print("\n[Comando]> ");
            String entradaRaw = teclado.nextLine().toLowerCase().trim();

            if (entradaRaw.equals("salir")) {
                escuchando = false;
                motor.detener();
                break;
            }

            // CAMBIO CLAVE: Usamos el procesador con argumentos
            procesarEntrada(entradaRaw, jugador, escenaActual);
        }
    }
    private void configurarComandos(Ente jugador) {
        comandos.put("hablar", () -> jugador.hablar("¡Hola!"));
        comandos.put("stats",  () -> Narrador.obtenerInstancia().narrar(jugador.toString(), 10));
        comandos.put("comer",  () -> {	Narrador.obtenerInstancia().narrar("Buscando algo para comer... ", 20);
  									  	Narrador.obtenerInstancia().narrar("Objetivo localizado:", 40); });
        comandos.put("ayuda", () -> System.out.println("Comandos disponibles: hablar, comer, estadistica, ayuda, salir"));
    }

    private void procesarComandoPro(String entrada) {
        // Buscamos el comando en el mapa y lo ejecutamos. Si no existe, usamos un lambda por defecto.
        comandos.getOrDefault(entrada, () -> System.out.println("Comando desconocido"))
                .run();
    }
    
    private void procesarEntrada(String entradaRaw, Ente jugador) {
    	String[] partes = entradaRaw.split(" ", 2); // Divide en ["lanzar", "manzana"]
    	Verbo verbo = Verbo.desdeCadena(partes[0]);
    	String objetivo = (partes.length > 1) ? partes[1] : "";
    	
    	if (verbo == null) {
    		System.out.println("No reconozco esa acción");
    		return;
    	}
    	
    	ejecutarAccion(new Orden(verbo, objetivo), jugador, null);
    }
    
    private void procesarEntrada(String entradaRaw, Ente jugador, Escena escenaActual) {
        String[] partes = entradaRaw.split(" ", 2);
        Verbo verbo = Verbo.desdeCadena(partes[0]);
        String objetivo = (partes.length > 1) ? partes[1] : "";
        
        if (verbo == null) {
            System.out.println("No reconozco esa acción");
            return;
        }
        
        ejecutarAccion(new Orden(verbo, objetivo), jugador, escenaActual);
    }

    private void ejecutarAccion(Orden orden, Ente jugador, Escena escenaActual) {
        String nombreObj = orden.objetivo();

        // 1. CASO ESPECIAL: MIRAR (Puede no tener objetivo)
        if (orden.verbo() == Verbo.MIRAR && nombreObj.isEmpty()) {
            Narrador.obtenerInstancia().narrar("Miras tus pertenencias:", 20);
            jugador.mostrarContenido(0);
            return; 
        }

        // 2. BÚSQUEDA ÚNICA: Buscamos el ítem una sola vez en todo el alcance
        Ente item = jugador.buscarEnAlcance(nombreObj, escenaActual.obtenerPresentes());

        if (item == null) {
            System.out.println("No encuentras ningún '" + nombreObj + "' aquí.");
            return;
        }
     // 3. LÓGICA DE VERBOS (Aquí el ítem YA existe)
        switch (orden.verbo()) {
            case MIRAR -> {
                Narrador.obtenerInstancia().narrar("Observas el " + item.obtenerNombre() + ":", 20);
                item.mostrarContenido(1);
            }
            case COMER -> {
                if (item.obtenerFuncionActual() == Funcion.ALIMENTO) {
                    jugador.interactuar(item); // Implementará tu Sistema Digestivo pronto
                } else {
                    jugador.hablar("Intentar comer un " + item.obtenerNombre() + " no parece buena idea.");
                }
            }
            case LANZAR -> {
                // Lógica de proyectil
                item.cambiarFuncion(Funcion.ARMA);
                System.out.println("¡Lanzas el " + item.obtenerNombre() + "!");
            }
            // ... otros casos
            default -> System.out.println("Aún no sé cómo " + orden.verbo() + " un objeto.");
        }
    }
/*
    private void procesarComando(String comando, Ente jugador) {
      switch (comando) {
      	case "hablar" -> 
      		jugador.hablar("Santo Santo Santo");
      
      	case "comer" -> {
      		Narrador.obtenerInstancia().narrar("Buscando algo para comer... ", 20);
      		Narrador.obtenerInstancia().narrar("Objetivo localizado:", 40);
      	// Aquí luego conectaremos con la lógica de inventario o escena
      	}
      	case "estadistica" -> 
      		Narrador.obtenerInstancia().narrar(jugador.toString(), 10);
      		
      	case "ayuda" ->
      		System.out.println("Comandos disponibles: hablar, comer, estadistica, ayuda, salir");
      		
      	default -> {
      		if (comando.startsWith("ir ")) {
      			System.out.println("Validando ruta hacia" + comando.substring(3));
      		}else {
      			System.out.println(comando + " Comando no existe.");
      			Narrador.obtenerInstancia().narrar("intenta con el comando - ayuda - ", 5);
      		}
      	}
}}

*/
    
}