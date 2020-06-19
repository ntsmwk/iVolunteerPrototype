package at.jku.cis.iVolunteer.model.meta.core;

import java.util.List;


public class ClassPropertyRequestObject {

	List<String> propertyDefinitionIds;
	List<String> enumDefinitionIds;
	

	public List<String> getPropertyDefinitionIds() {
		return propertyDefinitionIds;
	}
	
	public void setPropertyDefinitionIds(List<String> propertyDefinitionIds) {
		this.propertyDefinitionIds = propertyDefinitionIds;
	}
	
	public List<String> getEnumDefinitionIds() {
		return enumDefinitionIds;
	}
	
	public void setEnumDefinitionIds(List<String> enumDefinitionIds) {
		this.enumDefinitionIds = enumDefinitionIds;
	}

	

}
