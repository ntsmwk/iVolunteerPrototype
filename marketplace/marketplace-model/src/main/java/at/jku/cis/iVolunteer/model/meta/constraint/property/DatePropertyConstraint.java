package at.jku.cis.iVolunteer.model.meta.constraint.property;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

@Document
public class DatePropertyConstraint<T> extends PropertyConstraint<T> {
	
	public DatePropertyConstraint() {
		setPropertyType(PropertyType.DATE);
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DatePropertyConstraint)) {
			return false;
		}
		return ((DatePropertyConstraint<?>) obj).getId().equals(getId());
	}
}
