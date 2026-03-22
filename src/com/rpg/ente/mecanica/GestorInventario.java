package com.rpg.ente.mecanica;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.rpg.ente.Ente;
import com.rpg.ente.EstadoVital;
import com.rpg.ente.Funcion;
import com.rpg.ente.ParteDelCuerpo;
import com.rpg.ente.Atributo;
import com.rpg.ente.Creador;
import herramientas.texto.Narrador;

public class GestorInventario {
    private List<Ente> items = new ArrayList<>();
    private Ente armaEquipada;
	private List<Ente> presentes;

    // --- BÚSQUEDA ---
    public Ente buscarPorNombre(String nombre) {
        return items.stream()
            .filter(e -> e.obtenerNombre().equalsIgnoreCase(nombre))
            .findFirst()
            .orElse(null);
    }

    // --- EQUIPAMIENTO ---
    public void setArmaEquipada(Ente arma) {
        this.armaEquipada = arma;
        if (arma != null) {
            Narrador.obtenerInstancia().narrar("Empuñando: " + arma.obtenerNombre(), 20);
        }
    }

    // --- LA CARNICERÍA (Procesar Cadáver) ---
    public void ejecutarDeshuesar(Ente ejecutor, Ente cadaver) {
        Narrador n = Narrador.obtenerInstancia();
        n.narrar(">>> " + ejecutor.obtenerNombre() + " comienza a despiezar a " + cadaver.obtenerNombre(), 30);

        // 1. SAQUEO: Robar lo que el muerto cargaba
        List<Ente> botinEnemigo = cadaver.obtenerInventario();
        if (botinEnemigo != null && !botinEnemigo.isEmpty()) {
            for (Ente objeto : new ArrayList<>(botinEnemigo)) {
                this.items.add(objeto);
                System.out.println("[BOTÍN]: Has obtenido " + objeto.obtenerNombre());
            }
            botinEnemigo.clear(); // El cuerpo queda vacío
        }

        // 2. COSECHA: Extraer partes del cuerpo (Carne, huesos, etc.)
        // Accedemos al mapa de integridad que vive en el SistemaVital del muerto
        Map<ParteDelCuerpo, Integer> cuerpo = cadaver.obtenerMapaIntegridad();
        
        cuerpo.forEach((parte, integridad) -> {
            if (integridad > 0) {
                // Creamos un nuevo objeto "Resto de..."
                Ente trofeo = Creador.obtenerInstancia().crearNuevoEnte(
                    "Resto de " + parte + " (" + cadaver.obtenerNombre() + ")", 
                    Funcion.ALIMENTO
                );
                // La "calidad" del filete depende de qué tan sana estaba la parte
                trofeo.establecerValorAtributo(Atributo.RESISTENCIA, integridad);
                this.items.add(trofeo);
                System.out.println("[COSECHA]: Extraído " + parte + " (Integridad: " + integridad + "%)");
            }
        });
    }

    public List<Ente> getItems() {
        return this.items;
    }

    public void procesarCadaver(Ente ejecutor, Ente cadaver) {
        Narrador n = Narrador.obtenerInstancia();
        n.narrar(">>> " + ejecutor.obtenerNombre() + " comienza a despiezar el cuerpo de " + cadaver.obtenerNombre(), 30);

        // 1. FASE DE SAQUEO (Robar el inventario del muerto)
        // Obtenemos la lista de items que el cadáver tenía antes de morir
        List<Ente> botinEnemigo = cadaver.obtenerInventario();
        
        if (botinEnemigo != null && !botinEnemigo.isEmpty()) {
            // Usamos una copia para evitar errores de modificación concurrente
            for (Ente objeto : new ArrayList<>(botinEnemigo)) {
                this.items.add(objeto); // Los añadimos al inventario del ejecutor
                System.out.println("   [BOTÍN]: Reclamado: " + objeto.obtenerNombre());
            }
            botinEnemigo.clear(); // El cadáver queda vacío
        }

        // 2. FASE DE COSECHA BIOLÓGICA (Extraer carne y órganos)
        // Accedemos al mapa de integridad física del cadáver
        Map<ParteDelCuerpo, Integer> anatomia = cadaver.obtenerMapaIntegridad();

        anatomia.forEach((parte, integridad) -> {
            if (integridad > 0) {
                // Creamos un nuevo Ente que representa el trofeo orgánico
                Ente trofeo = Creador.obtenerInstancia().crearNuevoEnte(
                    "Resto de " + parte + " (" + cadaver.obtenerNombre() + ")", 
                    Funcion.ALIMENTO
                );
                
                // La calidad del "filete" depende de la salud de la parte al morir
                trofeo.establecerValorAtributo(Atributo.RESISTENCIA, integridad);
                
                this.items.add(trofeo); // Se guarda en el inventario de quien deshuesa
                System.out.println(String.format("   [COSECHA]: %s extraído (Integridad: %d%%)", parte, integridad));
            } else {
                System.out.println("   [AVISO]: El " + parte + " estaba demasiado dañado para aprovecharse.");
            }
        });
        
        n.narrar("Procesamiento de restos finalizado.", 20);
    }
    public void removerEnte(Ente objetivo) {
        if (presentes.contains(objetivo)) {
            presentes.remove(objetivo);
            Narrador.obtenerInstancia().narrar(objetivo.obtenerNombre() + " ha desaparecido de la escena.", 10);
        }
    }

    public Ente obtenerMasDebil() {
        return presentes.stream()
            .filter(e -> e.obtenerEstadoVital() == EstadoVital.VIVO)
            .min((e1, e2) -> Integer.compare(e1.obtenerVidaActual(), e2.obtenerVidaActual()))
            .orElse(null);
    }
    
    
}