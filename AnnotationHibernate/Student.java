import javax.persistence.*;

@Entity
@Table(name="Student")
@PrimaryKeyJoinColumn(name="ID")
public class Student extends Person{

	@Column(name="branch")
	private String branch;

	public void setBranch(String branch){
		this.branch = branch;
	}

	public String getBranch(){
		return this.branch;
	}
}