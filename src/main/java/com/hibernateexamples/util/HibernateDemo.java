package com.hibernateexamples.util;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.hibernateexamples.entities.Address;
import com.hibernateexamples.entities.Employee;

public class HibernateDemo {

	static Session sessionObj;
	static SessionFactory sessionFactoryObj;

	private static SessionFactory buildSessionFactory() {
		// Creating Configuration Instance & Passing Hibernate Configuration
		// File
		Configuration configObj = new Configuration();
		configObj.configure("hibernate.cfg.xml");

		// Since Hibernate Version 4.x, ServiceRegistry Is Being Used but it has issuess working with standard way : throws Unknown entity
		ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder()
				.applySettings(configObj.getProperties()).build();

		
		// Creating Hibernate SessionFactory Instance
		sessionFactoryObj = configObj.buildSessionFactory();
		return sessionFactoryObj;
	}

	public static void main(String[] args) throws SQLException {


		final Session session = HibernateUtil.getSessionFactory().openSession();
		final List<Employee> resultList = session.createQuery("from Employee ", Employee.class).getResultList();

		System.out.println(resultList);

		List<Address> address = session.createQuery("from Address ", Address.class).getResultList();
		System.out.println(address.get(0).toString());
		
		
		try {
			// STEP 1: Getting Session Object From SessionFactory
			sessionObj = buildSessionFactory().openSession();
			// STEP 2: Getting Transaction Object From Session Object
			sessionObj.beginTransaction();
			
			Address addressTemp = new Address();
			List<Address> tempAddress = new ArrayList<>();
			tempAddress.add(addressTemp);
			
			Employee newJoiner = new Employee();
			newJoiner.seteName("Test name");
			
			newJoiner.setAddress(tempAddress);
			
			 //STEP 3:
			sessionObj.save(newJoiner);
			
			
			// STEP 4: Committing The Transactions To The Database
						sessionObj.getTransaction().commit();
						
			
		} catch (Exception sqlException) {
			if(null != sessionObj.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......\n");
				sessionObj.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		} finally {
			if(sessionObj != null) {
				sessionObj.close();
			}

		}
		final List<Employee> resultListEmp = session.createQuery("from Employee ", Employee.class).getResultList();
		System.out.println(resultListEmp.get(resultListEmp.size()-1).toString());
	}
	
	
	
}
