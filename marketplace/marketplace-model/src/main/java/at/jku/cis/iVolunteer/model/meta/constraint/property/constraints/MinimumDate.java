package at.jku.cis.iVolunteer.model.meta.constraint.property.constraints;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.constraint.ConstraintType;
import at.jku.cis.iVolunteer.model.meta.constraint.property.DatePropertyConstraint;

@Document
public class MinimumDate extends DatePropertyConstraint<Date> {

	MinimumDate(Date value) {
		setConstraintType(ConstraintType.MIN);
		this.setValue(value);
	}
	
	@Override
	public int hashCode() {
		return getId().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MinimumDate)) {
			return false;
		}
		return ((MinimumDate) obj).getId().equals(getId());
	}
}
