package at.jku.cis.iVolunteer.core.security.activation;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.core.user.CoreUser;
import at.jku.cis.iVolunteer.model.user.PendingActivation;

@Repository
public interface CorePendingActivationRepository extends MongoRepository<PendingActivation, String> {

}
