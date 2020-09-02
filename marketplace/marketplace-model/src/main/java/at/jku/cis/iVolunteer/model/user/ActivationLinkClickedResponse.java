package at.jku.cis.iVolunteer.model.user;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ActivationLinkClickedResponse {

	private PendingActivation pendingActivation;
	private ActivationResponse activationResponse;
	
	public ActivationLinkClickedResponse() {}
	public ActivationLinkClickedResponse(PendingActivation pendingActivation, ActivationResponse activationResponse) {
		this.pendingActivation = pendingActivation;
		this.activationResponse = activationResponse;
	}
	
	public PendingActivation getPendingActivation() {
		return pendingActivation;
	}
	public void setPendingActivation(PendingActivation pendingActivation) {
		this.pendingActivation = pendingActivation;
	}
	public ActivationResponse getActivationResponse() {
		return activationResponse;
	}
	public void setActivationResponse(ActivationResponse activationResponse) {
		this.activationResponse = activationResponse;
	}
	
	
	

	
	
	
}
