package at.jku.cis.iVolunteer.core.recruiter;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.core.user.CoreRecruiter;

public interface CoreRecruiterRepository extends MongoRepository<CoreRecruiter, String> {

	CoreRecruiter findByUsername(String username);
}
