package at.jku.cis.iVolunteer.model.meta.core.property.instance;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.ClassProperty;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.PropertyDefinition;

@Document
public class PropertyInstance<T> {

	@Id
	String id;
	String name;
	
	List<T> values;
	List<T> allowedValues;
	
	PropertyType type;

	boolean immutable;
	boolean updateable;
	boolean required;
	
	int position; 
	
	List<PropertyConstraint<T>> propertyConstraints;
	
	public PropertyInstance() {}
	
	public PropertyInstance(ClassProperty<T> classProperty) {
		this.id = classProperty.getId();
		this.name = classProperty.getName();
		
		List<T> values = new ArrayList<T>();
		if (classProperty.getDefaultValues() != null) {
			for (T t : classProperty.getDefaultValues()) {
				values.add(t);
			}
		}
		this.values = values;
		
		List<T> allowedValues = new ArrayList<T>();
		if (classProperty.getAllowedValues() != null) {
			for (T t : classProperty.getAllowedValues()) {
				allowedValues.add(t);
			}
		}
		this.allowedValues = allowedValues;
		
		this.type = classProperty.getType();
		
		this.immutable = classProperty.isImmutable();
		this.updateable = classProperty.isUpdateable();
		this.required = classProperty.isRequired();
		
		this.position = classProperty.getPosition();
		
		List<PropertyConstraint<T>> propertyConstraints = new ArrayList<PropertyConstraint<T>>();
		if (classProperty.getPropertyConstraints() != null) {
			for (PropertyConstraint<T> c : classProperty.getPropertyConstraints()) {
				propertyConstraints.add(c);
			}
		}
		this.propertyConstraints = propertyConstraints;
	}
	
	public PropertyInstance(PropertyDefinition<T> propertyDefinition) {
		this.id = propertyDefinition.getId();
		this.name = propertyDefinition.getName();
		
		List<T> allowedValues = new ArrayList<T>();
		if (propertyDefinition.getAllowedValues() != null) {
			for (T t : propertyDefinition.getAllowedValues()) {
				allowedValues.add(t);
			}
		}
		this.allowedValues = allowedValues;
		
		this.type = propertyDefinition.getType();
		
		List<PropertyConstraint<T>> propertyConstraints = new ArrayList<PropertyConstraint<T>>();
		if (propertyDefinition.getPropertyConstraints() !=  null) {
			for (PropertyConstraint<T> c : propertyDefinition.getPropertyConstraints()) {
				propertyConstraints.add(c);
			}
		}
		this.propertyConstraints = propertyConstraints;
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

	public List<PropertyConstraint<T>> getPropertyConstraints() {
		return propertyConstraints;
	}

	public void setPropertyConstraints(List<PropertyConstraint<T>> propertyConstraints) {
		this.propertyConstraints = propertyConstraints;
	}
	
	public void resetValues() {
		values = new ArrayList<T>();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PropertyInstance<?>)) {
			return false;
		}
		return ((PropertyInstance<?>) obj).id.equals(id);
	
	}
	
	@Override
	public int hashCode() {
		return this.id.hashCode();
	}
	
	
	
}
