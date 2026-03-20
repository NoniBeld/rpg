package com.rpg.ente;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import herramientas.texto.Narrador;
import herramientas.tiempo.Clima;

/**
 * Representa cualquier objeto con existencia en el mundo del juego.
 * Utilizamos una clase final para evitar jerarquías de herencia innecesarias (Composición).
 */
public final class Ente {
    private final int identificadorUnico;
    private String nombre;
    private Funcion funcionActual;
    
    private int puntosDeVidaActuales;
    private int puntosDeVidaMaximos;
	private EstadoMateria estadoActual;

    private float temperaturaIdeal = 20.0f; // Por defecto 20°C
    
    private List<Ente> inventario = new ArrayList<>();
        
 // Almacenamos los valores de los atributos de forma dinámica
    private final Map<Atributo, Integer> estadisticas;
    private final Map<ParteDelCuerpo, Integer> integridadFisica; // Salud por parte del cuerpo
    private final Map<Funcion, Consumer<Ente>> comportamientos = new EnumMap<>(Funcion.class);
    
    // Atributos de estado (POD - Programación Orientada a Datos)
    // En el futuro, estos podrían ser objetos "Componente"
    private float posicionX, posicionY, posicionZ;
    
    public Ente(int identificadorUnico, String nombre, Funcion funcionInicial) {
        this.identificadorUnico = identificadorUnico;
        this.nombre = nombre;
        this.funcionActual = funcionInicial;
        this.estadoActual = EstadoMateria.SOLIDO;
        
        this.estadisticas = new EnumMap<>(Atributo.class);
        this.integridadFisica = new EnumMap<>(ParteDelCuerpo.class);
        
     // Inicializamos vida por defecto
        this.puntosDeVidaMaximos = 10;
        this.puntosDeVidaActuales = 10;
        
        inicializarEnte();
    
    }

    private void inicializarEnte() {
        // Por defecto todos los entes nacen con 0 en todo
    	for (Atributo a : Atributo.values()) estadisticas.put(a, 0);
        for (ParteDelCuerpo p : ParteDelCuerpo.values()) integridadFisica.put(p, 100); // 100% salud
    }
    
    public void establecerValorAtributo(Atributo atributo, int valor) {
        estadisticas.put(atributo, valor);
    }

    public int obtenerValorAtributo(Atributo atributo) {
        return estadisticas.getOrDefault(atributo, 0);
    }

 // ---Inventario---
    

    /**
     * Busca un ente dentro del inventario por su nombre.
     * @param nombreObjetivo El texto introducido por el usuario.
     * @return El Ente encontrado o null si no existe.
     */
    public Ente buscarEnInventario(String nombreObjetivo) {
        return this.inventario.stream()
            .filter(e -> e.obtenerNombre().equalsIgnoreCase(nombreObjetivo))
            .findFirst()
            .orElse(null); 
    }
    
    /**
     * Busca un objetivo primero en el inventario y luego en la escena actual.
     */
    public Ente buscarEnAlcance(String nombre, List<Ente> entesEnEscena) {
        // 1. Buscar en lo que cargo (Prioridad)
        Ente encontrado = buscarEnInventario(nombre);
        
        // 2. Si no lo tengo, buscar en el suelo (Escena)
        if (encontrado == null) {
            encontrado = entesEnEscena.stream()
                .filter(e -> e.obtenerNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
        }
        
        return encontrado;
    }
    
    public void mostrarContenido(int nivel) {
        String sangria = "  ".repeat(nivel); // Para crear jerarquía visual
        
        if (this.inventario.isEmpty()) {
            System.out.println(sangria + "- (Vacío)");
            return;
        }

        for (Ente e : this.inventario) {
            System.out.println(sangria + "└─ " + e.obtenerNombre() + " [" + e.obtenerFuncionActual() + "]");
            // Si el objeto es un CONTENEDOR, mostramos su interior también
            if (e.obtenerFuncionActual() == Funcion.CONTENEDOR) {
                e.mostrarContenido(nivel + 1);
            }
        }
    }
    
    
    @Override
    public String toString() {
        return String.format("Ente[ID: %d, Nombre: %s, Funcion: %s, Pos: (%.1f, %.1f, %.1f)]", 
                identificadorUnico, nombre, funcionActual, posicionX, posicionY, posicionZ);
    }

    
    //--------------------   Funciones  ----------------------------------------------
    
    public void hablar(String mensaje) {
    	String formato = String.format("[%s]: %s", this.obtenerNombre(), mensaje);    	
    	herramientas.texto.Narrador.obtenerInstancia().narrar(formato, 40);
    }
    public void recoger(Ente objeto) {
    	if(objeto.obtenerFuncionActual() != Funcion.SUJETO) {
    		this.inventario.add(objeto);
    		Narrador.obtenerInstancia().narrar(this.nombre + "ha Guardado " + objeto.obtenerNombre(), 30);
    	}
    }
    
    public void interactuar(Ente objetivo) {
    	switch (objetivo.obtenerFuncionActual()) {
    	case ALIMENTO -> {
    	consumir(objetivo);
    		this.hablar(objetivo.obtenerNombre() + ". es comido." );
    	}
    	case ARMA ->{ 
    		recoger(objetivo);
            this.hablar("Esta arma me será útil para el Coliseo.");
            }
        case SUJETO->{
            this.hablar("Saludos, " + objetivo.obtenerNombre() + ". ¿Qué haces en este lugar?");
        }
        default->
            this.hablar("Es un " + objetivo.obtenerNombre() + "... no parece hacer nada.");
      	}
    }
    
    private void consumir(Ente alimento) {
    	int nutricion = 20;
    	this.recibirImpacto(-nutricion);
    	
    	Narrador.obtenerInstancia().narrar(this.nombre +" consume " + alimento.obtenerNombre() + ". vida actual: " + this.puntosDeVidaActuales, 40);
    	alimento.cambiarFuncion(Funcion.OBJETO);
    }

    // --- NUEVA FUNCIÓN: Evolución de Función ---
    public void cambiarFuncion(Funcion nuevaFuncion) {
        herramientas.texto.Narrador.obtenerInstancia().narrar(
            String.format("¡Atención! %s ha dejado de ser %s para convertirse en %s.", 
            nombre, funcionActual, nuevaFuncion), 30);
        this.funcionActual = nuevaFuncion;
    }
    
    private void inicializarComportamientos() {
        comportamientos.put(Funcion.ALIMENTO, this::consumir);
        comportamientos.put(Funcion.ARMA, obj -> {
            recoger(obj);
            this.hablar("¡Un arma nueva!");
        });
        comportamientos.put(Funcion.SUJETO, obj -> this.hablar("Hola, " + obj.obtenerNombre()));
    }

    public void interactuarPro(Ente objetivo) {
        comportamientos.getOrDefault(objetivo.obtenerFuncionActual(), 
            obj -> this.hablar("No sé qué hacer con esto.")
        ).accept(objetivo);
    }

  //--------------------   Movimiento  ----------------------------------------------
    
    public void teletransportar(float x, float y, float z) {
        this.posicionX = x; this.posicionY = y; this.posicionZ = z;
    }
    
    /**
     * Calcula la distancia física entre este ente y otro en el espacio 3D.
     */
    public float calcularDistancia(Ente otro) {
        float deltaX = otro.posicionX - this.posicionX;
        float deltaY = otro.posicionY - this.posicionY;
        float deltaZ = otro.posicionZ - this.posicionZ;

        // Aplicamos Pitágoras: d = sqrt(dx² + dy² + dz²)
        return (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }
    // Métodos para modificar el estado sin destruir el objeto
    public void mover(float deltaX, float deltaY) {
        this.posicionX += deltaX;
        this.posicionY += deltaY;
    }

    //--------------------   Fisica  ----------------------------------------------
    
    /**
     * Determina si otro ente está dentro de un rango de visión o percepción.
     */
    public boolean percibir(Ente otro, float rangoDeVision) {
        return calcularDistancia(otro) <= rangoDeVision;
    }
    
    /**
     * Actualiza el estado del ente basándose en el tiempo transcurrido.
     * @param delta El tiempo que ha pasado desde la última actualización.
     */
    public void actualizar(double delta) {
        // Aquí podemos implementar regeneración pasiva, efectos de veneno, etc.
        
        // Ejemplo: Recuperar 1 punto de VIDA cada 60 segundos si está descansando
        if (obtenerValorAtributo(Atributo.VIDA) < 100) {
            // Lógica de recuperación basada en delta
        }
    }
    
    public void sentirClima(Clima climaActual) {
        float diferencia = Math.abs(climaActual.temperaturaAmbiente() - temperaturaIdeal);
        
        if (diferencia > 15.0f) {
            this.hablar("¡El clima es insoportable! Me siento débil.");
            // Aquí el framework podría bajarle la FUERZA automáticamente
        } else {
            this.hablar("El clima de " + climaActual.nombre() + " me sienta bien.");
        }
    }
    
    public void recibirImpacto(int cantidad) {
        this.puntosDeVidaActuales -= cantidad;
        
        verificarEstadoFisico();
    }

    private void verificarEstadoFisico() {
        if (puntosDeVidaActuales > 0) {
            this.estadoActual = EstadoMateria.SOLIDO; // O el que tuviera
        } else if (puntosDeVidaActuales < 0 && puntosDeVidaActuales > -100) {
            // Aquí entra tu idea: Vida Negativa
            this.hablar("Mi esencia se oscurece... Vida actual: " + puntosDeVidaActuales);
        } else if (puntosDeVidaActuales <= -100) {
            this.hablar("Mi cuerpo no soporta más el vacío. Desvaneciendo...");
            // Lógica de eliminación del mapa
        }
    }
  
    

    // ------------------- Getters y Setters con nombres completos
    
    
    public int obtenerIdentificadorUnico(){ return identificadorUnico; }
    public String obtenerNombre() { return nombre;  	}
    public int obtenerVidaActual() { return puntosDeVidaActuales; }
    public Funcion obtenerFuncionActual() { return funcionActual; }

	public void cambiarNombre(String string) {
		// TODO Auto-generated method stub
		
	}


}