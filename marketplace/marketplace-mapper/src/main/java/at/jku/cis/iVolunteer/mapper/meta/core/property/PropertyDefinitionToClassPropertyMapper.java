package at.jku.cis.iVolunteer.mapper.meta.core.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;

@Component
public class PropertyDefinitionToClassPropertyMapper implements OneWayMapper<PropertyDefinition<Object>, ClassProperty<Object>> {
	
	
	//TODO @mwe remove!
	@Override
	public ClassProperty<Object> toTarget(PropertyDefinition<Object> source) {
		if (source == null) {
			return null;
		}
		
		ClassProperty<Object> classProperty = new ClassProperty<Object>();
		
		classProperty.setId(source.getId());
		classProperty.setName(source.getName());
		
		if (source.getAllowedValues() != null) {
			classProperty.setAllowedValues(new ArrayList<Object>(source.getAllowedValues()));
		}
		
		classProperty.setType(source.getType());
		
		if (source.getPropertyConstraints() != null) {
			classProperty.setPropertyConstraints(new ArrayList<PropertyConstraint<Object>>(source.getPropertyConstraints()));
		}
		
		classProperty.setMultiple(source.isMultiple());
		
		classProperty.setRequired(source.isRequired());
		
		return classProperty;
	} 

	@Override
	public List<ClassProperty<Object>> toTargets(List<PropertyDefinition<Object>> sources) {
		if (sources == null) {
			return null;
		}

		List<ClassProperty<Object>> targets = new ArrayList<ClassProperty<Object>>();
		for (PropertyDefinition<Object> definition : sources) {
			targets.add(this.toTarget(definition));
		}
		
		return targets;
	}



}
