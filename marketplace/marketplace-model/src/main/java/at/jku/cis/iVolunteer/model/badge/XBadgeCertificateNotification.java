package at.jku.cis.iVolunteer.model.badge;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class XBadgeCertificateNotification {

	@Id private String id;
	private String userId;
	private String badgeCertificateId;

	public XBadgeCertificateNotification() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBadgeCertificateId() {
		return badgeCertificateId;
	}

	public void setBadgeCertificateId(String badgeCertificateId) {
		this.badgeCertificateId = badgeCertificateId;
	}
}
