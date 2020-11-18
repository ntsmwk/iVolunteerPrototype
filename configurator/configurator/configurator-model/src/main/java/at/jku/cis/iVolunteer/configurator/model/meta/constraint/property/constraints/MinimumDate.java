package at.jku.cis.iVolunteer.configurator.model.meta.constraint.property.constraints;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.configurator.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.configurator.model.meta.constraint.property.DatePropertyConstraint;

@Document
public class MinimumDate extends DatePropertyConstraint<Date> {

	MinimumDate(Date value) {
		setConstraintType(ConstraintType.MIN);
		this.setValue(value);
	}

}
