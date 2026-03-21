package com.rpg.teatro;

import com.rpg.ente.Ente;
import com.rpg.ente.Tamaño;

import java.util.ArrayList;
import java.util.List;

import herramientas.clima.Bioma;
import herramientas.clima.Clima;
import herramientas.texto.Narrador;
import herramientas.tiempo.CalendarioLunar;

public final class Escena {
	private String nombre;
	private Bioma bioma;
    private String descripcion;
    private List<Ente> presentes = new ArrayList<>();

    private CalendarioLunar calendario;
    private Clima clima;
    
    
    public Escena(String nombre, String descripcion, CalendarioLunar calendario, Clima clima, Bioma bioma) {
    	this.nombre = nombre;
        this.descripcion = descripcion;
        this.calendario = calendario;
        this.clima = clima;
        this.bioma = bioma;
        this.presentes = new ArrayList<>();
    }
    

 // 3. Gestión de Entes (Vital para el Generador Procedural)
    public void agregarEnte(Ente ente) {
        if (!presentes.contains(ente)) {
            presentes.add(ente);
        }
    }

    
    public Bioma obtenerBioma() {
        return this.bioma;
    }


    public void jugar() {
        Narrador.obtenerInstancia().narrar("\n--- NUEVA ESCENA ---", 20);
        Narrador.obtenerInstancia().narrar(descripcion, 50);
        Narrador n = Narrador.obtenerInstancia();
        
        n.narrar("[ Tiempo: " + calendario.obtenerFechaFormateada() + " ]", 10);
        n.narrar("[ Clima: " + clima.nombre() + " | " + clima.temperaturaAmbiente() + "°C ]", 10);
        n.narrar("------------------------------------------------", 10);
        
        // 2. Descripción Atmosférica
        n.narrar(descripcion, 40);
        
        for (Ente e : presentes) {
            Narrador.obtenerInstancia().narrar("Ves un " + e.obtenerNombre() + " (" + e.obtenerFuncionActual() + ")", 30);
            e.sentirClima(clima);
        }
    }
    
    public void actualizarEntidades(double delta) {
        for (Ente e : presentes) {
            e.actualizar(delta); // Este es el método que ya definimos en Ente
        }
    }
 // En Escena.java
    public String obtenerDescripcion(Ente observador) {
        Tamaño escala = observador.obtenerTamaño();
        
        return switch (escala) {
            case MEDIO -> "Ves un lago tranquilo con una pared de piedra al fondo.";
            case COLOSAL -> "Ves un pequeño charco acumulado en una grieta de tu jardín.";
            case MINUSCULO -> "Estás frente a una muralla de metal ciclópea que retiene un océano infinito.";
            default -> "Un paisaje cuya escala te resulta incomprensible.";
        };
    }
    public List<Ente> obtenerPresentes() {
        // Si por alguna razón es null, devolvemos una lista vacía para evitar que el motor explote
        if (this.presentes == null) {
            this.presentes = new ArrayList<>();
        }
        return this.presentes;
    }

	public String obtenerNombre() {		
		
		return this.nombre;
	}
}