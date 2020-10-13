package at.jku.cis.iVolunteer.model._httpresponses;

public class RegisterResponseMessage {
	RegisterResponse response;
	String message;

	public RegisterResponseMessage(RegisterResponse response, String message) {
		this.response = response;
		this.message = message;
	}

	public RegisterResponse getResponse() {
		return response;
	}

	public void setResponse(RegisterResponse response) {
		this.response = response;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}