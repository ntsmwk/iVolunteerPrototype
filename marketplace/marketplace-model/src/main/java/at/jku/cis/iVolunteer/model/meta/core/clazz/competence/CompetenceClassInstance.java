package at.jku.cis.iVolunteer.model.meta.core.clazz.competence;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@Document(collection="classInstance")
public class CompetenceClassInstance extends ClassInstance {

	public CompetenceClassInstance() {

	}

}
