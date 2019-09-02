package at.jku.cis.iVolunteer.model.meta.core.property.dtos;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.property.dto.PropertyConstraintDTO;
import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;


public class PropertyDefinitionDTO<T> {

	String id;
	String name;
	
	boolean custom;
	
	PropertyType type;
	
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
	public boolean isCustom() {
		return custom;
	}
	public void setCustom(boolean custom) {
		this.custom = custom;
	}
	public PropertyType getType() {
		return type;
	}
	public void setType(PropertyType type) {
		this.type = type;
	}
	public List<PropertyConstraintDTO<T>> getPropertyConstraints() {
		return propertyConstraints;
	}
	public void setPropertyConstraints(List<PropertyConstraintDTO<T>> propertyConstraints) {
		this.propertyConstraints = propertyConstraints;
	}
	
	
	
	
}
