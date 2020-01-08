package at.jku.cis.iVolunteer.model.meta.core.clazz.achievement;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.VolunteeringClassDefinition;


@Document(collection="classDefinition")
public class AchievementClassDefinition extends VolunteeringClassDefinition {

	public AchievementClassDefinition() {
		this.setClassArchetype(ClassArchetype.ACHIEVEMENT);

	}

}
