package at.jku.cis.iVolunteer.marketplace.volunteer.profile;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.user.Volunteer;
import at.jku.cis.iVolunteer.model.volunteer.profile.VolunteerProfile;

@Repository
public interface VolunteerProfileRepository extends MongoRepository<VolunteerProfile, String> {

	VolunteerProfile findByVolunteer(Volunteer volunteer);

}
