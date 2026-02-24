package com.ente;

import java.time.LocalDateTime;

/**
 * Representa la raíz de la ontología: el Ente.
 * Provee identidad, trazabilidad y existencia en el sistema.
 */
public abstract class Ente {
    protected double id;
    protected String nombre;
    protected String descripcion;
    protected LocalDateTime fechaCreacion;
    protected String creador;

    public Ente(double id, String nombre, String descripcion, String creador) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.creador = creador;
        this.fechaCreacion = LocalDateTime.now();
    }

    public double getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getCreador() {
        return creador;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Ente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", creador='" + creador + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}