package at.jku.cis.iVolunteer.model.task;

import java.util.ArrayList;
import java.util.List;

import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.instance.PropertyInstance;

public class TaskField {

	String key;
	List<Object> values;
	String label;
	PropertyType type;
	boolean required;

	//	OPTIONAL 
	String unit;
	List<Object> allowedValues = new ArrayList<>();
	List<PropertyConstraint<Object>> constraints = new ArrayList<>();
	
	
	public TaskField() { }
	
	public TaskField(ClassProperty<Object> classProperty) {
		this.key = classProperty.getId();
		this.values = new ArrayList<>();
		this.label = classProperty.getName();
		this.type = classProperty.getType();
		this.required = classProperty.isRequired();
		this.unit = classProperty.getUnit();
		this.allowedValues = classProperty.getAllowedValues();
		this.constraints = classProperty.getPropertyConstraints();
	}
	
	public TaskField(PropertyInstance<Object> propertyInstance) {
		this.key = propertyInstance.getId();
		this.values = propertyInstance.getValues();
		this.label = propertyInstance.getName();
		this.type = propertyInstance.getType();
		this.required = propertyInstance.isRequired();
		this.unit = propertyInstance.getUnit();
		this.allowedValues = propertyInstance.getAllowedValues();
		this.constraints = propertyInstance.getPropertyConstraints();
	}
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public List<Object> getValues() {
		return values;
	}
	public void setValues(List<Object> values) {
		this.values = values;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public PropertyType getType() {
		return type;
	}
	public void setType(PropertyType type) {
		this.type = type;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public List<Object> getAllowedValues() {
		return allowedValues;
	}
	public void setAllowedValues(List<Object> allowedValues) {
		this.allowedValues = allowedValues;
	}
	public List<PropertyConstraint<Object>> getConstraints() {
		return constraints;
	}
	public void setConstraints(List<PropertyConstraint<Object>> constraints) {
		this.constraints = constraints;
	}

}
