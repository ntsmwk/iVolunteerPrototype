package at.jku.cis.iVolunteer.model.meta.core.clazz.task;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

@Document
public class TaskClassDefinition extends ClassDefinition{

	public TaskClassDefinition() {
		this.setClassArchetype(ClassArchetype.TASK);
	}
}
