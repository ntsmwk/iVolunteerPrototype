package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import java.util.List;

import org.springframework.stereotype.Repository;

import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassArchetype;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

@Repository
public interface ClassDefinitionRepository extends HasTenantRepository<ClassDefinition, String> {

	List<ClassDefinition> findByClassArchetype(ClassArchetype classArchetype, String tenantId);

	ClassDefinition findById(String id, String tenantId);

	List<ClassDefinition> findAllByTenantId(String tenantId);
	
	
	// TODO: Philipp add tenantId?
	ClassDefinition findByName(String name);



}
