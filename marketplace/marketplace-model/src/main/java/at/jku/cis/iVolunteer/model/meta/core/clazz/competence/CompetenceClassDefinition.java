package at.jku.cis.iVolunteer.model.meta.core.clazz.competence;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.clazz.VolunteeringClassDefinition;

@Document(collection = "classDefinition")
public class CompetenceClassDefinition extends VolunteeringClassDefinition {

	public CompetenceClassDefinition() {
	}

}
