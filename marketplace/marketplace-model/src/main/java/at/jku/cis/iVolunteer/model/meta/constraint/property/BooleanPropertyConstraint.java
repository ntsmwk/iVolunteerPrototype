package at.jku.cis.iVolunteer.model.meta.constraint.property;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

@Document
public class BooleanPropertyConstraint<T> extends PropertyConstraint<T> {
	
	public BooleanPropertyConstraint() {
		setPropertyType(PropertyType.BOOL);
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BooleanPropertyConstraint)) {
			return false;
		}
		return ((BooleanPropertyConstraint) obj).getId().equals(getId());
	}
}
