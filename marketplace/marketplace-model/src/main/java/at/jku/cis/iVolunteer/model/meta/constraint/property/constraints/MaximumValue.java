package at.jku.cis.iVolunteer.model.meta.constraint.property.constraints;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.model.meta.constraint.property.LongNumberPropertyConstraint;

@Document
public class MaximumValue extends LongNumberPropertyConstraint {

	public MaximumValue() {
		setConstraintType(ConstraintType.MAX);
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MaximumValue)) {
			return false;
		}
		return ((MaximumValue) obj).getId().equals(getId());
	}
}
