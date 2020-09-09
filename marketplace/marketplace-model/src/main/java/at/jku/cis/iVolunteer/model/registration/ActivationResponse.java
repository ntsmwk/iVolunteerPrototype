package at.jku.cis.iVolunteer.model.registration;

import com.fasterxml.jackson.annotation.JsonCreator;


public enum ActivationResponse {
	SUCCESS("SUCCESS"), EXPIRED("EXPIRED"), FAILED("FAILED");
	
	private final String response;

	private ActivationResponse(String response) {
		this.response = response;
	}

	public String getResponse() {
		return this.response;
	}

	@Override
	public String toString() {
		return response;
	}

	@JsonCreator
	public static ActivationResponse getFromClassArchetype(String type) {
		for (ActivationResponse r : ActivationResponse.values()) {
			if (r.getResponse().equals(type)) {
				return r;
			}
		}
		throw new IllegalArgumentException();
	}
}
