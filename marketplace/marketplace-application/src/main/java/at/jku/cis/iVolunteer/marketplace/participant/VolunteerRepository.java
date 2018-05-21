package at.jku.cis.iVolunteer.marketplace.participant;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VolunteerRepository extends MongoRepository<Volunteer, String> {

	Volunteer findByUsername(String username);
}
