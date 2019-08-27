package at.jku.cis.iVolunteer.model.meta.constraint.property.constraints;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.model.meta.constraint.property.LongNumberPropertyConstraint;

@Document
public class MinimumValue extends LongNumberPropertyConstraint {

	public MinimumValue() {
		setConstraintType(ConstraintType.MIN);
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MinimumValue)) {
			return false;
		}
		return ((MinimumValue) obj).getId().equals(getId());
	}
}
