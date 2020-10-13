package at.jku.cis.iVolunteer.model.meta.core.clazz.competence;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

@Document(collection = "classDefinition")
public class CompetenceClassDefinition extends ClassDefinition {

	public CompetenceClassDefinition() {
	}

}
