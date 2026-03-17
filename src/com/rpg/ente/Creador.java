package com.rpg.ente;

import java.util.concurrent.atomic.AtomicInteger;

public final class Creador { private static final AtomicInteger ContadorDeIdentidad = new AtomicInteger(0);
		public Ente crearNuevoEnte(String nombre) 
		{ return new Ente(ContadorDeIdentidad.incrementAndGet(),nombre); } }
