package at.jku.cis.iVolunteer.model.task.template;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.property.Property;

/**
 * @author alexander
 *
 */
@Document
public class UserDefinedTaskTemplate {

	@Id
	String id;
	String name;
	
	List<Property<?>> properties;
	
	public UserDefinedTaskTemplate() {}
	
	public UserDefinedTaskTemplate(String id) {
		this.id = id;
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
	
	public List<Property<?>> getProperties() {
		return properties;
	}

	public void setProperties(List< Property<?>> properties) {
		this.properties = properties;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UserDefinedTaskTemplate)) {
			return false;
		}
		return ((UserDefinedTaskTemplate) obj).id.equals(id);
	}
	
	
	
	
}
