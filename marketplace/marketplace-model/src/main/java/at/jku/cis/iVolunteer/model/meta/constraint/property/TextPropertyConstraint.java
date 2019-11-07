package at.jku.cis.iVolunteer.model.meta.constraint.property;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

@Document
public class TextPropertyConstraint<T> extends PropertyConstraint<T> {
	
	public TextPropertyConstraint() {
		setPropertyType(PropertyType.TEXT);
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TextPropertyConstraint)) {
			return false;
		}
		return ((TextPropertyConstraint<?>) obj).getId().equals(getId());
	}
}
