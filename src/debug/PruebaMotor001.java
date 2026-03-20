package debug;

import com.rpg.motor.*;
import com.rpg.ente.*;
import herramientas.tiempo.CalendarioLunar;

public class PruebaMotor001 {
    public static void main(String[] args) {
        // 1. Configuración de datos
        CalendarioLunar cal = new CalendarioLunar();
        Ente erick = Creador.obtenerInstancia().crearNuevoEnte("Erick", Funcion.SUJETO);
        
        // 2. Iniciamos el Motor (Hilo secundario)
        MotorJuego motor = new MotorJuego(cal);
        motor.iniciar(); 
        
        // 3. Iniciamos la Entrada (Hilo principal)
        GestorEntrada entrada = new GestorEntrada();
        entrada.iniciarBucleEscucha(erick, motor);
        
        System.out.println(">>> Programa finalizado con éxito.");
    }
}