package at.jku.cis.iVolunteer.configurator.model.meta.constraint.property.constraints;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.configurator.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.configurator.model.meta.constraint.property.LongNumberPropertyConstraint;

@Document
public class MinimumValue extends LongNumberPropertyConstraint<Integer> {

	public MinimumValue(int value) {
		setConstraintType(ConstraintType.MIN);
		this.setValue(value);
	}

}
