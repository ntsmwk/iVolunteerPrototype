package at.jku.cis.iVolunteer.model.iVolunteerObject.dto;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.core.property.dtos.PropertyInstanceDTO;

/**
 * @author alexander
 *
 */

public class IVolunteerObjectDTO {

	public String id;
	public List<PropertyInstanceDTO<Object>> propertyInstances;	
	
	
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

	public List<PropertyInstanceDTO<Object>> getPropertyInstances() {
		return propertyInstances;
	}
	
	public void setName(List<PropertyInstanceDTO<Object>> propertyInstances) {
		this.propertyInstances = propertyInstances;
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
