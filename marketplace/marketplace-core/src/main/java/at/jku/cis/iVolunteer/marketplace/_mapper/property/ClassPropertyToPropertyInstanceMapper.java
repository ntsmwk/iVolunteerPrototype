package at.jku.cis.iVolunteer.marketplace._mapper.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import at.jku.cis.iVolunteer.model._mapper.OneWayMapper;
import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

@Component
public class ClassPropertyToPropertyInstanceMapper
		implements OneWayMapper<ClassProperty<Object>, PropertyInstance<Object>> {

	@Override
	public PropertyInstance<Object> toTarget(ClassProperty<Object> source) {
		if (source == null) {
			return null;
		}

		PropertyInstance<Object> propertyInstance = new PropertyInstance<Object>();
		propertyInstance.setId(source.getId());
		propertyInstance.setName(source.getName());
		propertyInstance.setValues(new ArrayList<Object>(source.getDefaultValues()));
		propertyInstance.setAllowedValues(new ArrayList<Object>(source.getAllowedValues()));
		propertyInstance.setType(source.getType());
		propertyInstance.setImmutable(source.isImmutable());
		propertyInstance.setUpdateable(source.isUpdateable());
		propertyInstance.setRequired(source.isRequired());

		propertyInstance.setPosition(source.getPosition());

		propertyInstance
				.setPropertyConstraints(new ArrayList<PropertyConstraint<Object>>(source.getPropertyConstraints()));

		propertyInstance.setTabId(source.getTabId());
		propertyInstance.setVisible(source.isVisible());
		propertyInstance.setLevel(source.getLevel());

		return propertyInstance;
	}

	@Override
	public List<PropertyInstance<Object>> toTargets(List<ClassProperty<Object>> sources) {
		if (sources == null) {
			return null;
		}

		List<PropertyInstance<Object>> instances = new ArrayList<PropertyInstance<Object>>();
		for (ClassProperty<Object> classProperty : sources) {
			instances.add(this.toTarget(classProperty));
		}

		return instances;
	}

}
