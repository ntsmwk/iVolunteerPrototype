package at.jku.cis.iVolunteer.marketplace.participant;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.participant.Volunteer;

public interface VolunteerRepository extends MongoRepository<Volunteer, String> {

	Volunteer findByUsername(String username);
}
