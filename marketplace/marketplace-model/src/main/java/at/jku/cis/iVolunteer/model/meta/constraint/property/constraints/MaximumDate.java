package at.jku.cis.iVolunteer.model.meta.constraint.property.constraints;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.model.meta.constraint.property.DatePropertyConstraint;
import at.jku.cis.iVolunteer.model.meta.constraint.property.TextPropertyConstraint;

@Document
public class MaximumDate extends DatePropertyConstraint {

	MaximumDate() {
		setConstraintType(ConstraintType.MAX);
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MaximumDate)) {
			return false;
		}
		return ((MaximumDate) obj).getId().equals(getId());
	}
}
