package at.jku.cis.iVolunteer.model.badge;

import at.jku.cis.iVolunteer.model.core.tenant.XTenant;

public class XBadgeTemplateDTO {

	private String id;
	private String tenantId;
	private String name;
	private String description;
	private String image;
	
	public XBadgeTemplateDTO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	
	
}
