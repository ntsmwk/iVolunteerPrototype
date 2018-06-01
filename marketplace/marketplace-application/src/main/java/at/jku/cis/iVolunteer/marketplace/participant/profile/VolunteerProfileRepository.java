package at.jku.cis.iVolunteer.marketplace.participant.profile;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.participant.Volunteer;
import at.jku.cis.iVolunteer.model.participant.profile.VolunteerProfile;

@Repository
public interface VolunteerProfileRepository extends MongoRepository<VolunteerProfile, String> {

	VolunteerProfile findByVolunteer(Volunteer volunteer);

}
