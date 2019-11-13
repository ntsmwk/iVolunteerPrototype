package at.jku.cis.iVolunteer.model.meta.constraint.property;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

@Document
public class FloatNumberPropertyConstraint<T> extends PropertyConstraint<T> {
	
	public FloatNumberPropertyConstraint() {
		setPropertyType(PropertyType.FLOAT_NUMBER);
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FloatNumberPropertyConstraint)) {
			return false;
		}
		return ((FloatNumberPropertyConstraint<?>) obj).getId().equals(getId());
	}
}
