package debug;

import com.rpg.ente.*;
import com.rpg.motor.Navegador;
import com.rpg.teatro.Escena;
import herramientas.guardado.GestorGuardado;
import herramientas.tiempo.CalendarioLunar;
import herramientas.texto.Narrador;

import com.rpg.ente.bestiario.fauna.Humano;
import com.rpg.ente.bestiario.fauna.Orco;
import com.rpg.ente.bestiario.fauna.Slime;
import com.rpg.ente.bestiario.objeto.arma.Espada;
import com.rpg.ente.bestiario.objeto.arma.Hacha;

public class PruebaEscenario007 {


	public static void main(String[] args) {
		Narrador n = Narrador.obtenerInstancia();
	    Creador c = Creador.obtenerInstancia();
	    Navegador nav = new Navegador();
	    CalendarioLunar cal = new CalendarioLunar();
	    
	    Escena coliseo = nav.viajar("Coliseo", null, cal);

	    // --- EQUIPO A: Adán 1 y sus 3 Slimes ---
	    Ente adan1 = c.instanciarDesdePlantilla(Humano.Adan);
	    adan1.cambiarNombre("Adan_Alfa");
	    adan1.teletransportar(-5, 100, 0); 
	    
	    Ente sFresa1 = c.instanciarDesdePlantilla(Slime.SLIME_FRESA);
	    Ente sMora1 = c.instanciarDesdePlantilla(Slime.SLIME_MORA);
	    Ente sLimon1 = c.instanciarDesdePlantilla(Slime.SLIME_LIMON);

	    // --- EQUIPO B: Adán 2 y sus 3 Slimes ---
	    Ente adan2 = c.instanciarDesdePlantilla(Humano.Adan);
	    adan2.cambiarNombre("Adan_Beta");
	    adan2.teletransportar(5, 100, 0);

	    Ente sFresa2 = c.instanciarDesdePlantilla(Slime.SLIME_FRESA);
	    Ente sMora2 = c.instanciarDesdePlantilla(Slime.SLIME_MORA);
	    Ente sLimon2 = c.instanciarDesdePlantilla(Slime.SLIME_LIMON);

	    // Agregar todos a la escena
	    coliseo.agregarEnte(adan1); coliseo.agregarEnte(adan2);
	    coliseo.agregarEnte(sFresa1); coliseo.agregarEnte(sMora1); coliseo.agregarEnte(sLimon1);
	    coliseo.agregarEnte(sFresa2); coliseo.agregarEnte(sMora2); coliseo.agregarEnte(sLimon2);
        
        // 1. CARGA DEL SUJETO (Erick desde perfil.txt)
        Ente erick = GestorGuardado.cargarPerfilJugador();
        erick.inicializarAnatomiaPorChasis("HUMANOIDE"); // O "AMORFO" si es Slime
        n.narrar("Sujeto cargado: " + erick.obtenerNombre() + " en posición central.", 20);

        // 2. CONFIGURACIÓN DEL MUNDO
        Escena campoBatalla = nav.viajar("inicio", erick, cal);

        // 3. APARICIÓN DEL DEPREDADOR (Og)
        Ente og = c.instanciarDesdePlantilla(Orco.Og);
        og.establecerAlineamiento(EjeEtico.CAOTICO, EjeMoral.MALO);
        Ente espada = c.instanciarDesdePlantilla(Espada.Espada);
        Ente hacha = c.instanciarDesdePlantilla(Hacha.Hacha);
        // Lo ponemos lejos (X=10, Z=10) para ver cómo rastrea a Erick (0,0)
        og.teletransportar(10, 100, 10); 
        campoBatalla.agregarEnte(og);
        og.obtenerInventario().add(hacha);
        og.establecerArmaEquipada(hacha); // Necesitarás este método en Ente
        
        erick.obtenerInventario().add(espada);
        erick.establecerArmaEquipada(espada);
        
        n.narrar("\n[SISTEMA]: " + og.obtenerNombre() + " ha detectado a " + erick.obtenerNombre() + "...", 30);

        // 4. EL BUCLE DE EXISTENCIA (Movimiento y Combate)
        // Este método en Escena ya tiene el reporte de estado que implementamos
        campoBatalla.ejecutarBatallaReal();

        // 5. POST-MORTEM (Si alguien cayó, ver reporte de daños)
        n.narrar("\n=== REPORTE POST-SIMULACIÓN ===", 10);
        for (Ente e : campoBatalla.obtenerPresentes()) {
            if (e.obtenerVidaActual() <= -100 || e.obtenerEstadoVital() == EstadoVital.MUERTO) {
                System.out.println("\nAnalizando restos de: " + e.obtenerNombre());
                for (ParteDelCuerpo parte : ParteDelCuerpo.values()) {
                    int integridad = e.obtenerSaludDeParte(parte);
                    if (integridad < 100) {
                        System.out.println(" -> " + parte + ": " + integridad + "%");
                    }
                }
            }
        }

        n.narrar("\n=== FIN DE LA PRUEBA 007 ===", 10);
    }
}