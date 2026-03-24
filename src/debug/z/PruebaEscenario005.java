package debug.z;

import com.ente.*;
import com.motor.Navegador;
import com.rpg.teatro.Escena;
import herramientas.guardado.GestorGuardado;
import herramientas.tiempo.CalendarioLunar;
import herramientas.texto.Narrador;

public class PruebaEscenario005 {
    public static void main(String[] args) {
        Narrador n = Narrador.obtenerInstancia();
        n.narrar("=== [PRUEBA 005]: EL DESPERTAR DEL SUJETO ERICK ===", 10);

        // 1. CARGA DE ADN (Desde perfil.txt)
        // El GestorGuardado lee el archivo y Creador construye el cuerpo
        Ente erick = GestorGuardado.cargarPerfilJugador();
        n.narrar("Sujeto cargado: " + erick.obtenerNombre() + " | Chasis detectado.", 20);

        // 2. INICIALIZACIÓN DEL MUNDO
        Navegador nav = new Navegador();
        CalendarioLunar cal = new CalendarioLunar();
        
        // Viajamos al sector de origen [0,100,0]
        Escena campoBatalla = nav.viajar("inicio", erick, cal);

        // 3. POBLACIÓN DE LA ESCENA (Depredadores con IA)
        Ente og = Creador.obtenerInstancia().instanciarDesdePlantilla(com.rpg.ente.bestiario.fauna.Orco.Og);
        og.establecerAlineamiento(EjeEtico.CAOTICO, EjeMoral.MALO);
        og.teletransportar(8, 100, 8); // Posición inicial lejana
        
        campoBatalla.agregarEnte(og);

        // 4. EL BUCLE DE EXISTENCIA
        // Aquí es donde se activan decidirAccion() y avanzarHacia()
        n.narrar("\n--- LA SIMULACIÓN COMIENZA ---", 10);
        campoBatalla.ejecutarBatallaReal();
        

        n.narrar("\n=== FIN DE LA PRUEBA 005 ===", 10);
    }
}