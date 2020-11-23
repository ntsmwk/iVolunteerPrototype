package at.jku.cis.iVolunteer.model.badge;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.core.tenant.XTenantSerialized;

@Document
public class XBadgeCertificate {

	@Id String id;
	String userId;
	XTenantSerialized tenantSerialized;
	XBadgeSerialized badgeSerialized;
	private Date issueDate;
	
	public XBadgeCertificate() {
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

	public XTenantSerialized getTenantSerialized() {
		return tenantSerialized;
	}

	public void setTenantSerialized(XTenantSerialized tenantSerialized) {
		this.tenantSerialized = tenantSerialized;
	}

	public XBadgeSerialized getBadgeSerialized() {
		return badgeSerialized;
	}

	public void setBadgeSerialized(XBadgeSerialized badgeSerialized) {
		this.badgeSerialized = badgeSerialized;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

}
