package at.jku.cis.iVolunteer.marketplace.usermapping;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.usermapping.UserMapping;

@Repository
public interface UserMappingRepository extends MongoRepository<UserMapping, String> {

	UserMapping findByExternalUserId(String externalUserId);
	
}
