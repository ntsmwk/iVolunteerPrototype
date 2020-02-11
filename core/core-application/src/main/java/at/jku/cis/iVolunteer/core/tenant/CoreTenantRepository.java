package at.jku.cis.iVolunteer.core.tenant;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.core.user.CoreTenant;

public interface CoreTenantRepository extends MongoRepository<CoreTenant, String> {

	CoreTenant findByName(String name);

}
