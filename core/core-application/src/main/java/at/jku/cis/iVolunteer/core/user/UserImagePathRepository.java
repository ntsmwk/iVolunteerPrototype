package at.jku.cis.iVolunteer.core.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.user.UserImagePath;

@Repository
public interface UserImagePathRepository extends MongoRepository<UserImagePath, String> {

}
