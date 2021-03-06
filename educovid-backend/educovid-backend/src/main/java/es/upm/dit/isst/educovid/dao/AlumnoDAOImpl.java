package es.upm.dit.isst.educovid.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;

public class AlumnoDAOImpl implements AlumnoDAO {
	private static AlumnoDAOImpl instance = null;

	private AlumnoDAOImpl() {
	}

	public static AlumnoDAOImpl getInstance() {
		if (null == instance)
			instance = new AlumnoDAOImpl();
		return instance;
	}

	public String getNombreCentro(Alumno alumno) {
		for (CentroEducativo centro : CentroEducativoDAOImpl.getInstance().readAllCentroEducativo()) {
			for (Clase clase : centro.getClases()) {
				for (GrupoBurbuja grupo : clase.getGruposBurbuja()) {
					for (Alumno alumnoTest : grupo.getAlumnos()) {
						if (alumno.getId() == alumnoTest.getId()) {
							return centro.getNombre();
						}
					}
				}
			}
		}
		return "";
	}

	@Override
	public Alumno createAlumno(Alumno alumno) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		try {
			session.save(alumno);
		} catch (Exception e) {
			alumno = null;
		}
		session.getTransaction().commit();
		session.close();
		return alumno;
	}

	@Override
	public Alumno readAlumnobyMatNumCenter(String numeroMatricula, String centro) {
		List<Alumno> alumnos = this.readAllAlumnos();
		System.out.println(alumnos.toString());
		System.out.println("Centro received: " + centro.trim().toLowerCase());
		for (Alumno alumno : alumnos) {
			String centroAlumno = this.getNombreCentro(alumno);
			System.out.println(centroAlumno);
			System.out.println("A '" + alumno.getNumeroMatricula() + "'");
			System.out.println("B '" + numeroMatricula + "'");
			if (alumno.getNumeroMatricula().equals(numeroMatricula)
					&& centroAlumno.trim().toLowerCase().equals(centro.trim().toLowerCase())) {
				System.out.println("Alumno encontrado: " + alumno.getNombre());
				return alumno;
			}
		}
		return null;
	}

	@Override
	public Alumno readAlumnobyId(String id) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		Alumno alumno = session.get(Alumno.class, Long.parseLong(id));
		session.getTransaction().commit();
		session.close();
		return alumno;
	}

	@Override
	public Alumno updateAlumno(Alumno Alumno) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.saveOrUpdate(Alumno);
		session.getTransaction().commit();
		session.close();
		return Alumno;
	}

	@Override
	public Alumno updateAlumnobyEstadoSanitario(String estadoSanitario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alumno updateAlumnobyFechaConfinamiento(Date fechaConfinamiento) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alumno updateAlumnobyGrupo(String grupoBurbuja) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Alumno deleteAlumno(Alumno alumno) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.delete(alumno);
		session.getTransaction().commit();
		session.close();
		return alumno;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Alumno> readAllAlumnos() {
		List<Alumno> alumnos = new ArrayList<Alumno>();
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		alumnos.addAll(session.createQuery("from Alumno").list());
		session.getTransaction().commit();
		session.close();
		return alumnos;
	}
	
	public List<Alumno> readAllAlumnos(String nombreCentro) {
		List<Alumno> alumnosCentro = new ArrayList<Alumno>();
		CentroEducativo centro = CentroEducativoDAOImpl.getInstance().readCentroEducativobyName(nombreCentro);
		List<GrupoBurbuja> gruposCentro = GrupoBurbujaDAOImpl.getInstance().readAllGruposBurbujabyCentro(nombreCentro);
		for (GrupoBurbuja g : gruposCentro) {
			for (Alumno a : g.getAlumnos()) {
				alumnosCentro.add(a);
			}
		}
		return alumnosCentro;
	}
	
	public List<Alumno> readAllAlumnosByClase(Clase c) {
		List<Alumno> alumnosCentro = new ArrayList<Alumno>();
		List<GrupoBurbuja> gruposClase = c.getGruposBurbuja();
		for (GrupoBurbuja g : gruposClase) {
			for (Alumno a : g.getAlumnos()) {
				alumnosCentro.add(a);
			}
		}
		return alumnosCentro;
	}
	

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Alumno> readAllAlumnosbyGroup(String grupoBurbuja) {
//		List<Alumno> alumnos = new ArrayList<Alumno>();
//		Session session = SessionFactoryService.get().openSession();
//		session.beginTransaction();
//		alumnos.addAll(session.createQuery("from alumnos a where alumnos.grupoBurbuja=" + grupoBurbuja).list());
//		session.getTransaction().commit();
//		session.close();
//		return alumnos;
//	}

//	@Override
//	public List<Alumno> readAllAlumnosbyEstadoSanitario(String estadoSanitario) {
//		List<Alumno> alumnos = new ArrayList<Alumno>();
//		Session session = SessionFactoryService.get().openSession();
//		session.beginTransaction();
//		alumnos.addAll(session.createQuery("from alumnos a where alumnos.estadoSanitario=" + estadoSanitario).list());
//		session.getTransaction().commit();
//		session.close();
//		return alumnos;
//	}

//	@Override
//	public List<Alumno> readAllAlumnosbyFechaConfinamiento(Date fechaConfinamiento) {
//		List<Alumno> alumnos = new ArrayList<Alumno>();
//		Session session = SessionFactoryService.get().openSession();
//		session.beginTransaction();
//		alumnos.addAll(
//				session.createQuery("from alumnos a where alumnos.fechaConfinamiento=" + fechaConfinamiento).list());
//		session.getTransaction().commit();
//		session.close();
//		return alumnos;
//	}

}
