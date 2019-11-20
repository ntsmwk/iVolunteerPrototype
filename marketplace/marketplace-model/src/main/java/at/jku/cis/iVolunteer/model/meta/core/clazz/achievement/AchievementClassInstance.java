package at.jku.cis.iVolunteer.model.meta.core.clazz.achievement;

import org.springframework.data.mongodb.core.mapping.Document;

import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@Document(collection="classInstance")
public class AchievementClassInstance extends ClassInstance {

	public AchievementClassInstance() {
	}
}
