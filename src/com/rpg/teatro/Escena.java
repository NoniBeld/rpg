package com.rpg.teatro;

import com.rpg.ente.Ente;
import java.util.ArrayList;
import java.util.List;

import herramientas.clima.Clima;
import herramientas.texto.Narrador;
import herramientas.tiempo.CalendarioLunar;

public final class Escena {
	private String nombre;
    private String descripcion;
    private List<Ente> presentes = new ArrayList<>();

    private CalendarioLunar calendario;
    private Clima clima;
    
    
    public Escena(String nombre, String descripcion, CalendarioLunar calendario, Clima clima) {
    	this.nombre = nombre;
        this.descripcion = descripcion;
        this.calendario = calendario;
        this.clima = clima;
        this.presentes = new ArrayList<>();
    }
    
    public void agregarEnte(Ente ente ) {
        this.presentes.add(ente);
        
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

    public List<Ente> obtenerPresentes() {
        // Si por alguna razón es null, devolvemos una lista vacía para evitar que el motor explote
        if (this.presentes == null) {
            this.presentes = new ArrayList<>();
        }
        return this.presentes;
    }
}