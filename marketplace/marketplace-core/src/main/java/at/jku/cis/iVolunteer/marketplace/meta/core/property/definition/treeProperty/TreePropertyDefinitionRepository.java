package at.jku.cis.iVolunteer.marketplace.meta.core.property.definition.treeProperty;

import at.jku.cis.iVolunteer.marketplace.core.HasTenantRepository;
import at.jku.cis.iVolunteer.model.meta.core.property.definition.treeProperty.TreePropertyDefinition;

public interface TreePropertyDefinitionRepository extends HasTenantRepository<TreePropertyDefinition, String> {

	public TreePropertyDefinition findByName(String name);

}
