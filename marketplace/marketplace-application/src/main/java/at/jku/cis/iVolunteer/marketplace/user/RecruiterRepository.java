package at.jku.cis.iVolunteer.marketplace.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.user.Recruiter;

public interface RecruiterRepository extends MongoRepository<Recruiter, String> {

	Recruiter findByUsername(String username);
}
