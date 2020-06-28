package at.jku.cis.iVolunteer.core.helpseeker;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.core.user.CoreHelpSeeker;

public interface CoreHelpSeekerRepository extends MongoRepository<CoreHelpSeeker, String> {

	CoreHelpSeeker findByUsername(String username);
	
	List<CoreHelpSeeker>findByTenantId(String tenantId);
}
