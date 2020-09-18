package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.List;

import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@Repository
public interface ClassInstanceRepository extends HasTenantRepository<ClassInstance, String> {

	List<ClassInstance> getByClassDefinitionIdAndTenantId(String classDefinitionId, String tenantId);

	List<ClassInstance> getByUserIdAndClassDefinitionIdAndTenantId(String userId, String classDefinitionId,
			String tenantId);

	List<ClassInstance> getByUserIdAndClassArchetypeAndTenantId(String userId, ClassArchetype classArchetype,
			String tenantId);

	List<ClassInstance> getByUserIdAndTenantId(String userId, String tenantId);

//	List<ClassInstance> getByUserIdAndInUserRepositoryAndInIssuerInboxAndTenantId(String userId, boolean inUserRepository, boolean inIssuerInbox, String tenantId);

	List<ClassInstance> getByIssuedAndTenantId(boolean issued, String tenantId);

}
