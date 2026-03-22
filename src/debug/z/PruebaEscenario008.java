package debug.z;

import com.rpg.ente.Atributo;
import com.rpg.ente.Creador;
import com.rpg.ente.Ente;
import com.rpg.ente.bestiario.fauna.Humano;
import com.rpg.ente.bestiario.fauna.Slime;
import com.rpg.motor.Navegador;
import com.rpg.teatro.Escena;

import herramientas.texto.Narrador;
import herramientas.tiempo.CalendarioLunar;

public class PruebaEscenario008 {
	public static void main(String[] args) {
	    Narrador n = Narrador.obtenerInstancia();
	    Creador c = Creador.obtenerInstancia();
	    Navegador nav = new Navegador();
	    CalendarioLunar cal = new CalendarioLunar();
	    
	    Escena coliseo = nav.viajar("Coliseo de Biomasa", null, cal);

	    // --- EQUIPO ALFA (Lado Oeste) ---
	    Ente adanAlfa = c.instanciarDesdePlantilla(Humano.Adan);
	    adanAlfa.cambiarNombre("Adan_Alfa");
	    adanAlfa.teletransportar(-10, 100, 0);
	    adanAlfa.establecerValorAtributo(Atributo.SUERTE, 1); // ID de Facción en Suerte (truco temporal)

	    // Sus 3 Slimes (Fresa, Mora, Limon)
	    Ente[] slimesAlfa = {
	        c.instanciarDesdePlantilla(Slime.SLIME_FRESA),
	        c.instanciarDesdePlantilla(Slime.SLIME_MORA),
	        c.instanciarDesdePlantilla(Slime.SLIME_LIMON)
	    };
	    for(Ente s : slimesAlfa) { 
	        s.teletransportar(-12, 100, (float)(Math.random()*5-2.5));
	        s.establecerValorAtributo(Atributo.SUERTE, 1); 
	        coliseo.agregarEnte(s);
	    }

	    // --- EQUIPO BETA (Lado Este) ---
	    Ente adanBeta = c.instanciarDesdePlantilla(Humano.Adan);
	    adanBeta.cambiarNombre("Adan_Beta");
	    adanBeta.teletransportar(10, 100, 0);
	    adanBeta.establecerValorAtributo(Atributo.SUERTE, 2); // ID de Facción 2

	    Ente[] slimesBeta = {
	        c.instanciarDesdePlantilla(Slime.SLIME_FRESA),
	        c.instanciarDesdePlantilla(Slime.SLIME_MORA),
	        c.instanciarDesdePlantilla(Slime.SLIME_LIMON)
	    };
	    for(Ente s : slimesBeta) { 
	        s.teletransportar(12, 100, (float)(Math.random()*5-2.5));
	        s.establecerValorAtributo(Atributo.SUERTE, 2); 
	        coliseo.agregarEnte(s);
	    }

	    coliseo.agregarEnte(adanAlfa);
	    coliseo.agregarEnte(adanBeta);

	    n.narrar("=== [SISTEMA]: INICIANDO GUERRA DE FACCIONES (8 ENTIDADES) ===", 10);
	    coliseo.ejecutarBatallaReal();
	}
}
