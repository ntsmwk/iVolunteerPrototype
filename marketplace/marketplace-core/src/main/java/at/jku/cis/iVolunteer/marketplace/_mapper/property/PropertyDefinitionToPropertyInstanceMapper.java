package at.jku.cis.iVolunteer.marketplace._mapper.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty.FlatPropertyDefinition;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

@Component
public class PropertyDefinitionToPropertyInstanceMapper
		implements OneWayMapper<FlatPropertyDefinition<Object>, PropertyInstance<Object>> {

	@Override
	public PropertyInstance<Object> toTarget(FlatPropertyDefinition<Object> source) {
		if (source == null) {
			return null;
		}

		PropertyInstance<Object> propertyInstance = new PropertyInstance<Object>();

		propertyInstance.setId(source.getId());
		propertyInstance.setName(source.getName());
		propertyInstance.setType(source.getType());

		if (source.getPropertyConstraints() != null) {
			propertyInstance
					.setPropertyConstraints(new ArrayList<PropertyConstraint<Object>>(source.getPropertyConstraints()));
		}

		propertyInstance.setRequired(source.isRequired());

		return propertyInstance;
	}

	@Override
	public List<PropertyInstance<Object>> toTargets(List<FlatPropertyDefinition<Object>> sources) {
		if (sources == null) {
			return null;
		}

		List<PropertyInstance<Object>> targets = new ArrayList<PropertyInstance<Object>>();
		for (FlatPropertyDefinition<Object> definition : sources) {
			targets.add(toTarget(definition));
		}

		return targets;
	}

}
