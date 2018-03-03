package at.jku.csi.marketplace.user;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ApplicationUserRepository extends MongoRepository<ApplicationUser, String> {
	ApplicationUser findByUsername(String username);
}