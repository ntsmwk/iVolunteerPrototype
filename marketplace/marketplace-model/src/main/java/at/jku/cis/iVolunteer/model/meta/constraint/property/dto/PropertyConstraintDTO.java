package at.jku.cis.iVolunteer.model.meta.constraint.property.dto;

import at.jku.cis.iVolunteer.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

public class PropertyConstraintDTO<T> {
	
	String id;
	ConstraintType constraintType;

	T value;
	PropertyType propertyType;
	
	String message;
	
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	
	public ConstraintType getConstraintType() {
		return constraintType;
	}
	
	public void setConstraintType(ConstraintType constraintType) {
		this.constraintType = constraintType;
	}
	
	
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
