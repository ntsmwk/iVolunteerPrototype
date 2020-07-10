package at.jku.cis.iVolunteer.core.user;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.core.user.CoreUser;

public interface CoreUserRepository extends MongoRepository<CoreUser, String> {
	
    CoreUser findByUsername(String username);
    
    List<CoreUser> findByUsernameIn(List<String> username);

}
