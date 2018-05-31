package ua.rabota.dao;

import org.hibernate.Session;


import ua.rabota.model.Vacancy;
import ua.rabota.util.HibernateUtil;

public class VacancyDAO {

	private Session session;
	
	public synchronized void save(Vacancy vacancy) {
		
		session = HibernateUtil.getSessionFactory().openSession();

		session.getTransaction().begin();

		session.save(vacancy);

		session.getTransaction().commit();

		session.close();
	}
}
