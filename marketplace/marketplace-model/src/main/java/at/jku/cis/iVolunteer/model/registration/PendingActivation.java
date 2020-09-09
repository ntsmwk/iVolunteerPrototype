package at.jku.cis.iVolunteer.model.registration;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PendingActivation {

	@Id
	private String activationId;
	private String userId;
	private String email;
	private AccountType accountType;
	private Date timestamp;
	
	public PendingActivation() {}
	public PendingActivation(String activationId, String userId, String email, AccountType accountType) {
		this.activationId = activationId;
		this.userId = userId;
		this.email = email;
		this.timestamp = new Date();
		this.accountType = accountType;
	}
	
	public String getActivationId() {
		return activationId;
	}
	
	public void setActivationId(String activationId) {
		this.activationId = activationId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (!(obj instanceof PendingActivation)) {
			return false;
		}
		return ((PendingActivation) obj).activationId.equals(activationId);
	}

	@Override
	public int hashCode() {
		return activationId.hashCode();
	}

	
	
	
}
