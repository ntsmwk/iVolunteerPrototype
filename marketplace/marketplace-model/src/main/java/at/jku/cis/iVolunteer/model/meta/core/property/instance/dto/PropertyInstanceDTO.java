package at.jku.cis.iVolunteer.model.meta.core.property.instance.dto;

import java.util.List;

import at.jku.cis.iVolunteer.model.meta.constraint.property.dto.PropertyConstraintDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

public class PropertyInstanceDTO<T> {

	String id;	
	String name;
	
	List<T> values;
	List<T> allowedValues;
	
	PropertyType type;

	boolean immutable;
	boolean updateable;
	boolean required;
	
	int position; 
	
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

	public List<T> getValues() {
		return values;
	}

	public void setValues(List<T> values) {
		this.values = values;
	}

	public List<T> getAllowedValues() {
		return allowedValues;
	}

	public void setAllowedValues(List<T> allowedValues) {
		this.allowedValues = allowedValues;
	}

	public PropertyType getType() {
		return type;
	}

	public void setType(PropertyType type) {
		this.type = type;
	}

	public boolean isImmutable() {
		return immutable;
	}

	public void setImmutable(boolean immutable) {
		this.immutable = immutable;
	}

	public boolean isUpdateable() {
		return updateable;
	}

	public void setUpdateable(boolean updateable) {
		this.updateable = updateable;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public List<PropertyConstraintDTO<T>> getPropertyConstraints() {
		return propertyConstraints;
	}

	public void setPropertyConstraints(List<PropertyConstraintDTO<T>> propertyConstraints) {
		this.propertyConstraints = propertyConstraints;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PropertyInstanceDTO<?>)) {
			return false;
		}
		return ((PropertyInstanceDTO<?>) obj).id.equals(id);
	
	}
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	
	
	
}
