package at.jku.cis.iVolunteer.model.configurable.class_;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.configurable.ConfigurableObject;

@Document
public class ConfigurableClass {

	@Id
	public String id;
	
	List<ConfigurableObject> configurables;
	
	public ConfigurableClass() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<ConfigurableObject> getConfigurables() {
		return configurables;
	}

	public void setConfigurables(List<ConfigurableObject> configurables) {
		this.configurables = configurables;
	}
	
	
	
}
