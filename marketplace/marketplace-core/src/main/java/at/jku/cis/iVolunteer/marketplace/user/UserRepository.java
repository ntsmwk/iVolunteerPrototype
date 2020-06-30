package at.jku.cis.iVolunteer.marketplace.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import at.jku.cis.iVolunteer.model.user.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findByUsername(String username);
}