package at.jku.cis.iVolunteer.model.meta.constraint;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Constraint {
	ConstraintType constraintType;
	
	String message;

	public ConstraintType getConstraintType() {
		return constraintType;
	}

	public void setConstraintType(ConstraintType constraintType) {
		this.constraintType = constraintType;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
