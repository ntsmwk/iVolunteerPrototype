package at.jku.cis.iVolunteer.configurator.model.meta.constraint.property.constraints;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.configurator.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.configurator.model.meta.constraint.property.LongNumberPropertyConstraint;

@Document
public class MaximumValue extends LongNumberPropertyConstraint<Integer> {

	public MaximumValue(int value) {
		setConstraintType(ConstraintType.MAX);
		this.setValue(value);
	}
}
