package at.jku.cis.iVolunteer.model.configurable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class ConfigurableObject {
	
	@Id
	protected String id;
	String configurableType;
	
	

	public String getId() {
		return this.id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getConfigurableType() {
		return configurableType;
	}
	
	public void setConfigurableType(String configurableType) {
		this.configurableType = configurableType;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ConfigurableObject)) {
			return false;
		}
		return ((ConfigurableObject) obj).id.equals(id);
	}
		
	
}
