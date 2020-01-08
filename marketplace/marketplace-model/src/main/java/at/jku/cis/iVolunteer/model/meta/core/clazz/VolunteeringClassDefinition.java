package at.jku.cis.iVolunteer.model.meta.core.clazz;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "classDefinition")
public class VolunteeringClassDefinition extends ClassDefinition {

	private String iVolunteerUserId;

	public VolunteeringClassDefinition() {
	}

	public String getiVolunteerUserId() {
		return iVolunteerUserId;
	}

	public void setiVolunteerUserId(String iVolunteerUserId) {
		this.iVolunteerUserId = iVolunteerUserId;
	}

}