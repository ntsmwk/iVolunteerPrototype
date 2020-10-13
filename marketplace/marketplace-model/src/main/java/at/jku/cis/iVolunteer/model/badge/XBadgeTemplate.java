package at.jku.cis.iVolunteer.model.badge;

import at.jku.cis.iVolunteer.model.core.tenant.XTenant;

public class XBadgeTemplate {

	// TODO: trigger/evidence --> SPÄTER ÜBERLEGEN WIE DAS GEHANDHABT WANN BADGE AUSGESTELLT WIRD zb 5 mal geleichen typ
	// Oder min 10 km gefahren oder sowas
	String id;
	XTenant tenant;
	String name;
	String description;
	String imagePath;
	
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
