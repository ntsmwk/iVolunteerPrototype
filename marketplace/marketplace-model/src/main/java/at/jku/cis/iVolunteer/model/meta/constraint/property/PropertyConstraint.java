package at.jku.cis.iVolunteer.model.meta.constraint.property;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.Constraint;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

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
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PropertyConstraint<?>)) {
			return false;
		}
		return ((PropertyConstraint<?>) obj).getId().equals(getId());
	}
	

	
	

	
	
}
