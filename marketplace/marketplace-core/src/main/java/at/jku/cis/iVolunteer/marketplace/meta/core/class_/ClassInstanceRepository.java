package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.List;

import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@Repository
public interface ClassInstanceRepository extends HasTenantRepository<ClassInstance, String> {
		
	List<ClassInstance> getByClassDefinitionId(String classDefinitionId, String tenantId);
	
	List<ClassInstance> getByUserIdAndClassDefinitionId(String userId, String classDefinitionId, String tenantId);
	
	List<ClassInstance> getByUserIdAndInUserRepositoryAndInIssuerInbox(String userId, boolean inUserRepository, boolean inIssuerInbox, String tenantId);

	List<ClassInstance> getByIssuerIdAndInIssuerInboxAndInUserRepository(String issuerId, boolean inIssuerInbox, boolean inUserRepository, String tenantId);

	ClassInstance findByName(String name);

	
	
}
