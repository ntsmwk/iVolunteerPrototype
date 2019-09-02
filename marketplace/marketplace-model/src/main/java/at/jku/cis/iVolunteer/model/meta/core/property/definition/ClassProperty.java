package at.jku.cis.iVolunteer.model.meta.core.property.definition;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

@Document
public class ClassProperty<T> {

	@Id
	String id;
	String name;
	
	List<T> defaultValues;
	List<T> allowedValues;
	
	PropertyType type;

	boolean immutable;
	boolean updateable;
	boolean required;
	
	int position; 
	
	List<PropertyConstraint<T>> propertyConstraints;

	public ClassProperty() {}
	
	public ClassProperty(PropertyDefinition<T> propertyDefinition) {
		this.id = propertyDefinition.getId();
		this.name = propertyDefinition.name;
		this.defaultValues = new ArrayList<T>();
		
		
		this.allowedValues = new ArrayList<T>();
		if (propertyDefinition.allowedValues != null) {
			for (T t : propertyDefinition.allowedValues) {
				this.allowedValues.add(t);
			}
		}
		this.type = propertyDefinition.type;
		
		this.immutable = false;
		this.updateable = false;
		this.required = false;
		
		this.position = 0;
		
		this.propertyConstraints = new ArrayList<PropertyConstraint<T>>();
		if (propertyDefinition.propertyConstraints != null) {
			for (PropertyConstraint<T> c : propertyDefinition.propertyConstraints) {
				this.propertyConstraints.add(c);
			}
		}
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

	public List<T> getDefaultValues() {
		return defaultValues;
	}

	public void setDefaultValues(List<T> defaultValues) {
		this.defaultValues = defaultValues;
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
	

	public List<PropertyConstraint<T>> getPropertyConstraints() {
		return propertyConstraints;
	}

	public void setPropertyConstraints(List<PropertyConstraint<T>> propertyConstraints) {
		this.propertyConstraints = propertyConstraints;
	}
	
	
	
	
	
	
}
