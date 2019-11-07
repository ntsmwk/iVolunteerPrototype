package at.jku.cis.iVolunteer.model.meta.core.property.dtos;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.constraint.property.dto.PropertyConstraintDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

public class PropertyDefinitionDTO<T> {

	String id;
	String name;
	
	List<T> allowedValues;
	
	boolean custom;
	boolean multiple;
	
	PropertyType type;
	
	boolean required;
	List<PropertyConstraintDTO<T>> propertyConstraints;
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<T> getAllowedValues() {
		return allowedValues;
	}
	public void setAllowedValues(List<T> allowedValues) {
		this.allowedValues = allowedValues;
	}
	
	public boolean isCustom() {
		return custom;
	}
	public void setCustom(boolean custom) {
		this.custom = custom;
	}	
	
	public boolean isMultiple() {
		return multiple;
	}
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
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
	
	public List<PropertyConstraintDTO<T>> getPropertyConstraints() {
		return propertyConstraints;
	}
	public void setPropertyConstraints(List<PropertyConstraintDTO<T>> propertyConstraints) {
		this.propertyConstraints = propertyConstraints;
	}
	
	
	
	
}
