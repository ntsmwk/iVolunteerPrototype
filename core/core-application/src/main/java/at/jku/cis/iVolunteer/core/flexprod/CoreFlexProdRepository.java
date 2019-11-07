package at.jku.cis.iVolunteer.core.flexprod;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.core.user.CoreFlexProd;

public interface CoreFlexProdRepository extends MongoRepository<CoreFlexProd, String> {

	CoreFlexProd findByUsername(String username);
}
