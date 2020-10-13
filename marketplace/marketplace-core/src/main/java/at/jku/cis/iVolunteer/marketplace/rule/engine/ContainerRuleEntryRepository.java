package at.jku.cis.iVolunteer.marketplace.rule.engine;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.model.rule.engine.ContainerRuleEntry;

@Repository
public interface ContainerRuleEntryRepository extends MongoRepository<ContainerRuleEntry, String> {
	
	List<ContainerRuleEntry> findByTenantId(String tenantId);
	
	ContainerRuleEntry getByIdAndTenantId(String id, String tenantId);
	
	List<ContainerRuleEntry> getByTenantIdAndContainer(String tenantId, String container);
	
	ContainerRuleEntry getByTenantIdAndContainerAndName(String tenantId, String container, String name);
	
}
