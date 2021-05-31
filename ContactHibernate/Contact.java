public class Contact{
	private String id, firstName, lastName, eMail;

	//	Setter methods
	public void setId(String id){
		this.id = id;
	}
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	public void seteMail(String eMail){
		this.eMail = eMail;
	}

	//	Getter methos
	public String getid(){
		return this.id;
	}
	public String getfirstName(){
		return this.firstName;
	}
	public String getlastName(){
		return this.lastName;
	}
	public String geteMail(){
		return this.eMail;
	}
}