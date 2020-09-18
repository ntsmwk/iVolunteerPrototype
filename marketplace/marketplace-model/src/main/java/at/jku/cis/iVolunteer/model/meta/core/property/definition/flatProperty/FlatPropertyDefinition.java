package at.jku.cis.iVolunteer.model.meta.core.property.definition.flatProperty;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.IVolunteerObject;
import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

@Document
public class FlatPropertyDefinition<T> extends IVolunteerObject {

	// @Id
	// private String id;
	private String name;
	
	private List<T> allowedValues = new ArrayList<>();

	private String unit;
	
	private boolean custom;
	private boolean multiple;

	protected PropertyType type;

	private boolean required;
	private String requiredMessage;
	private List<PropertyConstraint<Object>> propertyConstraints = new ArrayList<>();

	public FlatPropertyDefinition() {
	}

	public FlatPropertyDefinition(String name, PropertyType type, String tenantId) {
		this.name = name;
		this.type = type;
		this.tenantId = tenantId;
	}

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
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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

	public String getRequiredMessage() {
		return requiredMessage;
	}

	public void setRequiredMessage(String requiredMessage) {
		this.requiredMessage = requiredMessage;
	}

	public List<PropertyConstraint<Object>> getPropertyConstraints() {
		return propertyConstraints;
	}

	public void setPropertyConstraints(List<PropertyConstraint<Object>> propertyConstraints) {
		this.propertyConstraints = propertyConstraints;
	}

}
