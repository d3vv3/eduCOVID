package es.upm.dit.isst.educovid.aux;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import es.upm.dit.isst.educovid.dao.ClaseDAOImpl;
import es.upm.dit.isst.educovid.model.Clase;

public class Temporizador extends TimerTask {
	
	final static int milisegundosPorDia = 24*60*60*1000;

	@Override
	public void run() {
		
		System.out.println("Comprobación de rotación en curso");
		
		List<Clase> clases = ClaseDAOImpl.getInstance().readAllClases();
		
		// Obtengo la fecha actual y pongo la hora a las 21:00:00.000 para compararlo en días enteros con la fecha de primer cambio
		Date fechaActual = new Date(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaActual);
		cal.set(Calendar.HOUR_OF_DAY, 21);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		fechaActual = cal.getTime();
		
		// Para cada clase, compruebo si hay que actualizar su presencialidad
		for (Clase c: clases) {
			
			System.out.println("Comprobando clase: " + c.getNombre());
			
			// Si la clase solo tiene un grupo (o no tuviese grupos) no hacemos comprobaciones ya que no habra rotaciones
			if (c.getGruposBurbuja().size() <= 1) {
				System.out.println("Solo hay un grupo");
				continue;
			}
			
			// Obtengo la fecha de inicio de la conmutacion
			java.sql.Date fechaPrimerCambioSQL = c.getFechaInicioConmutacion();
			
			// Creo instancia de calendario con la fecha del primer cambio y pongo la hora a las 21:00:00.000 para compararlo en días enteros
			// con la fecha actual
			Date fechaPrimerCambio;
			cal.setTimeInMillis(fechaPrimerCambioSQL.getTime());
			cal.set(Calendar.HOUR_OF_DAY, 21);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			fechaPrimerCambio = cal.getTime();
			
			// Calculo el tiempo pasado de una fecha a otra en dias
			int diasPasados = (int) ( (fechaActual.getTime() - fechaPrimerCambio.getTime()) / milisegundosPorDia);
			
			// Si han pasado tantos dias como indica el tiempo de conmutacion o un multiplo de este, hacemos que cambie la presencialidad
			// al siguiente grupo que le toque
			if ((diasPasados % c.getTiempoConmutacion()) == 0) {
				ClaseDAOImpl.getInstance().updatePresencialGroup(c);
			}
		}
		System.out.println("Comprobación de rotación terminada.");
	}
	
}