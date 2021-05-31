import javax.persistence.*;

@Entity
@Table(name="person")
@Inheritance(strategy=InheritanceType.JOINED)

public class Person{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private int id;

	@Column(name="name")
	private String name;

	public void setId(int id){
		this.id = id;
	}
	public void setName(String name){
		this.name = name;
	}

	public int getId(){
		return this.id;
	}

	public String getName(){
		return this.name;
	}
}