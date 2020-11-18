package at.jku.cis.iVolunteer.configurator.model.meta.constraint.property;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.configurator.model.meta.constraint.Constraint;
import at.jku.cis.iVolunteer.configurator.model.meta.core.property.PropertyType;

@Document
public class PropertyConstraint<T> extends Constraint {
	
	T value;
	
	PropertyType propertyType;

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public PropertyType getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}	
	
}
