package com.herramientas.tiempo;

public final class CalendarioLunar {
	private int diaMes = 1; // 1 a 28
    private int indiceMes = 0; // Para recorrer el Enum Mes
    private int indiceDiaSemana = 0; // Para recorrer el Enum DiaSemana
    private int anio = 1;

    public void avanzarDia() {
        // Lógica de los días de la semana
        indiceDiaSemana = (indiceDiaSemana + 1) % DiaSemana.values().length;
        
        diaMes++;
        if (diaMes > 28) {
            diaMes = 1;
            avanzarMes();
        }
    }

    private void avanzarMes() {
        indiceMes++;
        if (indiceMes >= Mes.values().length) {
            indiceMes = 0;
            anio++;
        }
    }
/*
    public void mostrarFecha() {
        Mes mesActual = Mes.values()[indiceMes];
        DiaSemana diaActual = DiaSemana.values()[indiceDiaSemana];
        
        String log = String.format("[%s] Día %d de %s (%s) - Año %d", 
            diaActual, diaMes, mesActual, mesActual.obtenerEstacion(), anio);
            
        herramientas.texto.Narrador.obtenerInstancia().narrar(log, 20);
    }
    */
   
    
    public String obtenerFechaFormateada() { // Cambiamos el nombre para que sea más descriptivo
        Mes mesActual = Mes.values()[indiceMes];
        DiaSemana diaActual = DiaSemana.values()[indiceDiaSemana];
        
        // Retornamos el String en lugar de llamar al Narrador aquí dentro
        return String.format("[%s] Día %d de %s (%s) - Año %d", 
            diaActual, diaMes, mesActual, mesActual.obtenerEstacion(), anio);
    }
}