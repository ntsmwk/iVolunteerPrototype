package at.jku.cis.iVolunteer.model.meta.constraint.property.constraints;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.model.meta.constraint.property.TextPropertyConstraint;

@Document
public class MaximumTextLength extends TextPropertyConstraint {

	public MaximumTextLength() {
		setConstraintType(ConstraintType.MAX_LENGTH);
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MaximumTextLength)) {
			return false;
		}
		return ((MaximumTextLength) obj).getId().equals(getId());
	}
}
