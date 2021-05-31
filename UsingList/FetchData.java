import java.util.List;

import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

class FetchData{
	public static void main(String arg[]){
		Session s = new Configuration().configure("hibernate.cfg.xml")
							.buildSessionFactory().openSession();

							
		Query query = s.createQuery("from Question");

		List<Question> qs = query.list();

		for(Question q : qs){
			System.out.println("Question " + q.getId() + ": " + q.getQname());
			System.out.println("Answers");
			for(String ans: q.getAnswers()){
				System.out.println(ans);
			}
		}

		s.close();
	}
}