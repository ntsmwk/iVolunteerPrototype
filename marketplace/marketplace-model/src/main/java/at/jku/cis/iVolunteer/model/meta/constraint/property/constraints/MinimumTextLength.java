package at.jku.cis.iVolunteer.model.meta.constraint.property.constraints;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.model.meta.constraint.property.TextPropertyConstraint;

@Document
public class MinimumTextLength extends TextPropertyConstraint<Integer> {

	public MinimumTextLength(int value) {
		setConstraintType(ConstraintType.MIN_LENGTH);
		this.setValue(value);
	}

}
