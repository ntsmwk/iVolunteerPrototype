package at.jku.cis.iVolunteer.model.meta.core.property.dtos;

import java.util.List;

import org.springframework.data.annotation.Id;

import at.jku.cis.iVolunteer.model.meta.constraint.property.dto.PropertyConstraintDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

public class ClassPropertyDTO<T> {

	String id;
	String name;
	
	List<T> defaultValues;
	List<T> allowedValues;
	
	PropertyType type;

	boolean immutable;
	boolean updateable;
	boolean required;
	
	int position; 
	
	List<PropertyConstraintDTO<?>> propertyConstraints;

	
	
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

	public List<T> getDefaultValues() {
		return defaultValues;
	}

	public void setDefaultValues(List<T> defaultValues) {
		this.defaultValues = defaultValues;
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
	

	public List<PropertyConstraintDTO<?>> getPropertyConstraints() {
		return propertyConstraints;
	}

	public void setPropertyConstraints(List<PropertyConstraintDTO<?>> propertyConstraints) {
		this.propertyConstraints = propertyConstraints;
	}
	
	
	
	
	
	
}
