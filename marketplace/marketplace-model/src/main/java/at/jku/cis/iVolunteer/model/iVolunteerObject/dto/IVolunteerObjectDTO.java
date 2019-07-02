package at.jku.cis.iVolunteer.model.iVolunteerObject.dto;

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
public class IVolunteerObjectDTO {

	@Id
	public String id;
	public List<Property> properties;	
	
	
	public IVolunteerObjectDTO() {
	}
	
	public IVolunteerObjectDTO(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Property> getProperties() {
		return properties;
	}
	
	public void setName(List<Property> properties) {
		this.properties = properties;
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IVolunteerObjectDTO)) {
			return false;
		}
		return ((IVolunteerObjectDTO) obj).id.equals(id);
	}

	
	
	
	
	
	
}