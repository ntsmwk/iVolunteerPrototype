package at.jku.cis.iVolunteer.mapper.meta.core.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

@Component
public class PropertyDefinitionToPropertyInstanceMapper implements OneWayMapper<PropertyDefinition<Object>, PropertyInstance<Object>> {
	
	@Override
	public PropertyInstance<Object> toTarget(PropertyDefinition<Object> source) {
		if (source == null) {
			return null;
		}
		
		PropertyInstance<Object> propertyInstance = new PropertyInstance<Object>();
		
		propertyInstance.setId(source.getId());
		propertyInstance.setName(source.getName());
		
//		if (source.getAllowedValues() != null) {
//			propertyInstance.setAllowedValues(new ArrayList<Object>(source.getAllowedValues()));
//		}
		propertyInstance.setType(source.getType());
		
		if (source.getPropertyConstraints() != null) {
			propertyInstance.setPropertyConstraints(new ArrayList<PropertyConstraint<Object>>(source.getPropertyConstraints()));
		}
		
		propertyInstance.setRequired(source.isRequired());
		
		return propertyInstance;
	}

	@Override
	public List<PropertyInstance<Object>> toTargets(List<PropertyDefinition<Object>> sources) {
		if (sources == null) {
			System.out.println("returning null");
			return null;
		}
		
		System.out.println("not returning null");
		
		List<PropertyInstance<Object>> targets = new ArrayList<PropertyInstance<Object>>();
		for (PropertyDefinition<Object> definition : sources) {
			System.out.println(definition.getId());

			targets.add(toTarget(definition));
		}
		
		return targets;
	}



}
