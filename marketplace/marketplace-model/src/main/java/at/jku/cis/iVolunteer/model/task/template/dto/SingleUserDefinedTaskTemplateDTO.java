package at.jku.cis.iVolunteer.model.task.template.dto;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.property.Property;
import at.jku.cis.iVolunteer.model.property.SingleProperty;
import at.jku.cis.iVolunteer.model.property.dto.PropertyDTO;

/**
 * @author alexander
 *
 */
@Document
public class SingleUserDefinedTaskTemplateDTO {
	
	String id;
	String name;
	String description;
	
	String kind;
	
	List<PropertyDTO<Object>> properties;
	
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
	
	public void setKind(String kind) {
		this.kind = kind;
	}
	
	public String getKind() {
		return kind;
	}


	public List<PropertyDTO<Object>> getProperties() {
		return properties;
	}

	public void setProperties(List<PropertyDTO<Object>> properties) {
		this.properties = properties;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof SingleUserDefinedTaskTemplateDTO)) {
			return false;
		}
		return ((SingleUserDefinedTaskTemplateDTO) obj).id.equals(id);
	}
	
	
	
	
}
