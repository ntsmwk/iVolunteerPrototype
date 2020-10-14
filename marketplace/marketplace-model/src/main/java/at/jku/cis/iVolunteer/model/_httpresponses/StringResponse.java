package at.jku.cis.iVolunteer.model._httpresponses;

public class StringResponse {

	private String message;
	
	public StringResponse() {
	}
	
	public StringResponse(String message){
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
