package at.jku.cis.iVolunteer.model.meta.constraint.property;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

@Document
public class LongNumberPropertyConstraint<T> extends PropertyConstraint<T> {
	
	public LongNumberPropertyConstraint() {
		setPropertyType(PropertyType.WHOLE_NUMBER);
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LongNumberPropertyConstraint)) {
			return false;
		}
		return ((LongNumberPropertyConstraint<?>) obj).getId().equals(getId());
	}
}
