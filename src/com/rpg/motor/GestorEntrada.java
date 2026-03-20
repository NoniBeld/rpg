package com.rpg.motor;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import herramientas.texto.Narrador;

import com.rpg.ente.Atributo;
import com.rpg.ente.Ente;
import com.rpg.ente.Funcion;
import com.rpg.logica.Escena;

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

    public void iniciarBucleEscucha(Ente jugador, MotorJuego motor) {
        Narrador.obtenerInstancia().narrar(">>> Sistema de entrada listo. Escribe un comando (o 'salir'):", 20);
        
        while (escuchando) {
            System.out.print("\n[Comando]> ");
            String entradaRaw = teclado.nextLine().toLowerCase().trim();

            if (entradaRaw.equals("salir")) {
                escuchando = false;
                motor.detener();
                break;
            }

            procesarComandoPro(entradaRaw);
           // procesarComando(entradaRaw, jugador);
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
    
    @SuppressWarnings("null")
	private void ejecutarAccion(Orden orden, Ente jugador, Escena escenaActual) {
    	String nombreObjetivo = orden.objetivo();
    	
    	// 1. Buscar el objeto en el inventario o la escena
        Ente item = jugador.buscarEnInventario(orden.objetivo()); 
        
     // 1. Lógica para el comando MIRAR (puede ser general o a un objeto)
        if (orden.verbo() == Verbo.MIRAR) {
        	if (nombreObjetivo.isEmpty()) {
        		Narrador.obtenerInstancia().narrar("Mirar tus pertenencias", 20);
        		jugador.mostrarContenido(0);
        	}else {
        		// Buscamos en alcance (Inventario + Escena)
        		Ente algo = jugador.buscarEnAlcance(nombreObjetivo, escenaActual.obtenerPresentes());
        		if (algo != null) {
                    Narrador.obtenerInstancia().narrar("Observas el " + algo.obtenerNombre() + ":", 20);
                    algo.mostrarContenido(1);
                } else {
                    System.out.println("No ves ningún '" + nombreObjetivo + "' por aquí.");
                }
            }
            return; // Salimos ya que MIRAR no requiere el chequeo de null de abajo
        }
        	
    

        if (item == null) {
            System.out.println("No encuentras el objeto: " + nombreObjetivo);
            return;
        }
        
        if (item == null) {
            System.out.println("No tienes eso.");
            return;
        }

        // 2. Lógica Multimodal según la Función
        switch (orden.verbo()) {
            case COMER -> {
                if (item.obtenerFuncionActual() == Funcion.ALIMENTO) {
                    jugador.interactuar(item); // Ya lo cura y cambia su estado
                } else {
                    jugador.hablar("No puedo comer un " + item.obtenerNombre() + ", me rompería los dientes.");
                }
            }
            case LANZAR -> {
                // Aquí la manzana se comporta como ARMA temporalmente
                float daño = jugador.obtenerValorAtributo(Atributo.FUERZA) * 0.5f;
                item.cambiarFuncion(Funcion.ARMA);
                System.out.println("¡Lanzas el " + item.obtenerNombre() + " causando " + daño + " de impacto!");
            }
            case CANALIZAR -> {
                if (item.obtenerFuncionActual() == Funcion.CANALIZADOR || item.obtenerFuncionActual() == Funcion.OBJETO) {
                    jugador.hablar("Concentro mi energía a través de " + item.obtenerNombre());
                    // Lógica de conjuro
                }
            }
            case MIRAR ->{
            	String objetivo = null;
				if (objetivo.isEmpty()) {
                    Narrador.obtenerInstancia().narrar("Miras tus pertenencias:", 20);
                    jugador.mostrarContenido(0);
                } else {
                    Ente algo = jugador.buscarEnAlcance(objetivo, escenaActual.obtenerPresentes());
                    if (algo != null) {
                        Narrador.obtenerInstancia().narrar("Observas el " + algo.obtenerNombre() + ":", 20);
                        algo.mostrarContenido(1);
                    }
            }}
            
            // ... otros casos para RECEPTOR, TRANSMISOR, etc.
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