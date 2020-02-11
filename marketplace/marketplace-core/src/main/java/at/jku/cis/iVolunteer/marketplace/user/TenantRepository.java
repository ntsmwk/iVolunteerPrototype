package at.jku.cis.iVolunteer.marketplace.user;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.user.Tenant;


public interface TenantRepository extends MongoRepository<Tenant, String>{

	Tenant findByName(String name);

}
