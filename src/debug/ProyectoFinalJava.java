package debug;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProyectoFinalJava {

	record Compra(String usuario, double monto, String item) {}

    public static void main(String[] args) {
        List<Compra> ventasDelDia = List.of(
            new Compra("Player1", 59.99, "Elden Ring"),
            new Compra("Player2", 9.99, "Skins"),
            new Compra("Player3", 15.00, "Pase de Batalla")
        );

        List<String> reporteFinal = new CopyOnWriteArrayList<>();

        // Usamos try-with-resources para el ejecutor
        try (var ejecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            
            for (Compra compra : ventasDelDia) {
                ejecutor.submit(() -> {
                    try {
                        // El catch es obligatorio aquí dentro del hilo
                        Thread.sleep(100); 
                        
                        if (compra.monto() > 10) {
                            reporteFinal.add("Venta VIP: " + compra.item() + " por $" + compra.monto());
                        }
                    } catch (InterruptedException e) {
                        // Si el hilo es interrumpido, avisamos sin romper el programa
                        System.err.println("Error: El proceso de " + compra.usuario() + " fue interrumpido.");
                    }
                });
            }
            
        } catch (Exception e) {
            // Este catch atrapa errores generales del administrador de hilos
            System.err.println("Error crítico en el administrador: " + e.getMessage());
        }

        // Mostrar resultados
        System.out.println("--- RESULTADOS SEGUROS ---");
        reporteFinal.forEach(System.out::println);
    }
}