package at.jku.cis.iVolunteer.core.volunteer;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;

public interface CoreVolunteerRepository extends MongoRepository<CoreVolunteer, String> {

	CoreVolunteer findByUsername(String username);
}
