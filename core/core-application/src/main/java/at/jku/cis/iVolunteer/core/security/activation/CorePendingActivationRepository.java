package at.jku.cis.iVolunteer.core.security.activation;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.registration.PendingActivation;

@Repository
public interface CorePendingActivationRepository extends MongoRepository<PendingActivation, String> {

		PendingActivation findByUserId(String userId);
}
