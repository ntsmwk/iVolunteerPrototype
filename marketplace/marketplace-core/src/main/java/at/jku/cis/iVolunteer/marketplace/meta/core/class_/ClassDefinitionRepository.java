package at.jku.cis.iVolunteer.marketplace.meta.core.class_;

import org.springframework.stereotype.Repository;
import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.meta.core.clazz.ClassDefinition;

@Repository
public interface ClassDefinitionRepository extends HasTenantRepository<ClassDefinition, String> {

	ClassDefinition findByNameAndTenantId(String name, String tenantId);

}
