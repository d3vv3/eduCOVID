package es.upm.dit.isst.educovid.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;

import es.upm.dit.isst.educovid.model.Alumno;
import es.upm.dit.isst.educovid.model.CentroEducativo;
import es.upm.dit.isst.educovid.model.Clase;
import es.upm.dit.isst.educovid.model.GrupoBurbuja;
import es.upm.dit.isst.educovid.model.ResponsableCOVID;

public class CentroEducativoDAOImpl implements CentroEducativoDAO {
	private static CentroEducativoDAOImpl instance = null;

	private CentroEducativoDAOImpl() {
	}

	public static CentroEducativoDAOImpl getInstance() {
		if (null == instance)
			instance = new CentroEducativoDAOImpl();
		return instance;
	}

	@Override
	public CentroEducativo createCentroEducativo(CentroEducativo centroEducativo) {
		// Validate:
		// 1. Numero de matricula unique per alumno per center
		// 2. Alumno unique per Grupo Burbuja
		Set<String> numsMatricula = new HashSet<String>();
		Set<Integer> idAlumnoGruposBurbuja = new HashSet<Integer>();
		List<Clase> clases = centroEducativo.getClases();
		for (Clase clase : clases) {
			for (GrupoBurbuja grupo : clase.getGruposBurbuja()) {
				for (Alumno alumno : grupo.getAlumnos()) {
					if (numsMatricula.contains(alumno.getNumeroMatricula())) {
						System.out.println("There cannot be two Alumno with the same numeroMatricula");
						return null;
					} else {
						numsMatricula.add(alumno.getNumeroMatricula());
					}
					if (idAlumnoGruposBurbuja.contains(alumno.getId())) {
						System.out.println("The same Alumno cannot be in two GrupoBurbuja");
						return null;
					}
				}
			}
		}
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		try {
			session.save(centroEducativo);
		} catch (Exception e) {
			centroEducativo = null;
		}
		session.getTransaction().commit();
		session.close();
		return centroEducativo;
	}

	@Override
	public CentroEducativo readCentroEducativobyName(String nombre) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		CentroEducativo centro = null;
		Object object = session.createQuery("from CentroEducativo c where c.nombre='" + nombre.replace("'", "''") + "'")
				.uniqueResult();
		if (object != null)
			centro = (CentroEducativo) object;
		session.getTransaction().commit();
		session.close();
		return centro;
	}

	@Override
	public CentroEducativo readCentroEducativobyId(String id) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		CentroEducativo centroEducativo = session.get(CentroEducativo.class, Integer.parseInt(id));
		session.getTransaction().commit();
		session.close();
		return centroEducativo;
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<CentroEducativo> readAllCentroEducativobyResponsable(ResponsableCOVID responsable) {
//		List<CentroEducativo> centros = new ArrayList<CentroEducativo> ();
//        Session session = SessionFactoryService.get().openSession();
//        session.beginTransaction();
//        centros.addAll(session.createQuery("from centros_educativos where centros_educativos.ResponsableCOVID="+ responsable).list());
//        session.getTransaction().commit();
//        session.close();
//        return centros;
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CentroEducativo> readAllCentroEducativo() {
		List<CentroEducativo> centros = new ArrayList<CentroEducativo>();
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		centros.addAll(session.createQuery("from CentroEducativo").list());
		session.getTransaction().commit();
		session.close();
		return centros;
	}

	@Override
	public CentroEducativo updateCentroEducativo(CentroEducativo centroEducativo) {
		// Validate:
		// 1. Numero de matricula unique per alumno per center
		// 2. Alumno unique per Grupo Burbuja
		Set<String> numsMatricula = new HashSet<String>();
		Set<Integer> idAlumnoGruposBurbuja = new HashSet<Integer>();
		List<Clase> clases = centroEducativo.getClases();
		for (Clase clase : clases) {
			for (GrupoBurbuja grupo : clase.getGruposBurbuja()) {
				for (Alumno alumno : grupo.getAlumnos()) {
					if (numsMatricula.contains(alumno.getNumeroMatricula())) {
						System.out.println("There cannot be two Alumno with the same numeroMatricula");
						return null;
					} else {
						numsMatricula.add(alumno.getNumeroMatricula());
					}
					if (idAlumnoGruposBurbuja.contains(alumno.getId())) {
						System.out.println("The same Alumno cannot be in two GrupoBurbuja");
						return null;
					}
				}
			}
		}
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		try {
			session.saveOrUpdate(centroEducativo);
		} catch (Exception e) {
			centroEducativo = null;
		}
		session.getTransaction().commit();
		session.close();
		return centroEducativo;
	}

	@Override
	public CentroEducativo updateCentroEducativobyResponsable(ResponsableCOVID responsable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CentroEducativo deleteCentroEducativo(CentroEducativo centroEducativo) {
		Session session = SessionFactoryService.get().openSession();
		session.beginTransaction();
		session.delete(centroEducativo);
		session.getTransaction().commit();
		session.close();
		return centroEducativo;
	}

}