import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class StoreData{
	public static void main(String arg[]){
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();

		System.out.println("LOG: SSR and Meta created Successfully");
		SessionFactory factory = meta.getSessionFactoryBuilder().build();

		Session session = factory.openSession();
		Transaction transaction = session.beginTransaction();	

		System.out.println("LOG: Transaction created Successfully");

		Student s1 = new Student();
		s1.setId("18IT007");
		s1.setName("Mystery Trivedi");
		s1.setAge(99);

		session.save(s1);
		transaction.commit();

		System.out.println("Data stored Successfully");
		factory.close();
		session.close();

	}
}