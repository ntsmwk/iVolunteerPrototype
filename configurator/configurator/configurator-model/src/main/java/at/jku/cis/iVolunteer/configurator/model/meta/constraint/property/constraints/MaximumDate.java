package at.jku.cis.iVolunteer.configurator.model.meta.constraint.property.constraints;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.configurator.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.configurator.model.meta.constraint.property.DatePropertyConstraint;

@Document
public class MaximumDate extends DatePropertyConstraint<Date> {

	MaximumDate(Date value) {
		setConstraintType(ConstraintType.MAX);
		this.setValue(value);
	}

}
