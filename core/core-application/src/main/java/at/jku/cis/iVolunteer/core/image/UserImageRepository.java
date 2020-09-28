package at.jku.cis.iVolunteer.core.image;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.image.UserImage;

public interface UserImageRepository extends MongoRepository<UserImage, String> {

	List<UserImage> findByUserId(String userId);

}
