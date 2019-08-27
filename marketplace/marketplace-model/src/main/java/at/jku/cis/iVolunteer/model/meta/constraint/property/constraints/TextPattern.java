package at.jku.cis.iVolunteer.model.meta.constraint.property.constraints;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.model.meta.constraint.property.TextPropertyConstraint;

@Document
public class TextPattern extends TextPropertyConstraint {

	TextPattern() {
		setConstraintType(ConstraintType.PATTERN);
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TextPattern)) {
			return false;
		}
		return ((TextPattern) obj).getId().equals(getId());
	}
}
