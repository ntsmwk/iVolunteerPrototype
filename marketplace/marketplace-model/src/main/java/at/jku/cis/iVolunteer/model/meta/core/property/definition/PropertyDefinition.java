package at.jku.cis.iVolunteer.model.meta.core.property.definition;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

@Document
public class PropertyDefinition<T> {

	@Id
	private String id;
	private String name;
	
	private List<T> allowedValues;
	
	private boolean custom;
	private boolean multiple;
	
	protected PropertyType type;
	
	private boolean required;
	private List<PropertyConstraint<Object>> propertyConstraints;
	
	
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
	
	public List<PropertyConstraint<Object>> getPropertyConstraints() {
		return propertyConstraints;
	}
	public void setPropertyConstraints(List<PropertyConstraint<Object>> propertyConstraints) {
		this.propertyConstraints = propertyConstraints;
	}

	
	
	
	
}
