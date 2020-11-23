package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.List;

import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassInstance;

@Repository
public interface ClassInstanceRepository extends HasTenantRepository<ClassInstance, String> {

	List<ClassInstance> getByUserId(String userId);

	List<ClassInstance> getByClassDefinitionIdAndTenantId(String classDefinitionId, String tenantId);

	List<ClassInstance> getByUserIdAndClassDefinitionIdAndTenantId(String userId, String classDefinitionId,
			String tenantId);

	List<ClassInstance> getByUserIdAndTenantId(String userId, String tenantId);

	List<ClassInstance> getByUserIdAndDerivationRuleId(String userId, String derivationRuleId);

	List<ClassInstance> getByClassArchetypeAndTenantId(ClassArchetype classArchetype, String tenantId);

	List<ClassInstance> getByClassArchetypeAndUserIdAndTenantId(ClassArchetype classArchetype, String userId,
			String tenantId);

	List<ClassInstance> getByClassArchetypeAndUserId(ClassArchetype classArchetype, String userId);

	List<ClassInstance> getByClassArchetypeAndTenantIdAndUserId(ClassArchetype classArchetype, String tenantId,
			String userId);

	List<ClassInstance> getByUserIdAndClassArchetypeAndTenantId(String userId, ClassArchetype classArchetype,
			String tenantId);

}