package com.meridia.world;

import java.util.*;

public class Ubicacion {
    private String nombre;
    private String descripcion;
    private Map<String, Ubicacion> salidas; // "norte" -> Destino
    private int contadorVisitas = 0;
    
    public Ubicacion(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.salidas = new HashMap<>();
    }

    public void registrarVisita() { this.contadorVisitas++; }
    public int getVisitas() { return contadorVisitas; }
    
    // Método maestro para conectar dos lugares ida y vuelta con direcciones lógicas
    public void conectarCon(String direccionIda, Ubicacion destino, String direccionRegreso) {
        this.salidas.put(direccionIda.toLowerCase(), destino);
        destino.salidas.put(direccionRegreso.toLowerCase(), this);
    }

    public Ubicacion getDestino(String dir) { return salidas.get(dir.toLowerCase()); }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public Set<String> getSalidas() { return salidas.keySet(); }


    public void mostrarInfo() {
        System.out.println("\n=== " + nombre + " ===");
        System.out.println(descripcion);
        System.out.println("Salidas visibles: " + salidas.keySet());
    }

   
}