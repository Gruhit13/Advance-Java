import javax.persistence.*;

@Entity
@Table(name="Proffesor")
@PrimaryKeyJoinColumn(name="ID")
public class Proffesor extends Person{

	@Column(name="subject")
	private String subject;

	public void setSubject(String subject){
		this.subject = subject;
	}

	public String getSubject(){
		return this.subject;
	}
}