package es.upm.dit.isst.educovid.aux;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class TimerPresencialidad {
	
	final static int horasComprobacion = 24;
	
	private boolean iniciado = false;
	
	private Temporizador task = new Temporizador();
	
	private static TimerPresencialidad instance = null;

	private TimerPresencialidad() {
	}

	public static TimerPresencialidad getInstance() {
		if (null == instance)
			instance = new TimerPresencialidad();
		return instance;
	}

	public void iniciarTemporizador() {
		
		if (iniciado)
			return;
		
		iniciado = true;
		
		// Guardo la fecha y hora actual
		Date horaComprobacion = new Date(System.currentTimeMillis());
		
		System.out.println("Fecha actual: " + horaComprobacion);
		
		// Convierto la fecha actual en un Calendar
		Calendar c = Calendar.getInstance();
		c.setTime(horaComprobacion);
		
		// Si la hora actual es las 9 PM o mas tarde, pongo la fecha de mañana
		if (c.get(Calendar.HOUR_OF_DAY) >= 21) {
            c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) + 1);
        }
		
		// Pongo la hora de las 21:00:00.000
		c.set(Calendar.HOUR_OF_DAY, 16);
		c.set(Calendar.MINUTE, 17);
		c.set(Calendar.SECOND, 0);
		
		// Pongo la hora de comprobacion seteada a las siguientes 9 PM en el objeto tipo Date de nuevo
		horaComprobacion = c.getTime();
		
		System.out.println("Fecha actual con hora inicio: " + horaComprobacion);
		
		// Creo tiempoRepetición con los milisegundos que contiene un dia
		int tiempoRepeticion = horasComprobacion*60*60*1000;
		
		// Creo el timer con el temporizador creado en la clase Temporizador, con la hora de comprobacion que hemos
		// creado antes y pongo la repeticion cada 24 horas
		Timer temporizador = new Timer();
		temporizador.schedule(task, horaComprobacion, 2*60*1000);
		System.out.println("Temporizador inicializado.");
	}

}
