package at.jku.cis.iVolunteer.model.meta.constraint.property.constraints;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.model.meta.constraint.property.DatePropertyConstraint;

@Document
public class MinimumDate extends DatePropertyConstraint {

	MinimumDate() {
		setConstraintType(ConstraintType.MIN);
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MinimumDate)) {
			return false;
		}
		return ((MinimumDate) obj).getId().equals(getId());
	}
}
