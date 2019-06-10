package at.jku.cis.iVolunteer.model.property.dto;

import java.util.List;

public class MultiPropertyDTO extends PropertyDTO<Object> {

	List<PropertyDTO<Object>> properties;
	


	public List<PropertyDTO<Object>> getProperties() {
		return properties;
	}
	
	public void setProperties(List<PropertyDTO<Object>> properties) {
		this.properties = properties;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MultiPropertyDTO)) {
			return false;
		}
		return ((MultiPropertyDTO) obj).id.equals(id);
	}

	
	
	
}
