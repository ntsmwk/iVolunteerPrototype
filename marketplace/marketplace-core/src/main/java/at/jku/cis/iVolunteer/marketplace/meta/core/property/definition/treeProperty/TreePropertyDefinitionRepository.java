package at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.treeProperty;

import java.util.List;

import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;

public interface TreePropertyDefinitionRepository extends HasTenantRepository<TreePropertyDefinition, String> {

	public TreePropertyDefinition findByName(String name);

	public TreePropertyDefinition findByNameAndTenantId(String name, String tenantId);

	public List<TreePropertyDefinition> getAllByTenantId(String tenantId);

}
