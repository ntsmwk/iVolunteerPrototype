package at.jku.cis.iVolunteer.model.meta.core.clazz.task;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.VolunteeringClassDefinition;

@Document(collection = "classDefinition")
public class TaskClassDefinition extends VolunteeringClassDefinition {

	private String userId;

	public TaskClassDefinition() {
		this.setClassArchetype(ClassArchetype.TASK);
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
