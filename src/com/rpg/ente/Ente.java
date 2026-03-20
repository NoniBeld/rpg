package com.rpg.ente;

import java.util.EnumMap;
import java.util.Map;

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
        
 // Almacenamos los valores de los atributos de forma dinámica
    private final Map<Atributo, Integer> estadisticas;
    private final Map<ParteDelCuerpo, Integer> integridadFisica; // Salud por parte del cuerpo
    	
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

 // --- Métodos de Acción ---
    public void teletransportar(float x, float y, float z) {
        this.posicionX = x; this.posicionY = y; this.posicionZ = z;
    }
    
    @Override
    public String toString() {
        return String.format("Ente[ID: %d, Nombre: %s, Funcion: %s, Pos: (%.1f, %.1f, %.1f)]", 
                identificadorUnico, nombre, funcionActual, posicionX, posicionY, posicionZ);
    }

    public void hablar(String mensaje) {
    	String formato = String.format("[%s]: %s", this.obtenerNombre(), mensaje);    	
    	herramientas.texto.Narrador.obtenerInstancia().narrar(formato, 40);
    }
    // Métodos para modificar el estado sin destruir el objeto
    public void mover(float deltaX, float deltaY) {
        this.posicionX += deltaX;
        this.posicionY += deltaY;
    }

    // Getters y Setters con nombres completos
    public int obtenerIdentificadorUnico() {
    	return identificadorUnico;
    	}
    public String obtenerNombre() { 
    	return nombre; 
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
    
 // --- NUEVA FUNCIÓN: Evolución de Función ---
    public void cambiarFuncion(Funcion nuevaFuncion) {
        herramientas.texto.Narrador.obtenerInstancia().narrar(
            String.format("¡Atención! %s ha dejado de ser %s para convertirse en %s.", 
            nombre, funcionActual, nuevaFuncion), 30);
        this.funcionActual = nuevaFuncion;
    }
    
 // Getters y Setters
    public int obtenerVidaActual() { return puntosDeVidaActuales; }
    public Funcion obtenerFuncionActual() { return funcionActual; }
}