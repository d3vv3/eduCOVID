package es.upm.dit.isst.educovid.dao;

import org.hibernate.Session;

public class CreateTables {

	public static void main(String[] args) {
		Session session = SessionFactoryService.get().openSession();		
		session.close();
	}

}
