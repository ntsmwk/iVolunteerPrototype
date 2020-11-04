package at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.treeProperty;

import java.util.List;

import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;

public interface TreePropertyDefinitionRepository extends HasTenantRepository<TreePropertyDefinition, String> {

	TreePropertyDefinition getByNameAndTenantId(String name, String tenantId);

	TreePropertyDefinition getByName(String name);

	List<TreePropertyDefinition> getAllByTenantId(String tenantId);

}
