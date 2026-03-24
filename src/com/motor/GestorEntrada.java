package com.motor;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.herramientas.texto.Narrador;

import com.combate.ArbitroCombate;
import com.combate.AtaqueBase;
import com.ente.Atributo;
import com.ente.Creador;
import com.ente.Ente;
import com.ente.Funcion;
import com.ente.ParteDelCuerpo;
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
            case ABRIR -> { // Comando: "abrir gallo"
                Ente contenedor = jugador.buscarEnAlcance(nombreObj, escenaActual.obtenerPresentes());
                
                if (contenedor == null) {
                    // Si no está en el suelo, buscamos en nuestro inventario
                    contenedor = jugador.buscarEnInventario(nombreObj);
                }

                if (contenedor != null && !contenedor.obtenerInventario().isEmpty()) {
                    // Sacamos al primer hijo de la matruza
                    Ente liberado = contenedor.obtenerInventario().remove(0); 
                    
                    // Lo posicionamos cerca del jugador para que sea visible
                    liberado.teletransportar(jugador.obtenerPosicionX() + 1, 
                                             jugador.obtenerPosicionY(), 
                                             jugador.obtenerPosicionZ() + 1);
                    
                    escenaActual.agregarEnte(liberado);
                    
                    Narrador.obtenerInstancia().narrar("¡" + contenedor.obtenerNombre() + " se abre! " + 
                                                      liberado.obtenerNombre() + " ha emergido.", 30);
                } else {
                    System.out.println("El objeto está vacío o no se puede abrir.");
                }}
            case GUARDAR -> {
                Narrador.obtenerInstancia().narrar("Guardando esencia en el registro universal...", 40);
                com.herramientas.guardado.GestorGuardado.guardarProgreso(jugador);
                System.out.println(">>> [SISTEMA]: Partida guardada con éxito.");
            }
            case SPAWN -> { // Comando: "spawn slime 10" (Crea 10 slimes)
                String[] info = nombreObj.split(" ");
                String tipo = info[0].toUpperCase();
                int cantidad = (info.length > 1) ? Integer.parseInt(info[1]) : 1;

                for (int i = 0; i < cantidad; i++) {
                    Ente nuevo;
                    // Decidimos qué crear según el texto
                    if (tipo.contains("ORCO")) nuevo = Creador.obtenerInstancia().crearNuevoEnte("Orco_Spawned", Funcion.SUJETO);
                    else if (tipo.contains("SLIME")) nuevo = Creador.obtenerInstancia().crearNuevoEnte("Slime_Spawned", Funcion.ALIMENTO);
                    else nuevo = Creador.obtenerInstancia().crearNuevoEnte("Cosa_Desconocida", Funcion.OBJETO);

                    // Lo ponemos en una posición aleatoria del mapa procedural
                    nuevo.teletransportar(Math.random() * 20, 0, Math.random() * 20);
                    escenaActual.agregarEnte(nuevo);
                }
                System.out.println(">>> Spawn exitoso: " + cantidad + " entes de tipo " + tipo);
            }
            case LANZAR -> {
                // Lógica de proyectil
                item.cambiarFuncion(Funcion.ARMA);
                System.out.println("¡Lanzas el " + item.obtenerNombre() + "!");
            }
            case ATACAR -> {
                String[] info = nombreObj.split(" ");
                String nombreEnte = info[0];
                String parteNombre = (info.length > 1) ? info[1].toUpperCase() : "TORSO";

                Ente victima = jugador.buscarEnAlcance(nombreEnte, escenaActual.obtenerPresentes());
                
                if (victima != null) {
                    try {
                        ParteDelCuerpo parte = ParteDelCuerpo.valueOf(parteNombre);
                        ArbitroCombate.procesarAtaqueDirigido(jugador, victima, AtaqueBase.CABEZAZO, parte);
                    } catch (IllegalArgumentException e) {
                        System.out.println("La parte '" + parteNombre + "' no es válida. Intentando al TORSO...");
                        ArbitroCombate.procesarAtaqueDirigido(jugador, victima, AtaqueBase.CABEZAZO, ParteDelCuerpo.TORSO);
                    }
                }
            }
            // ... otros casos
            default -> System.out.println("Aún no sé cómo " + orden.verbo() + " un objeto.");
        }
    }
    
}