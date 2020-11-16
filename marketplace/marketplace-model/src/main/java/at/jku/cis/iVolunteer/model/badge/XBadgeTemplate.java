package at.jku.cis.iVolunteer.model.badge;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.core.tenant.XTenant;


@Document
public class XBadgeTemplate {

	@Id private String id;
	private XTenant tenant;
	private String name;
	private String description;
	private String imagePath;

	public XBadgeTemplate() {
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

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
