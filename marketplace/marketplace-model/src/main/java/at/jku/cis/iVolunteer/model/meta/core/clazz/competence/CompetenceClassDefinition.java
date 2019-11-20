package at.jku.cis.iVolunteer.model.meta.core.clazz.competence;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

@Document
public class CompetenceClassDefinition extends ClassDefinition {

	public CompetenceClassDefinition() {
		this.setClassArchetype(ClassArchetype.COMPETENCE);
	}

}
