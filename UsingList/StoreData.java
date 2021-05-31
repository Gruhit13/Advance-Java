import java.util.ArrayList;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

class StoreData {
	public static void main(String arg[]){
		Session session = new Configuration().configure("hibernate.cfg.xml")
							.buildSessionFactory().openSession();

		Transaction t = session.beginTransaction();

		ArrayList<String> arr1 = new ArrayList<String>();
		arr1.add("Answer1 to question 1");
		arr1.add("Answer2 to question 1");
		arr1.add("Answer3 to question 1");

		Question q1 = new Question();
		q1.setQname("Question1");
		q1.setAnswers(arr1);

		ArrayList<String> arr2 = new ArrayList<String>();
		arr2.add("Answer1 to question 2");
		arr2.add("Answer2 to question 2");
		arr2.add("Answer3 to question 2");
		arr2.add("Answer4 to question 2");

		Question q2 = new Question();
		q2.setQname("Question2");
		q2.setAnswers(arr2);

		session.save(q1);
		session.save(q2);

		System.out.println("All Data Stored Successful");
		t.commit();
		session.close();
	}
}