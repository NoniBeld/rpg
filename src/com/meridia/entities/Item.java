package com.meridia.entities;

public record Item(
    String nombre,
    double peso,
    int valor,
    int daño,
    double critico,
    String material,
    String descripcion,
    String creador
) {
    // Podemos añadir un constructor compacto para validar que el peso no sea negativo
    public Item {
        if (peso < 0) throw new IllegalArgumentException("El peso no puede ser negativo");
    }

	public String getNombre() {
		// TODO Auto-generated method stub
		return null;
	}
}