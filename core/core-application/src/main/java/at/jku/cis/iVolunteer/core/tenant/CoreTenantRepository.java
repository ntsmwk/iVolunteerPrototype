package at.jku.cis.iVolunteer.core.tenant;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.core.tenant.Tenant;

public interface CoreTenantRepository extends MongoRepository<Tenant, String> {

	Tenant findByName(String name);

}
