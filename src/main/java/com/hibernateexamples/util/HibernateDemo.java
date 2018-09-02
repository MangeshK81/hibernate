package com.hibernateexamples.util;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;

import com.hibernateexamples.entities.Address;
import com.hibernateexamples.entities.Employee;

public class HibernateDemo {

	public static void main(String[] args) throws SQLException {


		final Session session = HibernateUtil.getSessionFactory().openSession();
		final List<Employee> resultList = session.createQuery("from Employee ", Employee.class).getResultList();

		System.out.println(resultList);

		List<Address> address = session.createQuery("from Address ", Address.class).getResultList();
		System.out.println(address.stream());
	}
}
