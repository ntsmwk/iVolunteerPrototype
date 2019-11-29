package at.jku.cis.iVolunteer.marketplace.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.user.HelpSeeker;

public interface HelpSeekerRepository extends MongoRepository<HelpSeeker, String> {

	HelpSeeker findByUsername(String username);
}
