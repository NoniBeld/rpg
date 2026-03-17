package debug;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class MasterClassHilos {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO SIMULADOR DE MICROTRANSACCIONES MASIVAS ---");
        
        long tiempoInicio = System.currentTimeMillis();

        // 1. Creamos un "Ejecutor" que usa Hilos Virtuales (Novedad de Java 21+)
        // Este comando le dice a Java: "Usa los hilos ligeros, no los pesados"
        try (var administradorDeHilos = Executors.newVirtualThreadPerTaskExecutor()) {
            
            // 2. Vamos a lanzar 10,000 tareas al mismo tiempo
            IntStream.range(0, 10_000).forEach(i -> {
                administradorDeHilos.submit(() -> {
                    // Simulamos que cada tarea es una compra en un juego
                    // que tarda 1 segundo en procesarse con el banco
                    Thread.sleep(Duration.ofSeconds(1));
                    
                    if (i % 1000 == 0) { 
                        System.out.println("Tarea #" + i + " completada por un hilo virtual.");
                    }
                    return null;
                });
            });
            
        } // El 'try' cierra el administrador y espera a que todos terminen

        long tiempoFinal = System.currentTimeMillis();
        double segundosTotales = (tiempoFinal - tiempoInicio) / 1000.0;

        System.out.println("------------------------------------------------");
        System.out.println("¡LISTO! Se procesaron 10,000 tareas.");
        System.out.println("Tiempo total: " + segundosTotales + " segundos.");
        System.out.println("Nota: ¡Hicimos 10,000 segundos de trabajo en casi 1 segundo!");
    }
}