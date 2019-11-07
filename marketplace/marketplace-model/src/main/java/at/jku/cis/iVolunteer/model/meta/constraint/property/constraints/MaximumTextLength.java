package at.jku.cis.iVolunteer.model.meta.constraint.property.constraints;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.model.meta.constraint.property.TextPropertyConstraint;

@Document
public class MaximumTextLength extends TextPropertyConstraint<Integer> {

	public MaximumTextLength(int value) {
		setConstraintType(ConstraintType.MAX_LENGTH);
		this.setValue(value);
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
