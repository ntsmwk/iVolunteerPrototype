package at.jku.cis.iVolunteer.core.tenant;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import at.jku.cis.iVolunteer.model.core.tenant.Tenant;

public interface TenantRepository extends MongoRepository<Tenant, String> {

	Tenant findByName(String name);
	
	List<Tenant> findByMarketplaceId(String marketplaceId);
}
