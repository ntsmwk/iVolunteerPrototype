package at.jku.cis.iVolunteer.model.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class UserImagePath {
	@Id String userId;
	String imagePath;
	
}
