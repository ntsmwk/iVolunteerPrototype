package at.jku.cis.iVolunteer.core.volunteer;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import at.jku.cis.iVolunteer.model.core.user.CoreVolunteer;

public interface CoreVolunteerRepository extends MongoRepository<CoreVolunteer, String> {

	CoreVolunteer findByUsername(String username);

	@Query("{'tenantId': ?0}")
	List<CoreVolunteer> findAllByTenantId(String tenantId);
}
