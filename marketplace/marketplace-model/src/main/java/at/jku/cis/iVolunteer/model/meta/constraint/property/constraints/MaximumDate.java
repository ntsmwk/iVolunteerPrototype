package at.jku.cis.iVolunteer.model.meta.constraint.property.constraints;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.model.meta.constraint.property.DatePropertyConstraint;

@Document
public class MaximumDate extends DatePropertyConstraint<Date> {

	MaximumDate(Date value) {
		setConstraintType(ConstraintType.MAX);
		this.setValue(value);
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MaximumDate)) {
			return false;
		}
		return ((MaximumDate) obj).getId().equals(getId());
	}
}
