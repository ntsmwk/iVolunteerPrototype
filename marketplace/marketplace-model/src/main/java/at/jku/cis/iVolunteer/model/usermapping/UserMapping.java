package at.jku.cis.iVolunteer.model.usermapping;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserMapping {

	private String iVolunteerUserId;
	private String externalUserId;

	public UserMapping() {
	}

	public String getExternalUserId() {
		return externalUserId;
	}

	public void setExternalUserId(String externalUserId) {
		this.externalUserId = externalUserId;
	}

	public String getiVolunteerUserId() {
		return iVolunteerUserId;
	}

	public void setiVolunteerUserId(String iVolunteerUserId) {
		this.iVolunteerUserId = iVolunteerUserId;
	}
}