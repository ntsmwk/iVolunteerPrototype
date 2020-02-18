package at.jku.cis.iVolunteer.marketplace.meta.core.property;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;

@Component
public class PropertyDefinitionInitializer {

	
	public <T> PropertyDefinition<T> createPropertyDefinition(String name, PropertyType type, String tenantId){
		return new PropertyDefinition<>(name, type, tenantId);
	}
	
}
