package at.jku.cis.iVolunteer.model.registration;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.core.user.CoreUser;

@Document
public class ActivationLinkClickedResponse {

	private PendingActivation pendingActivation;
	private ActivationResponse activationResponse;
	private CoreUser user;
	
	public ActivationLinkClickedResponse() {}
	public ActivationLinkClickedResponse(PendingActivation pendingActivation, ActivationResponse activationResponse, CoreUser user) {
		this.pendingActivation = pendingActivation;
		this.activationResponse = activationResponse;
		this.user = user;
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
	public CoreUser getUser() {
		return user;
	}
	public void setUser(CoreUser user) {
		this.user = user;
	}
	
	
	
	

	
	
	
}
