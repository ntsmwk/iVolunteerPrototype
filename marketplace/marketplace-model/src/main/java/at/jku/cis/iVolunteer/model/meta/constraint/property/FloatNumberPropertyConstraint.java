package at.jku.cis.iVolunteer.model.meta.constraint.property;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

@Document
public class FloatNumberPropertyConstraint<T> extends PropertyConstraint<T> {
	
	public FloatNumberPropertyConstraint() {
		setPropertyType(PropertyType.FLOAT_NUMBER);
	}
}
