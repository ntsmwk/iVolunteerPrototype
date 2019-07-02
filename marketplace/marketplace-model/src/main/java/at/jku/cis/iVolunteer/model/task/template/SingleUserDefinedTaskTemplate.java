package at.jku.cis.iVolunteer.model.task.template;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.SingleProperty;

/**
 * @author alexander
 *
 */
@Document
public class SingleUserDefinedTaskTemplate extends UserDefinedTaskTemplate {
	
//	List<Property> properties;
	
	public SingleUserDefinedTaskTemplate() {
	}
	
	public SingleUserDefinedTaskTemplate(UserDefinedTaskTemplate template) {
		super();
		this.id = template.id;
		this.name = template.name;
		this.description = template.description;
	}
	
	public SingleUserDefinedTaskTemplate(String id) {
		super();
		this.id = id;
	}

	public List<Property> getProperties() {
		return properties;
	}

	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SingleUserDefinedTaskTemplate)) {
			return false;
		}
		return ((SingleUserDefinedTaskTemplate) obj).id.equals(id);
	}
	
	
	
	
}
