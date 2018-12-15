package at.jku.cis.iVolunteer.core.admin;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.core.user.CoreAdmin;

public interface CoreAdminRepository extends MongoRepository<CoreAdmin, String> {

	CoreAdmin findByUsername(String username);
}
