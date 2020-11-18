package at.jku.cis.iVolunteer.configurator.model.meta.constraint.property.constraints;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.configurator.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.configurator.model.meta.constraint.property.TextPropertyConstraint;

@Document
public class MaximumTextLength extends TextPropertyConstraint<Integer> {

	public MaximumTextLength(int value) {
		setConstraintType(ConstraintType.MAX_LENGTH);
		this.setValue(value);
	}

}
