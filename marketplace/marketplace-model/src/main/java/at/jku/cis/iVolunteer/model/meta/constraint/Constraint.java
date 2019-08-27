package at.jku.cis.iVolunteer.model.meta.constraint;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Constraint {
	@Id
	String id;
	
	ConstraintType constraintType;
	
	String message;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	@Override
	public int hashCode() {
		return id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Constraint)) {
			return false;
		}
		return ((Constraint) obj).id.equals(id);
	}
	

}
