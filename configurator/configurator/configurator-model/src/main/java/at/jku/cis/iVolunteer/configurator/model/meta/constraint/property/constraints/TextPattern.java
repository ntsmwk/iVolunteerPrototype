package at.jku.cis.iVolunteer.configurator.model.meta.constraint.property.constraints;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.configurator.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.configurator.model.meta.constraint.property.TextPropertyConstraint;

@Document
public class TextPattern extends TextPropertyConstraint<String> {

	public TextPattern(String value) {
		setConstraintType(ConstraintType.PATTERN);
		this.setValue(value);
	}

}
