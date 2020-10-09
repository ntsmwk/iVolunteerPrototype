package at.jku.cis.iVolunteer.model.badge;

import at.jku.cis.iVolunteer.model.core.tenant.XTenantSerialized;

public class XBadgeCertificate {

	String id;
	String userId;
	XTenantSerialized tenantSerialized;
	XBadgeSerialized badgeSerialized;
	
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
	
	
}
