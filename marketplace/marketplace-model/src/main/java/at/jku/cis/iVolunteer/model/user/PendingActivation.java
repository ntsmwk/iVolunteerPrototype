package at.jku.cis.iVolunteer.model.user;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PendingActivation {

	@Id
	private String activationId;
	private String userId;
	private Date timestamp;
	
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
