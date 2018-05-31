package ua.rabota.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import ua.rabota.model.Vacancy;

public class HibernateUtil {

	private static SessionFactory sessionFactory = configureSessionFactory();
	
	private static SessionFactory configureSessionFactory() throws HibernateException {
		
		Configuration configuration = new Configuration();
		
		configuration.addAnnotatedClass(Vacancy.class);
		
		return configuration.buildSessionFactory();
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}
