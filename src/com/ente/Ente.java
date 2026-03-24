package com.ente;

import java.util.*;

import com.ente.mecanica.EntropiaOrganica;
import com.ente.mecanica.GestorInventario;
import com.ente.mecanica.NavegadorPersonal;
import com.ente.mecanica.SistemaVital;
import com.rpg.teatro.Escena;

import com.herramientas.clima.Clima;
import com.herramientas.texto.Narrador;

public final class Ente {
    // 1. IDENTIDAD Y ESTADO
    private final int identificadorUnico;
    private String nombre;
    private Funcion funcionActual;
    private Potencia potenciaActual = Potencia.INTEGRO;
    private Tamaño tamañoActual = Tamaño.MEDIO;
    private EjeEtico etica = EjeEtico.NEUTRAL;
    private EjeMoral moral = EjeMoral.NEUTRAL;
    private int hambre = 0;

    // 2. COMPONENTES (COMPOSICIÓN)
    private SistemaVital vital;
    private GestorInventario inventario;
    private NavegadorPersonal navegacion; // Cambiado de Object a su tipo real
    private EntropiaOrganica entropia;    // Cambiado de Object a su tipo real

    // 3. DATOS BASE
    private final Map<Atributo, Integer> estadisticas = new EnumMap<>(Atributo.class);
	private int tiempoVidaRestante;
	private Potencia nuevaPotencia;
	private Object nuevaFuncion;
	private String nuevoNombre;

    public Ente(int id, String nombre, Funcion funcion) {
        this.identificadorUnico = id;
        this.nombre = nombre;
        this.funcionActual = funcion;

        // Inicializar Componentes
        this.vital = new SistemaVital();
        this.inventario = new GestorInventario();
        this.navegacion = new NavegadorPersonal();
        this.entropia = new EntropiaOrganica();
    }
    
    // --- MÉTODOS PUENTE (Para solucionar los errores de compilación) ---

    public void recibirImpacto(int daño) {
        this.modificarVidaActual(-daño);
    }

    public void recibirImpactoLocalizado(int daño, ParteDelCuerpo zona) {
        this.vital.recibirImpactoLocalizado(this, daño, zona);
    }

    public void teletransportar(double x, double y, double z) {
        this.navegacion.teletransportar(x, y, z);
    }

    public void avanzarHacia(double x, double y, double z) {
        this.navegacion.avanzarHacia(this, x, y, z);
    }

    public float calcularDistancia(Ente otro) {
        return this.navegacion.calcularDistancia(this, otro);
    }

    public void establecerAlineamiento(EjeEtico etica, EjeMoral moral) {
        this.etica = etica;
        this.moral = moral;
    }

    public List<Ente> obtenerInventario() {
        return this.inventario.getItems();
    }

    public void establecerArmaEquipada(Ente arma) {
        this.inventario.setArmaEquipada(arma);
    }

    public int obtenerSaludDeParte(ParteDelCuerpo parte) {
        return this.vital.getIntegridadFisica().getOrDefault(parte, 0);
    }

    public EstadoVital obtenerEstadoVital() {
        // Si la vida es 0 o menos, está MUERTO (o CAÍDO según tu lógica)
        if (this.obtenerVidaActual() <= 0) {
            return EstadoVital.MUERTO; 
        }
        return EstadoVital.VIVO;
    }
    // --- LÓGICA DE ACTUALIZACIÓN ---

    public void actualizar(double delta, Clima clima) {
        this.entropia.aplicar(this, (float)delta, clima.temperaturaAmbiente());
        // Aquí puedes añadir más procesos por ciclo
    }

    public void modificarVidaActual(int cantidad) {
        // Este método actúa como setter seguro
        int vidaAnterior = this.obtenerValorAtributo(Atributo.VIDA);
        int nuevaVida = vidaAnterior + cantidad;
        this.establecerValorAtributo(Atributo.VIDA, nuevaVida);
        
        if (nuevaVida <= 0) {
            this.hablar("Mi esencia se oscurece...");
        }
    }

    public void hablar(String mensaje) {
        Narrador.obtenerInstancia().narrar("[" + this.nombre + "]: " + mensaje, 40);
    }

  
    public Map<ParteDelCuerpo, Integer> obtenerMapaIntegridad() { return vital.getIntegridadFisica(); }

	public int obtenerTiempoVida() { return (int) this.tiempoVidaRestante; 	}

	public void modificarTiempoVida(float delta) { 	this.tiempoVidaRestante += delta; }

	public void transformar(Potencia digerido) { String mensaje = String.format("¡La estructura de %s ha mutado de %s a %s!", 
            this.nombre, this.potenciaActual, nuevaPotencia);
			com.herramientas.texto.Narrador.obtenerInstancia().narrar(mensaje, 30);
			this.potenciaActual = nuevaPotencia;}

	public void cambiarNombre(String string) {this.nombre = nuevoNombre; }

	public void cambiarFuncion(Funcion alimento) { String aviso = String.format("¡Atención! %s ahora cumple la función de %s.", 
			this.nombre, nuevaFuncion);
			com.herramientas.texto.Narrador.obtenerInstancia().narrar(aviso, 20);
	}
	public void cambiarTamaño(Tamaño nuevoTamaño) { this.tamañoActual = nuevoTamaño;}
	public Tamaño obtenerTamaño() {  return this.tamañoActual;}
	public int obtenerVidaMax() { return this.vital.calcularVidaMaxima(this);}
	// --- PUENTE DE INVENTARIO Y BÚSQUEDA ---
	public Ente buscarEnAlcance(String nombre, List<Ente> entesEnEscena) {
	    // 1. Buscamos en nuestro propio inventario (delegamos al GestorInventario)
	    Ente encontrado = this.inventario.buscarPorNombre(nombre);
	    
	    // 2. Si no lo tenemos, buscamos en la escena
	    if (encontrado == null) {
	        encontrado = entesEnEscena.stream()
	            .filter(e -> e.obtenerNombre().equalsIgnoreCase(nombre))
	            .findFirst()
	            .orElse(null);
	    }
	    return encontrado;
	}

	public void mostrarContenido(int nivelDetalle) {
	    // Delegamos la impresión de items al gestor de inventario
	    List<Ente> items = this.inventario.getItems();
	    if (items.isEmpty()) {
	        System.out.println("   (Vacio)");
	    } else {
	        items.forEach(i -> System.out.println("   - " + i.obtenerNombre()));
	    }
	}

	// --- PUENTE DE INTERACCIÓN ---
	public void interactuar(Ente objetivo) {
	    if (objetivo.obtenerFuncionActual() == Funcion.ALIMENTO) {
	        // Lógica de comer
	        int nutricion = objetivo.obtenerValorAtributo(Atributo.RESISTENCIA) / 10;
	        this.hambre = Math.max(0, this.hambre - nutricion);
	        this.hablar("He consumido " + objetivo.obtenerNombre() + ". Hambre actual: " + this.hambre);
	        
	        // El objeto desaparece del mundo o inventario tras ser comido
	        // (Esto lo manejará la Escena o el Gestor de Inventario)
	    } else if (objetivo.obtenerEstadoVital() == EstadoVital.MUERTO) {
	        // Si interactuamos con un muerto, lo deshuesamos
	        this.ejecutarDeshuesar(objetivo);
	    }
	
	}
	
	
	/**
	 * Solicita al gestor de inventario que procese los restos de otro ente.
	 * @param objetivo El cadáver o ente a ser despiezado.
	 */
	public void ejecutarDeshuesar(Ente objetivo) {
	    // El 'ejecutor' es este mismo ente (this)
	    // El 'cadaver' es el objetivo
	    this.inventario.procesarCadaver(this, objetivo);
	}
	
	public void decidirAccion(Escena escena) {
	    // 0. VALIDACIÓN: Si estamos muertos o eliminados, no hacemos nada
	    if (this.obtenerEstadoVital() != EstadoVital.VIVO) return;

	    // 1. PERCEPCIÓN: ¿Quién está cerca?
	    Ente objetivoCercano = escena.buscarObjetivoCercano(this);
	    
	    // 2. PRIORIDAD 1: SUPERVIVENCIA (HAMBRE)
	    // Si el hambre es alta, buscamos comida o cadáveres ignorando el combate
	    if (this.hambre > 80) {
	        if (objetivoCercano != null && (objetivoCercano.obtenerFuncionActual() == Funcion.ALIMENTO 
	            || objetivoCercano.obtenerEstadoVital() == EstadoVital.MUERTO)) {
	            
	            this.actuarHaciaObjetivo(objetivoCercano, "BUSCAR_COMIDA");
	            return;
	        }
	    }

	    // 3. PRIORIDAD 2: ALINEAMIENTO (COMPORTAMIENTO MORAL)
	    if (objetivoCercano != null) {
	        // Ejemplo: Lógica Caótico Malo (Ataca a todo lo que se mueve)
	        if (this.etica == EjeEtico.CAOTICO && this.moral == EjeMoral.MALO) {
	            this.actuarHaciaObjetivo(objetivoCercano, "COMBATE");
	        } 
	        // Ejemplo: Lógica Neutral (Solo observa o se aleja si no hay hambre)
	        else {
	            this.hablar("Observo a " + objetivoCercano.obtenerNombre() + " con indiferencia.");
	        }
	    } else {
	        // Si no hay nadie, el ente simplemente vaga por el mapa (Wander)
	        this.avanzarHacia(this.obtenerPosicionX() + (Math.random() * 2 - 1), 
	                          this.obtenerPosicionY(), 
	                          this.obtenerPosicionZ() + (Math.random() * 2 - 1));
	    }
	}

	/**
	 * Método auxiliar para decidir si moverse o interactuar
	 */
	private void actuarHaciaObjetivo(Ente objetivo, String modo) {
	    double dist = this.calcularDistancia(objetivo);
	    double rangoInteraccion = 1.5; // Distancia para morder/golpear/recoger

	    if (dist <= rangoInteraccion) {
	        if (modo.equals("BUSCAR_COMIDA")) {
	            this.interactuar(objetivo);
	        } else if (modo.equals("COMBATE")) {
	            com.combate.ArbitroCombate.procesarDuelo(this, objetivo, com.combate.AtaqueBase.EMBESTIDA);
	        }
	    } else {
	        // Si está lejos, caminamos hacia él
	        this.avanzarHacia(objetivo.obtenerPosicionX(), 
	                          objetivo.obtenerPosicionY(), 
	                          objetivo.obtenerPosicionZ());
	    }
	}
	/**
	 * Busca un objeto dentro de las posesiones del Ente.
	 * @param nombre El nombre del objeto (ej: "Daga", "Manzana")
	 * @return El Ente encontrado o null si no lo carga.
	 */
	public Ente buscarEnInventario(String nombre) {
	    // Delegamos la búsqueda al componente especializado
	    return this.inventario.buscarPorNombre(nombre);
	}
	public void estallarContenido(Escena escena) {
	    List<Ente> contenido = this.obtenerInventario();
	    while (!contenido.isEmpty()) {
	        Ente e = contenido.remove(0);
	        // Esparcimos los objetos aleatoriamente cerca
	        e.teletransportar(this.obtenerPosicionX() + (Math.random() - 0.5), 
	                          this.obtenerPosicionY(), 
	                          this.obtenerPosicionZ() + (Math.random() - 0.5));
	        escena.agregarEnte(e);
	    }
	    this.hablar("¡He liberado todo mi contenido!");
	}
	public int obtenerIdentificadorUnico() {
	    return this.identificadorUnico;
	}
	/**
	 * Convierte el estado actual del ente en una cadena de texto para guardado.
	 * Formato: ID|Nombre|Funcion|X,Y,Z|VidaActual
	 */
	public String compactar() {
	    return String.format("%d|%s|%s|%.2f,%.2f,%.2f|%d",
	            this.identificadorUnico,
	            this.nombre,
	            this.funcionActual,
	            this.obtenerPosicionX(),
	            this.obtenerPosicionY(),
	            this.obtenerPosicionZ(),
	            this.obtenerVidaActual()
	    );
	}
	
	
	// --- GETTERS BASE ---
    public String obtenerNombre() { return nombre; }
    public int obtenerVidaActual() { return obtenerValorAtributo(Atributo.VIDA); }
    public double obtenerPosicionX() { return navegacion.getX(); }
    public double obtenerPosicionY() { return navegacion.getY(); }
    public double obtenerPosicionZ() { return navegacion.getZ(); }
    public void establecerValorAtributo(Atributo a, int v) { estadisticas.put(a, v); }
    public int obtenerValorAtributo(Atributo a) { return estadisticas.getOrDefault(a, 0); }
    public Funcion obtenerFuncionActual() { return funcionActual; }



}
