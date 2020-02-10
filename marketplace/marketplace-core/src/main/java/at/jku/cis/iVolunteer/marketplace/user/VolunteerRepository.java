package at.jku.cis.iVolunteer.marketplace.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.user.Volunteer;

public interface VolunteerRepository extends MongoRepository<Volunteer, String> {

	Volunteer findByUsername(String username);
}