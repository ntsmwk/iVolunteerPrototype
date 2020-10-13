package at.jku.cis.iVolunteer.core.user;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.core.user.CoreUser;

public interface CoreUserRepository extends MongoRepository<CoreUser, String> {
	
    CoreUser findByUsername(String username);
    CoreUser findByLoginEmail(String loginEmail);
    CoreUser findByUsernameOrLoginEmail(String username, String loginEmail);
    
    List<CoreUser> findByUsernameIn(List<String> username);

}
