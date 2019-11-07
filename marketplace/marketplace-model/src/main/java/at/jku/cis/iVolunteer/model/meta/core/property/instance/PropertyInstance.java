package at.jku.cis.iVolunteer.model.meta.core.property.instance;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.property.PropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

@Document
public class PropertyInstance<T> {

	@Id
	String id;	
	String name;
	
	List<T> values;
	
	PropertyType type;

	int position; 
	
	boolean required;
	
	List<PropertyConstraint<T>> propertyConstraints;
	
	public PropertyInstance() {}

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

	public PropertyType getType() {
		return type;
	}

	public void setType(PropertyType type) {
		this.type = type;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
	

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
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
