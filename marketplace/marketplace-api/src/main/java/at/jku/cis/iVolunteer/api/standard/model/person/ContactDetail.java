package at.jku.cis.iVolunteer.api.standard.model.person;


public class ContactDetail {
	
	private String type;
	private String content;

	public ContactDetail() {
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
