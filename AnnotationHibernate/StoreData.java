import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;

public class StoreData{

	public static void main(String argp[]){
		Session session = new Configuration().configure("hibernate.cfg.xml")
								.buildSessionFactory().openSession();
		Transaction t = session.beginTransaction();

		//	Scanner inp = new Scanner(System.in);

		Person p = new Person();
		p.setName("Modi");

		Student s = new Student();
		s.setName("Salmon bhai");
		s.setBranch("IT");

		Proffesor prf = new Proffesor();
		prf.setName("Elon musk");
		prf.setSubject("JAVA");

		session.persist(p);
		session.persist(s);
		session.persist(prf);

		t.commit();
		System.out.println("All record stored successful");

		session.close();
	}
}