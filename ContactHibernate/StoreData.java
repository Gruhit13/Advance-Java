import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.cfg.Configuration;
import java.util.Scanner;

public class StoreData{
	public static void main(String arg[]){
		Session session = new Configuration().configure("hibernate.cfg.xml")
							.buildSessionFactory().openSession();

		Transaction t = session.beginTransaction();

		System.out.println("LOG: All requirement satisfied Successfully");

		int n_contact;
		Scanner in = new Scanner(System.in);
		System.out.print("Enter total number of Contanct to add: "); n_contact = in.nextInt();
		in.nextLine();

		for(int i = 0; i < n_contact; i++){
			System.out.println("\n<----------Enter Data no " + (i+1) + "---------->");
			Contact ct = new Contact();

			System.out.print("Enter ID: "); ct.setId(in.nextLine());
			System.out.print("Enter First Name: "); ct.setFirstName(in.nextLine());
			System.out.print("Enter Last Name: "); ct.setLastName(in.nextLine());
			System.out.print("Enter E-mail id: "); ct.seteMail(in.nextLine());

			session.save(ct);
			session.flush();
		}

		t.commit();

		System.out.println("All data stored Successfully");
		session.close();

	}
}