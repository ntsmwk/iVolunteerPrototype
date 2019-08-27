package at.jku.cis.iVolunteer.model.meta.constraint.property.constraints;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.model.meta.constraint.property.TextPropertyConstraint;

@Document
public class MinimumTextLength extends TextPropertyConstraint {

	public MinimumTextLength() {
		setConstraintType(ConstraintType.MIN_LENGTH);
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MinimumTextLength)) {
			return false;
		}
		return ((MinimumTextLength) obj).getId().equals(getId());
	}
}
