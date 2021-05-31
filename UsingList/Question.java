import java.util.List;

class Question{
	private int id;
	private String qname;
	private List<String> answers;

	public void setId(int id){
		this.id = id;
	}

	public void setQname(String qname){
		this.qname = qname;
	}

	public void setAnswers(List<String> answers){
		this.answers = answers;
	}

	public int getId(){
		return this.id;
	}

	public String getQname(){
		return this.qname;
	}

	public List<String> getAnswers(){
		return this.answers;
	}

}