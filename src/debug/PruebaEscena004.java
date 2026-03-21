package debug;

import com.rpg.ente.*;
import com.rpg.ente.bestiario.fauna.Orco;
import com.rpg.motor.Navegador;
import com.rpg.teatro.Escena;
import herramientas.guardado.GestorGuardado;
import herramientas.tiempo.CalendarioLunar;

public class PruebaEscena004 {
    public static void main(String[] args) {
        // 1. CARGAR AL JUGADOR (Desde perfil.txt)
        Ente jugador = GestorGuardado.cargarPerfilJugador(); 
        
        // 2. INICIAR NAVEGACIÓN
        Navegador nav = new Navegador();
        CalendarioLunar cal = new CalendarioLunar();
        
        // Viajamos al sector [0,0,0]
        Escena escena = nav.viajar("inicio", jugador, cal);

        // 3. POBLAR CON ALINEAMIENTOS (Para probar la IA)
        Ente og = Creador.obtenerInstancia().instanciarDesdePlantilla(Orco.Og);
        og.establecerAlineamiento(EjeEtico.CAOTICO, EjeMoral.MALO);
        og.teletransportar(10, 100, 10); // Lejos, para ver cómo camina
        
        escena.agregarEnte(og);

        // 4. EJECUTAR EL BUCLE DE MUNDO
        escena.ejecutarBatallaReal(); 
    }
}