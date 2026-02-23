package com.meridia;

import com.meridia.entities.*;
import java.util.*;

public class MeridiaRPG {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 1. Setup de personajes
        List<Entidad> personajes = new ArrayList<>();
        personajes.add(PersonajeFactory.crearSoul());
        personajes.add(PersonajeFactory.crearLaxir());
        personajes.add(PersonajeFactory.crearAlejandra());
        personajes.add(PersonajeFactory.crearNalanga());

        System.out.println("--- GRAN DUELO DE MERIDIA ---");

        // 2. Sistema de Turnos por Iniciativa
        // Ordenamos por Agilidad + Velocidad (Simulando iniciativa)
        personajes.sort((a, b) -> Integer.compare(
            b.getAtributos().get(Atributo.AGILIDAD) + b.getAtributos().get(Atributo.VELOCIDAD),
            a.getAtributos().get(Atributo.AGILIDAD) + a.getAtributos().get(Atributo.VELOCIDAD)
        ));

        while (vivos(personajes) > 1) {
            for (Entidad activo : personajes) {
                if (activo.getVidaActual() <= 0) continue;

                // Seleccionar objetivo (el siguiente vivo en la lista que no sea él mismo)
                Entidad objetivo = personajes.stream()
                    .filter(e -> e != activo && e.getVidaActual() > 0)
                    .findFirst().orElse(null);

                if (objetivo == null) break;

                mostrarEstado(activo, objetivo);
                ejecutarTurno(activo, objetivo, scanner);
            }
        }
        System.out.println("¡El entrenamiento ha terminado!");
    }

    private static void ejecutarTurno(Entidad atacante, Entidad objetivo, Scanner sc) {
        System.out.println(">>> Turno de " + atacante.getNombre());
        System.out.println("1. Especial  2. Básico");
        int op = sc.nextInt();
        
        double dano = atacante.calcularDano(op);
        objetivo.recibirDano(dano);
        
        System.out.printf("%s infligió %.2f de daño a %s%n", atacante.getNombre(), dano, objetivo.getNombre());
    }

    private static void mostrarEstado(Entidad a, Entidad b) {
        System.out.printf("%n[ %s: %.2f HP ] vs [ %s: %.2f HP ]%n", 
            a.getNombre(), a.getVidaActual(), b.getNombre(), b.getVidaActual());
    }

    private static long vivos(List<Entidad> lista) {
        return lista.stream().filter(e -> e.getVidaActual() > 0).count();
    }
}