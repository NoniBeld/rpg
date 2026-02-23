package com.meridia.entities;
import java.util.*;

public class Inventario {
    private Map<String, Integer> items = new HashMap<>();

    public void agregarItem(String nombre) {
        items.put(nombre, items.getOrDefault(nombre, 0) + 1);
        System.out.println("[+] " + nombre + " añadido al inventario.");
    }

    public void mostrarInventario() {
        System.out.println("\n=== MOCHILA DE GRUPO ===");
        if (items.isEmpty()) System.out.println("La mochila está vacía.");
        items.forEach((nombre, cantidad) -> System.out.println("- " + nombre + " x" + cantidad));
    }

    public void vaciar() {
        items.clear();
        System.out.println("Has vaciado la mochila.");
    }
}