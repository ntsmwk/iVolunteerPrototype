package at.jku.cis.iVolunteer.model.badge;

import at.jku.cis.iVolunteer.model.core.tenant.XTenant;

public class XBadgeTemplateDTO {

	private String id;
	private XTenant tenant;
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

	public XTenant getTenant() {
		return tenant;
	}

	public void setTenant(XTenant tenant) {
		this.tenant = tenant;
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

	
	
}
