package at.jku.cis.iVolunteer.core.user.image;


import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.image.UserImage;

public interface CoreUserImageRepository extends MongoRepository<UserImage, String> {
	
}
