package at.jku.cis.iVolunteer.model.meta.core.clazz.achievement;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

@Document
public class AchievementClassDefinition extends ClassDefinition {

	public AchievementClassDefinition() {
		this.setClassArchetype(ClassArchetype.ACHIEVEMENT);

	}

}
