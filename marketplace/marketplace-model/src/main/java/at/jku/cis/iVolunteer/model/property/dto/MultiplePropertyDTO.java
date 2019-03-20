package at.jku.cis.iVolunteer.model.property.dto;

import java.util.List;

public class MultiplePropertyDTO extends PropertyDTO<Object> {

	List<PropertyDTO<Object>> properties;
	


	public List<PropertyDTO<Object>> getProperties() {
		return properties;
	}
	
	public void setProperties(List<PropertyDTO<Object>> properties) {
		this.properties = properties;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MultiplePropertyDTO)) {
			return false;
		}
		return ((MultiplePropertyDTO) obj).id.equals(id);
	}

	
	
	
}
