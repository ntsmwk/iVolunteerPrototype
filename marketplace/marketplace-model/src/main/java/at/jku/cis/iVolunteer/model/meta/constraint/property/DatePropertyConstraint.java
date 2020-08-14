package at.jku.cis.iVolunteer.model.meta.constraint.property;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.property.PropertyType;

@Document
public class DatePropertyConstraint<T> extends PropertyConstraint<T> {
	
	public DatePropertyConstraint() {
		setPropertyType(PropertyType.DATE);
	}

}
