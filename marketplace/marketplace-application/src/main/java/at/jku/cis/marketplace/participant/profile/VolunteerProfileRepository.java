package at.jku.cis.marketplace.participant.profile;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.marketplace.participant.Volunteer;

@Repository
public interface VolunteerProfileRepository extends MongoRepository<VolunteerProfile, String> {

	VolunteerProfile findByVolunteer(Volunteer volunteer);

}
